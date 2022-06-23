package com.timilehinaregbesola.lazerpay

import androidx.activity.result.ActivityResultRegistry
import androidx.appcompat.app.AppCompatActivity
import com.timilehinaregbesola.lazerpay.model.LazerPayCurrency
import com.timilehinaregbesola.lazerpay.model.LazerPayData
import com.timilehinaregbesola.lazerpay.model.LazerPayResult
import com.timilehinaregbesola.lazerpay.ui.PayWithCheckout

class LazerPayCheckout private constructor(
    private val activity: AppCompatActivity,
    private val resultRegistry: ActivityResultRegistry,
    private val publicKey: String,
    private val name: String,
    private val email: String,
    private val amount: String,
    private val businessLogo: String?,
    private val currency: LazerPayCurrency?,
    private val acceptPartialPayment: Boolean?,
    private val reference: String?,
    private val metadata: String?
) {
    private val lazerpayData: LazerPayData
        get() = LazerPayData(
            publicKey,
            name,
            email,
            amount,
            businessLogo,
            currency,
            acceptPartialPayment,
            reference,
            metadata
        )

    fun charge(resultListener: LazerPayResultListener) {
        activity.registerForActivityResult(PayWithCheckout(), resultRegistry) { lazerPayResult ->
            when (lazerPayResult) {
                LazerPayResult.Cancel -> resultListener.onCancelled()
                LazerPayResult.Close -> TODO()
                is LazerPayResult.Error -> resultListener.onError(lazerPayResult.exception)
                is LazerPayResult.Initialize -> TODO()
                is LazerPayResult.Success -> resultListener.onSuccess(lazerPayResult.data)
            }
        }.launch(lazerpayData)
    }

    class Builder(
        private val activity: AppCompatActivity,
        private val publicKey: String,
        private val name: String,
        private val email: String,
        private val amount: String,
    ) {
        private var activityResultRegistry: ActivityResultRegistry = activity.activityResultRegistry

        private var businessLogo: String? = null
        private var currency: LazerPayCurrency = LazerPayCurrency.NGN
        private var acceptPartialPayment: Boolean = false
        private var reference: String? = null
        private var metadata: String? = null

        fun activityResultRegistry(resultRegistry: ActivityResultRegistry): Builder {
            this.activityResultRegistry = resultRegistry
            return this
        }

        fun businessLogo(businessLogo: String): Builder {
            this.businessLogo = businessLogo
            return this
        }

        fun currency(currency: LazerPayCurrency): Builder {
            this.currency = currency
            return this
        }

        fun acceptPartialPayment(acceptPartialPayment: Boolean): Builder {
            this.acceptPartialPayment = acceptPartialPayment
            return this
        }

        fun reference(reference: String): Builder {
            this.reference = reference
            return this
        }

        fun metadata(metadata: String): Builder {
            this.metadata = metadata
            return this
        }

        fun build(): LazerPayCheckout {
            return LazerPayCheckout(
                activity,
                activityResultRegistry,
                publicKey,
                name,
                email,
                amount,
                businessLogo,
                currency,
                acceptPartialPayment,
                reference,
                metadata
            )
        }
    }
}
