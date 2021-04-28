package com.rajkumar.shadidotcom.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query


@Dao
interface ProfileDao {


    @Insert(onConflict = IGNORE)
    fun insertProfile(profile: List<Profile?>?): LongArray?


    @Query(
        "UPDATE profile SET interest = :interest WHERE uuid = :uuid"
    )
    fun updateProfile(
        uuid: String?,
        interest: String?
    )



    @Query("SELECT * FROM profile")
    fun getProfiles(): LiveData<List<Profile?>?>
}