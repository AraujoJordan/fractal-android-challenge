package com.araujo.jordan.fractalbeer.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Beer (
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int,
    @SerializedName("image_url") val image_url: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("description") val description: String
) : Parcelable