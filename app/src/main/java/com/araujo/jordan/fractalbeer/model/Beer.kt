package com.araujo.jordan.fractalbeer.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Beer Model Class
 * Uses Kotlin Data Class
 * SerializedName for the Retrofit speed inject the variables from API results
 * Parcelable for easily pass the Beer thought Activities
 *  Created by araujojordan on 19/12/2018
 */
@Parcelize
data class Beer (
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int,
    @SerializedName("image_url") val image_url: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("description") val description: String
) : Parcelable