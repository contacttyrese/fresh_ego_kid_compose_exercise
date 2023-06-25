package com.example.freshegokidcompose.data.model.search

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchQuery(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val query: String,
)