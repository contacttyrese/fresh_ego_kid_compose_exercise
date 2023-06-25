package com.example.freshegokidcompose.data.local

import androidx.room.*
import com.example.freshegokidcompose.data.model.search.SearchQuery
import io.reactivex.Observable

@Dao
interface SearchQueryDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertQuery(searchQuery: SearchQuery)
    @Delete
    suspend fun deleteQuery(searchQuery: SearchQuery)
    @Query("SELECT * FROM searchquery ORDER BY id ASC")
    fun getQueriesOrderedById(): Observable<List<SearchQuery>>
    @Query("SELECT * FROM searchquery ORDER BY id DESC")
    fun getQueriesReverseOrderedById(): Observable<List<SearchQuery>>
}