package com.example.ai.datalayer.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ai.datalayer.source.local.entity.FavoriteCoinEntity

@Database(entities = [FavoriteCoinEntity::class], version = 1)
abstract class CoinRoomDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao

    companion object {
        @Volatile
        var INSTANCE: CoinRoomDatabase? = null

        fun getInstance(context: Context): CoinRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        CoinRoomDatabase::class.java,
                        "CoinDatabase"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}