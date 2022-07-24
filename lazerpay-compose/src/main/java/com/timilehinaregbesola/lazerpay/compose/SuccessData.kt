package com.timilehinaregbesola.lazerpay.compose

data class SuccessData(
    var acceptPartialPayment: Boolean?,
    var actualAmount: Double?,
    var amountPaid: Double?,
    var amountPaidFiat: Double?,
    var amountReceived: Int?,
    var amountReceivedFiat: Int?,
    var blockNumber: Int?,
    var blockchain: String?,
    var coin: String?,
    var createdAt: String?,
    var cryptoRate: Double?,
    var currency: String?,
    var customer: Customer?,
    var feeInCrypto: Double?,
    var fiatAmount: Int?,
    var fiatRate: Double?,
    var hash: String?,
    var id: String?,
    var network: String?,
    var recipientAddress: String?,
    var reference: String?,
    var senderAddress: String?,
    var status: String,
    var type: String,
    var updatedAt: String?
)

data class Customer(
    val customerEmail: String?,
    val customerName: String?,
    val customerPhone: Int?,
    val id: String?
)
