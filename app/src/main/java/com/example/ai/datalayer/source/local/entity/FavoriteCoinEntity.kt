package com.example.ai.datalayer.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteCoin")
data class FavoriteCoinEntity(
    @PrimaryKey var uuid: String,
    @ColumnInfo var symbol: String? = null,
    @ColumnInfo var name: String? = null,
    @ColumnInfo var coinrankingUrl: String? = null
)