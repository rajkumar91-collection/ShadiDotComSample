package com.rajkumar.shadidotcom.repository.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "profile")
data  class Profile(


    @PrimaryKey
    @ColumnInfo(name = "uuid")
    var uuid: String,

    @ColumnInfo(name = "gender")
    var gender: String? ,

    @ColumnInfo(name = "name")
    var name: String? ,


    @ColumnInfo(name = "city")
    var city: String? ,


    @ColumnInfo(name = "state")
    var state: String? ,


    @ColumnInfo(name = "email")
    var email: String? ,


    @ColumnInfo(name = "age")
    var age: String? ,


    @ColumnInfo(name = "photo")
    var photo: String? ,

    @ColumnInfo(name = "interest")
    var interest: String? //Y or N // NA


) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uuid)
        parcel.writeString(gender)
        parcel.writeString(name)
        parcel.writeString(city)
        parcel.writeString(state)
        parcel.writeString(email)
        parcel.writeString(age)
        parcel.writeString(photo)
        parcel.writeString(interest)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Profile> {
        override fun createFromParcel(parcel: Parcel): Profile {
            return Profile(parcel)
        }

        override fun newArray(size: Int): Array<Profile?> {
            return arrayOfNulls(size)
        }
    }
}





