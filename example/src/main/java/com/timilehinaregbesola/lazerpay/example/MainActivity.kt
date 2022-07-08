package com.timilehinaregbesola.lazerpay.example

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.timilehinaregbesola.lazerpay.LazerPayResultListener
import com.timilehinaregbesola.lazerpay.LazerPaySdk
import com.timilehinaregbesola.lazerpay.example.databinding.ActivityMainBinding
import com.timilehinaregbesola.lazerpay.model.SuccessData

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var lazerPaySdk: LazerPaySdk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLazerPaySdk()

        binding.btnLazerpay.setOnClickListener { lazerPaySdk.charge() }
    }

    private fun setupLazerPaySdk() {
        val resultListener = object : LazerPayResultListener {
            override fun onSuccess(result: SuccessData) {
                Toast.makeText(
                    this@MainActivity,
                    "Transaction complete - Ref: ${result.reference}",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("Success", result.toString())
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
        val lazerpayBuilder = LazerPaySdk.Builder(
            activity = this,
            name = "Frankie De Jong",
            email = "zuzuzeh@mail.com",
            amount = "5000",
            publicKey = "pk_test_LIfI1h8BvlW25UMxGQQCzgSula1MnrdVY7T5TcbOEKIh5uue36"
        )

        lazerpayBuilder.apply {
            reference("Kb8hV535l03t354540")
            businessLogo("https://securecdn.pymnts.com/wp-content/uploads/2021/12/stablecoins.jpg")
        }
        lazerPaySdk = lazerpayBuilder.build()
        lazerPaySdk.initialize(resultListener)
    }
}
