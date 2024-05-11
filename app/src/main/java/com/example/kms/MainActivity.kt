package com.example.kms

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kms.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        actionBar?.hide()

        setContentView(binding.root)
    }
}