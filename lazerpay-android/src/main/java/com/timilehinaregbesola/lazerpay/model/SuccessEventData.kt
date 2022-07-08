package com.timilehinaregbesola.lazerpay.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@JsonClass(generateAdapter = true)
internal data class SuccessEventData(
    val `data`: SuccessData,
    val type: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class SuccessData(
    val acceptPartialPayment: Boolean?,
    val actualAmount: Double?,
    val amountPaid: Double?,
    val amountPaidFiat: Double?,
    val amountReceived: Int?,
    val amountReceivedFiat: Int?,
    val blockNumber: Int?,
    val blockchain: String?,
    val coin: String?,
    val createdAt: String?,
    val cryptoRate: Double?,
    val currency: String?,
    val customer: Customer?,
    val feeInCrypto: Double?,
    val fiatAmount: Int?,
    val fiatRate: Double?,
    val hash: String?,
    val id: String?,
//    val metadata: Metadata?,
    val network: String?,
//    val paymentButton: PaymentButton?,
//    val paymentLink: PaymentLink?,
    val recipientAddress: String?,
    val reference: String?,
    val senderAddress: String?,
    val status: String,
    val type: String,
    val updatedAt: String?
) : Parcelable

@Parcelize
data class Customer(
    val customerEmail: String?,
    val customerName: String?,
    val customerPhone: Int?,
    val id: String?
) : Parcelable

internal class Metadata

internal class PaymentButton

internal class PaymentLink
