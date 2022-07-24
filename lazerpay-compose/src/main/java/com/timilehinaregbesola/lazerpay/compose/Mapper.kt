package com.timilehinaregbesola.lazerpay.compose

import com.timilehinaregbesola.lazerpay.common.model.LazerPayData
import com.timilehinaregbesola.lazerpay.common.model.LazerPayCurrency as CommonCurrency
import com.timilehinaregbesola.lazerpay.common.model.SuccessData as CommonSuccessData

class Mapper {
    fun mapFromCommonData(data: CommonSuccessData): SuccessData {
        return SuccessData(
            data.acceptPartialPayment,
            data.actualAmount,
            data.amountPaid,
            data.amountPaidFiat,
            data.amountReceived,
            data.amountReceivedFiat,
            data.blockNumber,
            data.blockchain,
            data.coin,
            data.createdAt,
            data.cryptoRate,
            data.currency,
            Customer(data.customer?.customerEmail, data.customer?.customerName, data.customer?.customerPhone, data.customer?.id),
            data.feeInCrypto,
            data.fiatAmount,
            data.fiatRate,
            data.hash,
            data.id,
            data.network,
            data.recipientAddress,
            data.reference,
            data.senderAddress,
            data.status,
            data.type,
            data.updatedAt
        )
    }

    fun mapToCommonLazerPayData(params: LazerPayParams): LazerPayData {
        return LazerPayData(
            publicKey = params.publicKey,
            name = params.name,
            email = params.email,
            amount = params.amount,
            businessLogo = params.businessLogo,
            currency = if (params.currency == LazerPayCurrency.NGN) CommonCurrency.NGN else CommonCurrency.USD,
            acceptPartialPayment = params.acceptPartialPayment,
            reference = params.reference,
            metadata = params.metadata
        )
    }
}
