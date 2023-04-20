package com.example.easypizy

import android.app.Application
import com.example.easypizy.data.data_source.local.SmokeMemoDatabase
import com.example.easypizy.data.data_source.local.SmokePlaceDatabase
import com.example.easypizy.data.repository.SmokeMemoRepository
import com.example.easypizy.data.repository.SmokePlaceRepository

class SmokePlaceApplication : Application() {
    val smokePlaceDatabase by lazy { SmokePlaceDatabase.getDatabase(this) }
    val smokePlaceRepository by lazy { SmokePlaceRepository(smokePlaceDatabase.smokePlaceDao()) }

    val smokeMemoDatabase by lazy { SmokeMemoDatabase.getDatabase(this) }
    val smokeMemoRepository by lazy { SmokeMemoRepository(smokeMemoDatabase.smokeMemoDao()) }
}