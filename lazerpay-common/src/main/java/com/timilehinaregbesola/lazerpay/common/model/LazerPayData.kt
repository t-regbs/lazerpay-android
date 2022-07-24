package com.timilehinaregbesola.lazerpay.common.model

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
)

enum class LazerPayCurrency {
    NGN,
    USD
}
