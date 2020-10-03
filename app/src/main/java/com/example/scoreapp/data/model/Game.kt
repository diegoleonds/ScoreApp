package com.example.scoreapp.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity()
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ForeignKey(
        entity = Season::class,
        parentColumns = ["id"],
        childColumns = ["fkSeason"]
    )
    val fkSeason: Long,
    var points: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeLong(fkSeason)
        parcel.writeInt(points)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Game> {
        override fun createFromParcel(parcel: Parcel): Game {
            return Game(parcel)
        }

        override fun newArray(size: Int): Array<Game?> {
            return arrayOfNulls(size)
        }
    }

}