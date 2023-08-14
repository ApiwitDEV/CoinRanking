package com.example.ai.datalayer.source.local

import androidx.room.*
import com.example.ai.datalayer.source.local.entity.FavoriteCoinEntity

@Dao
interface CoinDao {

    @Query("SELECT * FROM FavoriteCoin")
    suspend fun getFavoriteCoin(): List<FavoriteCoinEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteCoin: FavoriteCoinEntity)

    @Delete
    suspend fun delete(favoriteCoin: FavoriteCoinEntity)
}