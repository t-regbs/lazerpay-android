package com.timilehinaregbesola.lazerpay.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timilehinaregbesola.lazerpay.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLazerpay.setOnClickListener { goToLazerpayWebView() }
    }

    private fun goToLazerpayWebView() {
        val i = Intent(applicationContext, LazerpayActivity::class.java)
        startActivity(i)
    }
}
