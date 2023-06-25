package com.example.freshegokidcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.freshegokidcompose.data.model.search.SearchQuery

@Database(
    entities = [SearchQuery::class],
    version = 1
)
abstract class SearchQueryDatabase : RoomDatabase() {
    abstract val dao: SearchQueryDao
}