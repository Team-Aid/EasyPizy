package com.example.capston4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capston4.databinding.ActivityInfomationBinding

class InfomationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfomationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfomationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<SmokeArea>("data")

        binding.locaTextView.text = data?.areaName


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }


}