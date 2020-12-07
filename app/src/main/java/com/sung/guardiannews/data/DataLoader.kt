package com.sung.guardiannews.data

import android.app.Application
import com.sung.guardiannews.data.local.GuardianNewsDatabase

class DataLoader private constructor(private val app: Application) {
    private val LOCK = Any()
    private var INSTANCE: DataLoader? = null
    private val database by lazy {
        GuardianNewsDatabase.create(app)
    }

    private val serviceApi by lazy {
        GuardianNewsService.create()
    }

    fun getInstance(app: Application): DataLoader {
        val tempInstance = INSTANCE
        if (tempInstance != null)
            return tempInstance
        synchronized(LOCK) {
            INSTANCE = DataLoader(app)
            return INSTANCE!!
        }
    }

    fun getRepository() = GuardianNewsRepository(database, serviceApi)
}