package com.timilehinaregbesola.lazerpay

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.timilehinaregbesola.lazerpay.model.Customer
import com.timilehinaregbesola.lazerpay.model.LazerPayData
import com.timilehinaregbesola.lazerpay.model.LazerPayResult
import com.timilehinaregbesola.lazerpay.model.SuccessData
import com.timilehinaregbesola.lazerpay.ui.LazerpayActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.lang.IllegalStateException

@RunWith(AndroidJUnit4::class)
internal class LazerPaySdkTest {
    private val appContext: Context
        get() = ApplicationProvider.getApplicationContext()

    @Mock
    lateinit var resultListener: LazerPayResultListener

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun whenPaymentIsSuccessful_invokesResultListenerOnSuccess() {
        val intent = Intent(appContext, LazerpayActivity::class.java).apply {
            putExtra(LazerpayActivity.EXTRA_DATA, TEST_DATA)
        }
        ActivityScenario.launch<LazerpayActivity>(intent).use { scenario ->
            scenario.moveToState(Lifecycle.State.CREATED)
            scenario.onActivity { activity ->
                val lazerpayResult = LazerPayResult.Success(TEST_SUCCESSFUL_DATA)
                val testRegistry = getTestResultRegistry(lazerpayResult)
                val lazerSdk = LazerPaySdk.Builder(activity, TEST_DATA.publicKey, TEST_DATA.name, TEST_EMAIL, TEST_DATA.amount)
                    .activityResultRegistry(testRegistry)
                    .reference(TEST_DATA.reference!!)
                    .build()
                lazerSdk.initialize(resultListener)
                lazerSdk.charge()
            }
            scenario.moveToState(Lifecycle.State.RESUMED)
            scenario.onActivity {
                verify(resultListener).onSuccess(TEST_SUCCESSFUL_DATA)
            }
        }
    }

    @Test
    fun whenPaymentFails_invokesResultListenerOnError() {
        val intent = Intent(appContext, LazerpayActivity::class.java).apply {
            putExtra(LazerpayActivity.EXTRA_DATA, TEST_DATA)
        }
        ActivityScenario.launch<LazerpayActivity>(intent).use { scenario ->
            val lazerpayResult = LazerPayResult.Error(IllegalStateException())
            scenario.moveToState(Lifecycle.State.CREATED)
            scenario.onActivity { activity ->
                val testRegistry = getTestResultRegistry(lazerpayResult)
                val lazerSdk = LazerPaySdk.Builder(activity, TEST_DATA.publicKey, TEST_DATA.name, TEST_EMAIL, TEST_DATA.amount)
                    .activityResultRegistry(testRegistry)
                    .reference(TEST_DATA.reference!!)
                    .build()
                lazerSdk.initialize(resultListener)
                lazerSdk.charge()
            }
            scenario.moveToState(Lifecycle.State.RESUMED)
            scenario.onActivity {
                verify(resultListener).onError(lazerpayResult.exception)
            }
        }
    }

    @Test
    fun whenPaymentIsCancelled_invokesResultListenerOnCancelled() {
        val intent = Intent(appContext, LazerpayActivity::class.java).apply {
            putExtra(LazerpayActivity.EXTRA_DATA, TEST_DATA)
        }
        ActivityScenario.launch<LazerpayActivity>(intent).use { scenario ->
            scenario.moveToState(Lifecycle.State.CREATED)
            scenario.onActivity { activity ->
                val testRegistry = getTestResultRegistry(LazerPayResult.Cancel)
                val lazerSdk = LazerPaySdk.Builder(activity, TEST_DATA.publicKey, TEST_DATA.name, TEST_EMAIL, TEST_DATA.amount)
                    .activityResultRegistry(testRegistry)
                    .reference(TEST_DATA.reference!!)
                    .build()
                lazerSdk.initialize(resultListener)
                lazerSdk.charge()
            }
            scenario.moveToState(Lifecycle.State.RESUMED)
            scenario.onActivity {
                verify(resultListener).onCancelled()
            }
        }
    }

    private fun getTestResultRegistry(result: LazerPayResult): ActivityResultRegistry {
        return object : ActivityResultRegistry() {
            override fun <I, O> onLaunch(
                requestCode: Int,
                contract: ActivityResultContract<I, O>,
                input: I,
                options: ActivityOptionsCompat?
            ) {
                dispatchResult(requestCode, result)
            }
        }
    }

    companion object {
        const val TEST_EMAIL = "aregbestimi@gmail.com"

        val TEST_SUCCESSFUL_DATA = SuccessData(
            acceptPartialPayment = false,
            actualAmount = 8.16,
            amountPaid = 8.16,
            amountPaidFiat = 4998.0,
            amountReceived = 10,
            amountReceivedFiat = 6125,
            blockNumber = 20876941,
            blockchain = "Binance Smart Chain",
            coin = "USDT",
            createdAt = "2022-07-08T09:56:50.329Z",
            cryptoRate = 0.001633,
            currency = "NGN",
            customer = Customer(customerEmail = TEST_EMAIL, customerName = "Test User", customerPhone = null, id = "993cc1ab-1a88-4d0e-b93f-e262d8b51ae5"), feeInCrypto = 0.08,
            fiatAmount = 5000,
            fiatRate = 612.5,
            hash = "0xc849d6657bad356cef6f2752f524cbee54050f6096a2d75570ded9a10c99c697",
            id = "72bd2ead-e191-4dfc-867b-ddafe3237f08",
            network = "testnet", recipientAddress = "0x59b2b634973bE61d6AC9F93d5040486d69f4bFaF",
            reference = "123456", senderAddress = "0xaa25Aa7a19f9c426E07dee59b12f944f4d9f1DD3",
            status = "confirmed",
            type = "received", updatedAt = "2022-07-08T09:59:36.415Z"
        )

        val TEST_DATA = LazerPayData(
            publicKey = "pk_test_value",
            name = "Test User",
            email = TEST_EMAIL,
            amount = "5000", reference = "123456"
        )
    }
}
