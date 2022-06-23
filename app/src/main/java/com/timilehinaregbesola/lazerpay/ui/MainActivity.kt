package com.timilehinaregbesola.lazerpay.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.timilehinaregbesola.lazerpay.LazerPayCheckout
import com.timilehinaregbesola.lazerpay.LazerPayResultListener
import com.timilehinaregbesola.lazerpay.databinding.ActivityMainBinding
import com.timilehinaregbesola.lazerpay.model.SuccessData

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goToLazerpayWebView()
//        binding.btnLazerpay.setOnClickListener { goToLazerpayWebView() }
    }

    private fun goToLazerpayWebView() {
        val resultListener = object : LazerPayResultListener {
            override fun onSuccess(result: SuccessData) {
                Toast.makeText(
                    this@MainActivity,
                    "Transaction complete - Ref: ${result.reference}",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onError(exception: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Transaction failed",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onCancelled() {
                Toast.makeText(
                    this@MainActivity,
                    "Transaction cancelled by user",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        val lazerpayBuilder = LazerPayCheckout.Builder(
            activity = this,
            name = "Frankie De Jong",
            email = "zuzuzeh@mail.com",
            amount = "50000",
            publicKey = "pk_test_LIfI1h8BvlW25UMxGQQCzgSula1MnrdVY7T5TcbOEKIh5uue36"
        )

        lazerpayBuilder.apply {
            reference("X2b8hV55l0435t354541")
            businessLogo("https://securecdn.pymnts.com/wp-content/uploads/2021/12/stablecoins.jpg")
        }

        val checkout = lazerpayBuilder.build()
        checkout.charge(resultListener)
    }
}
