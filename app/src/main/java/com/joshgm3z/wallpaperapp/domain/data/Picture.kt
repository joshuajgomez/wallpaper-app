package com.joshgm3z.wallpaperapp.domain.data

import android.os.Parcel
import android.os.Parcelable

data class Picture(
    var dateAdded: Long,
    var url: String?,
    var description: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
    )

    constructor() : this(
        0, "", ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(dateAdded)
        parcel.writeString(url)
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