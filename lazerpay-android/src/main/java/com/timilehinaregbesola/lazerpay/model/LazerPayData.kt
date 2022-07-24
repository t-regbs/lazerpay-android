package com.timilehinaregbesola.lazerpay.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LazerPayData(
    val publicKey: String,
    val name: String,
    val email: String,
    val amount: String,
    val businessLogo: String? = null,
    val currency: LazerPayCurrency? = LazerPayCurrency.NGN,
    val acceptPartialPayment: Boolean? = false,
    val reference: String? = "",
    val metadata: String? = null
) : Parcelable

enum class LazerPayCurrency {
    NGN,
    USD
}
