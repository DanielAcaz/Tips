package br.com.broscoder.tips.model

import android.os.Parcel
import android.os.Parcelable

class Friend : TipsModel, Comparable<Friend>, Parcelable {

    var id: Long
    var name: String

    constructor(name: String) {
        this.name = name
        this.id = System.currentTimeMillis()
    }

    constructor(name: String, id: Long) {
        this.name = name
        this.id = id
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readLong()
    )

    override fun compareTo(other: Friend): Int {
        return this.name.compareTo(other.name)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Friend

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.writeString(this.name)
        parcel?.writeLong(this.id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Friend> {
        override fun createFromParcel(parcel: Parcel): Friend {
            return Friend(parcel)
        }

        override fun newArray(size: Int): Array<Friend?> {
            return arrayOfNulls(size)
        }
    }
}