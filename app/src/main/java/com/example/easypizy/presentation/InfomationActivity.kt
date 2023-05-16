package com.example.easypizy.presentation

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.easypizy.databinding.ActivityInfomationBinding

class InfomationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfomationBinding


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfomationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val text = findViewById<TextView>(com.example.easypizy.R.id.titleTextView1)
        val text2 = findViewById<TextView>(com.example.easypizy.R.id.locaTextView)



        supportActionBar?.title = "흡연구역 정보"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



    }



}