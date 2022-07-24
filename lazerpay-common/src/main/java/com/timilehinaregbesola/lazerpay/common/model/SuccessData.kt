package com.timilehinaregbesola.lazerpay.common.model

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
    val network: String?,
    val recipientAddress: String?,
    val reference: String?,
    val senderAddress: String?,
    val status: String,
    val type: String,
    val updatedAt: String?
)

data class Customer(
    val customerEmail: String?,
    val customerName: String?,
    val customerPhone: Int?,
    val id: String?
)
