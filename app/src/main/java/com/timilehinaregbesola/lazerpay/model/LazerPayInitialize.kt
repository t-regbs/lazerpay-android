package com.timilehinaregbesola.lazerpay.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class LazerPayInitialize(
    val `data`: InitializeData,
    val type: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class InitializeData(
//    val `data`: List<Data>?,
    val message: String?,
    val status: String?,
    val statusCode: Int?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Data(
    val address: String?,
    val blockchain: String?,
    val createdAt: String?,
    val id: String?,
    val logo: String?,
    val name: String?,
    val network: String?,
    val status: String?,
    val symbol: String?,
    val updatedAt: String?
) : Parcelable
