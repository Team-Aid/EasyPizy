package com.example.capston4

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.capston4.databinding.ActivityInfomationBinding

class InfomationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfomationBinding


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfomationBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }



}