package com.rajkumar.shadidotcom.repository.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rajkumar.shadidotcom.repository.Converters


@Database(entities = [Profile::class], version = 1,exportSchema = false)
@TypeConverters(Converters::class)
public abstract class ProfileDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao



    companion object {
        const val DATABASE_NAME = "profiles_db"
        @Volatile
        private var INSTANCE: ProfileDatabase? = null

        fun getDatabase(context: Context): ProfileDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE
                ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProfileDatabase::class.java,
                    DATABASE_NAME
                ).addCallback(ProfileDatabaseCallback())
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

    }

    private class ProfileDatabaseCallback(
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                Log.d("MyApp","Database Created")
            }
        }


    }
}

