package com.joshgm3z.wallpaperapp.domain.data

import android.os.Parcel
import android.os.Parcelable

data class Picture(
    val url: String?,
    val id: String?,
    val res: Int,
    val description: String?,
): Parcelable {
    constructor(parcel: Parcel) :this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeString(id)
        parcel.writeInt(res)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Picture> {
        override fun createFromParcel(parcel: Parcel): Picture {
            return Picture(parcel)
        }

        override fun newArray(size: Int): Array<Picture?> {
            return arrayOfNulls(size)
        }
    }
}