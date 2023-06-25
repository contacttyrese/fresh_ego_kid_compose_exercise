package com.example.freshegokidcompose.features.search.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freshegokidcompose.data.model.search.ProductListPage
import com.example.freshegokidcompose.domain.ListInteractor
import com.example.freshegokidcompose.data.model.search.SearchQuery
import com.example.freshegokidcompose.data.local.SearchQueryDao
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val interactor: ListInteractor,
    private val disposables: CompositeDisposable,
    private val dao: SearchQueryDao
    ) : ViewModel() {
    val userActionSubject = PublishSubject.create<SearchUserAction>()
    val queryActionSubject = PublishSubject.create<SearchQueryUserAction>()
    private val _viewState = MutableLiveData<SearchViewState>()
    val viewState: LiveData<SearchViewState>
        get() = _viewState
    val userAction = MutableLiveData<SearchUserAction>()
    private val _searchState = MutableLiveData<SearchQueryState>()
    val searchState: LiveData<SearchQueryState>
        get() = _searchState

    init {
        disposables.add(
            queryActionSubject
                .subscribeOn(Schedulers.io())
                .subscribe({ queryUserAction ->
                    onQueryAction(queryUserAction)
                }, { throwable ->
                    Log.e("process_query_error", "query processing error $throwable")
                    _searchState.postValue(SearchQueryState.QueryError(throwable))
                    throwable.printStackTrace()
                })
        )
        disposables.add(
            userActionSubject
                .subscribeOn(Schedulers.io())
                .subscribe({ searchUserAction ->
                    onUserAction(searchUserAction)
                }, { throwable ->
                    processThrowable(throwable)
                })
        )
    }

    fun clearDisposable() {
        // soft clear
//        disposables.clear()
        // hard clear
        disposables.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        clearDisposable()
    }

    private fun processSearchResults(listPage: ProductListPage) {
        Log.i("process_result_sub", "size of search results is ${listPage.searchResults.size}")
        _viewState.postValue(SearchViewState.ProductLoaded(listPage))
    }

    private fun processThrowable(throwable: Throwable) {
        Log.e("process_product_error", "product processing error $throwable")
        throwable.printStackTrace()
        _viewState.postValue(SearchViewState.ProductLoadError(throwable))
    }

    fun onUserAction(searchUserAction: SearchUserAction) {
        when (searchUserAction) {
            SearchUserAction.AwaitingInput -> {
                Log.i("user_action_await", "awaiting input")
                _searchState.postValue(SearchQueryState.SetupState)
            }
            is SearchUserAction.UserTyping -> {
                Log.i("user_action_typing", "user typing: ${searchUserAction.input}")
            }
            is SearchUserAction.QuerySubmittedError -> {
                Log.e("user_action_error", "user query error")
                processThrowable(java.lang.RuntimeException("user query error"))
            }
            is SearchUserAction.QuerySubmittedSuccess -> {
                Log.i("user_action_success", "user query success")
                _viewState.postValue(SearchViewState.Loading)
                disposables.add(
                    interactor.getPageObservableWithSearchResultsByQuery(searchUserAction.query)
                        .subscribe({ page ->
                            processSearchResults(page)
                        }, { throwable ->
                            processThrowable(throwable)
                        })
                )
            }
        }
    }

    fun onQueryAction(searchQueryUserAction: SearchQueryUserAction) {
        when(searchQueryUserAction) {
            is SearchQueryUserAction.DeleteQuery -> {
                _searchState.postValue(SearchQueryState.DeletingQuery)
                viewModelScope.launch {
                    dao.deleteQuery(searchQueryUserAction.searchQuery)
                }
            }
            is SearchQueryUserAction.SaveQuery -> {
                _searchState.postValue(SearchQueryState.SavingQuery)
                viewModelScope.launch {
                    dao.insertQuery(searchQueryUserAction.searchQuery)
                }
            }
            is SearchQueryUserAction.SelectQuery -> {
                _searchState.postValue(SearchQueryState.SelectedQuery(searchQueryUserAction.searchQuery))
            }
        }
    }

    fun getSearchQueries(): Observable<List<SearchQuery>> {
        return dao.getQueriesOrderedById()
            .subscribeOn(Schedulers.io())
    }
}