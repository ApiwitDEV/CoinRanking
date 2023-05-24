package com.example.ai.datalayer.source.local

import androidx.room.*
import com.example.ai.datalayer.source.local.entity.FavoriteCoinEntity

@Dao
interface CoinDao {

    @Query("SELECT * FROM FavoriteCoin")
    fun getFavoriteCoin(): List<FavoriteCoinEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteCoin: FavoriteCoinEntity)

    @Delete
    fun delete(favoriteCoin: FavoriteCoinEntity)
}