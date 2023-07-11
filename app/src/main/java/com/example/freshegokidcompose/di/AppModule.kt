package com.example.freshegokidcompose.di

import android.content.Context
import androidx.room.Room
import com.example.freshegokidcompose.BaseApplication
import com.example.freshegokidcompose.data.remote.home.HomeRetroFit
import com.example.freshegokidcompose.data.remote.home.HomeService
import com.example.freshegokidcompose.data.remote.search.ProductListRetroFit
import com.example.freshegokidcompose.data.remote.search.ProductListService
import com.example.freshegokidcompose.helpers.InteractorHelper
import com.example.freshegokidcompose.helpers.RepositoryHelper
import com.example.freshegokidcompose.data.local.SearchQueryDao
import com.example.freshegokidcompose.data.local.SearchQueryDatabase
import com.example.freshegokidcompose.data.remote.productdetails.DetailsRetroFit
import com.example.freshegokidcompose.data.remote.productdetails.DetailsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Provides
    fun provideListService(): ProductListService {
        return ProductListRetroFit.createProductListService()
    }

    @Provides
    fun provideDetailsService(): DetailsService {
        return DetailsRetroFit.createDetailsService()
    }

    @Provides
    fun provideHomeService(): HomeService {
        return HomeRetroFit.createHomeService()
    }

    @Provides
    fun provideDisposables(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    fun provideRepositoryHelper(): RepositoryHelper {
        return RepositoryHelper()
    }

    @Provides
    fun provideInteractorHelper(): InteractorHelper {
        return InteractorHelper()
    }

    @Singleton
    @Provides
    fun provideSearchQueryDatabase(@ApplicationContext app: Context): SearchQueryDatabase {
        return Room.databaseBuilder(
            app,
            SearchQueryDatabase::class.java,
            name = "queries.db"
        ).build()
    }

    @Provides
    fun provideSearchQueryDao(searchQueryDatabase: SearchQueryDatabase): SearchQueryDao {
        return searchQueryDatabase.dao
    }
}