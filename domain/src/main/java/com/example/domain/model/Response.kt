package com.example.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Breed(
    val id: Int,
    val name: String,
    @Json(name = "life_span")
    val lifeSpan: String? = null,
    @Json(name = "breed_group")
    val breedGroup: String? = null,
    val temperament: String? = null,
    @Json(name = "bred_for")
    val bredFor: String? = null,
    @Json(name = "reference_image_id")
    val referenceImageId: String? = null
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Measurement(
    val metric: String,
    val imperial: String
) : Parcelable