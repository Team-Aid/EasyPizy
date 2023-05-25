package com.example.easypizy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.easypizy.databinding.ActivityMainBinding
import com.example.easypizy.presentation.Home
import com.example.easypizy.presentation.Page
import com.example.easypizy.presentation.NaverFragment2

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())




        binding.bottomNavigationView.setOnItemSelectedListener {


            when(it.itemId){
                R.id.home-> replaceFragment(Home())
                R.id.map-> startActivity(Intent(this, NaverFragment2::class.java))
                R.id.page-> replaceFragment(Page())

                else->{

                }
            }
            true
        }


    }




    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()

    }
}

