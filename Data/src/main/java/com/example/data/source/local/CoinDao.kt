package com.example.data.source.local

import androidx.room.*
import androidx.room.Insert
import com.example.data.source.local.entity.FavoriteCoinEntity

@Dao
interface CoinDao {

    @Query("SELECT * FROM FavoriteCoin")
    suspend fun getFavoriteCoin(): List<FavoriteCoinEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteCoin: FavoriteCoinEntity)

    @Delete
    suspend fun delete(favoriteCoin: FavoriteCoinEntity)
}