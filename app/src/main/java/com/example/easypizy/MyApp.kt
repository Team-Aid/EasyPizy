package com.example.easypizy

import android.app.Application
import android.content.Context
import com.example.easypizy.data.data_source.local.SmokeMemoDatabase
import com.example.easypizy.data.data_source.local.SmokePlaceDatabase
import com.example.easypizy.data.repository.SmokeMemoRepository
import com.example.easypizy.data.repository.SmokePlaceRepository

class MyApp : Application() {
    lateinit var context: Context
    init {
        instance =this}
    companion object {
        private var instance: MyApp? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    val smokePlaceDatabase by lazy { SmokePlaceDatabase.getDatabase(this) }
    val smokePlaceRepository by lazy { SmokePlaceRepository(smokePlaceDatabase.smokePlaceDao()) }

    val smokeMemoDatabase by lazy { SmokeMemoDatabase.getDatabase(this) }
    val smokeMemoRepository by lazy { SmokeMemoRepository(smokeMemoDatabase.smokeMemoDao()) }
}