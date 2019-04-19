package br.com.broscoder.tips.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class RestaurantItems(val id: Long,
                           @SerializedName("id_restaurant") val restaurantId: Long,
                           val lotation: String,
                           @SerializedName("wait_time") val waitTime: Long,
                           val price: Double,
                           val rating: Int) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readDouble(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeLong(restaurantId)
        parcel.writeString(lotation)
        parcel.writeLong(waitTime)
        parcel.writeDouble(price)
        parcel.writeInt(rating)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RestaurantItems> {
        override fun createFromParcel(parcel: Parcel): RestaurantItems {
            return RestaurantItems(parcel)
        }

        override fun newArray(size: Int): Array<RestaurantItems?> {
            return arrayOfNulls(size)
        }
    }
}