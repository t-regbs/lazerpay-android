package com.timilehinaregbesola.lazerpay.model

import com.timilehinaregbesola.lazerpay.common.model.LazerPayCurrency as CommonCurrency
import com.timilehinaregbesola.lazerpay.common.model.LazerPayData as CommonLazerPayData
import com.timilehinaregbesola.lazerpay.common.model.SuccessData as CommonSuccessData

class Mapper {
    fun mapFromCommonSuccessData(data: CommonSuccessData): SuccessData {
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

    fun mapToCommonLazerPayData(data: LazerPayData): CommonLazerPayData {
        return CommonLazerPayData(
            publicKey = data.publicKey,
            name = data.name,
            email = data.email,
            amount = data.amount,
            businessLogo = data.businessLogo,
            currency = if (data.currency == LazerPayCurrency.NGN) CommonCurrency.NGN else CommonCurrency.USD,
            acceptPartialPayment = data.acceptPartialPayment,
            reference = data.reference,
            metadata = data.metadata
        )
    }
}
