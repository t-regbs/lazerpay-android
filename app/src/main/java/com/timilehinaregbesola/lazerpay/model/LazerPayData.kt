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
    val reference: String? = null,
    val metadata: String? = null
) : Parcelable {
    val isProd = !publicKey.contains("test")

    // Map to request
    fun toRequestMap(): Map<String, Any> {
        return buildMap<String, Any> {
            "publicKey" to publicKey
            "name" to name
            "email" to email
            "amount" to amount
            if (!businessLogo.isNullOrEmpty()) "businessLogo" to businessLogo
            "currency" to (currency?.name ?: "NGN")
            "acceptPartialPayment" to acceptPartialPayment
            "reference" to reference
            "metadata" to metadata
        }.pruneNullValues()
    }

    private fun <K, V> Map<K, V?>.pruneNullValues(): Map<K, V> {
        return filterValues { it != null } as Map<K, V>
    }
}

enum class LazerPayCurrency {
    NGN,
    USD
}
