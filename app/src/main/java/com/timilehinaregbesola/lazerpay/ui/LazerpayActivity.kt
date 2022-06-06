package com.timilehinaregbesola.lazerpay.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timilehinaregbesola.lazerpay.databinding.ActivityLazerpayBinding

class LazerpayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLazerpayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLazerpayBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
