package com.example.easypizy.data.data_source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.easypizy.data.data_source.local.dao.SmokeMemoDao
import com.example.easypizy.data.data_source.local.entity.SmokeMemoEntity

@Database(entities = [SmokeMemoEntity::class], version = 1, exportSchema = false)
abstract class SmokeMemoDatabase : RoomDatabase() {

    abstract fun smokeMemoDao(): SmokeMemoDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: SmokeMemoDatabase? = null

        fun getDatabase(context: Context): SmokeMemoDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SmokeMemoDatabase::class.java,
                    "smoke_memo_table"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}