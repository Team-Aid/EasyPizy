package com.example.easypizy

import android.app.Application
import com.example.easypizy.data.data_source.local.SmokePlaceDatabase
import com.example.easypizy.data.repository.SmokePlaceRepository

class SmokePlaceApplication : Application() {
    val database by lazy { SmokePlaceDatabase.getDatabase(this) }
    val repository by lazy { SmokePlaceRepository(database.smokePlaceDao()) }
}