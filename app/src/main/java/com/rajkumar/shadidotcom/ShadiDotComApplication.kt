package com.rajkumar.shadidotcom

import android.app.Application
import com.rajkumar.shadidotcom.repository.DataRepository
import com.rajkumar.shadidotcom.repository.database.ProfileDatabase

class ShadiDotComApplication : Application() {

    val database by lazy { ProfileDatabase.getDatabase(this) }
    val repository by lazy {
        DataRepository(
            database.profileDao()
        )
    }
}