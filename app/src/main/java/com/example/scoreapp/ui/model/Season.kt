package com.example.scoreapp.ui.model

import android.os.Parcel
import android.os.Parcelable

/**
 * implements parcelable to be able to be sent to other activities via intent
 */
data class Season(val id: Long,
                  var maxScore: Int,
                  var minScore: Int) : Parcelable{
    constructor(parcel: Parcel) : this(
    parcel.readLong(),
    parcel.readInt(),
    parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeInt(maxScore)
        parcel.writeInt(minScore)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Season> {
        override fun createFromParcel(parcel: Parcel): Season {
            return Season(parcel)
        }

        override fun newArray(size: Int): Array<Season?> {
            return arrayOfNulls(size)
        }
    }
}