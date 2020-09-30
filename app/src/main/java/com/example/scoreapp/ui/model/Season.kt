package com.example.scoreapp.ui.model

import android.os.Parcel
import android.os.Parcelable

data class Season(val id: Long,
                  var maxRecord: Int,
                  var minRecord: Int) : Parcelable{
    constructor(parcel: Parcel) : this(
    parcel.readLong(),
    parcel.readInt(),
    parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeInt(maxRecord)
        parcel.writeInt(minRecord)
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