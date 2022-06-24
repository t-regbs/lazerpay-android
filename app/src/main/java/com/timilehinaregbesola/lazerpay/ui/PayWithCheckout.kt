package com.timilehinaregbesola.lazerpay.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.timilehinaregbesola.lazerpay.model.LazerPayData
import com.timilehinaregbesola.lazerpay.model.LazerPayResult

class PayWithCheckout : ActivityResultContract<LazerPayData, LazerPayResult>() {
    override fun createIntent(context: Context, input: LazerPayData): Intent {
        return Intent(context, LazerpayActivity::class.java).apply {
            putExtra(LazerpayActivity.EXTRA_DATA, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): LazerPayResult {
        if (resultCode == Activity.RESULT_CANCELED) {
            return LazerPayResult.Cancel
        }

        if (resultCode == Activity.RESULT_OK && intent != null) {
            return intent.getParcelableExtra(LazerpayActivity.EXTRA_TRANSACTION_RESULT)
                ?: error(NO_RESULT_ERROR)
        }

        error(NO_RESULT_ERROR)
    }

    companion object {
        const val NO_RESULT_ERROR =
            "No transaction result. This should never happen. Please report issue to Lazerpay"
    }
}
