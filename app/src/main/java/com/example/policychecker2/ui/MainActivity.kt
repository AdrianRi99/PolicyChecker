package com.example.policychecker2.ui

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.policychecker2.R
import com.example.policychecker2.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        supportActionBar!!.title = "PolicyChecker"

//        Animated Background
//        val animationDrawable: AnimationDrawable = binding.mainLayout.background as AnimationDrawable
//        animationDrawable.setEnterFadeDuration(2500)
//        animationDrawable.setExitFadeDuration(5000)
//        animationDrawable.start()
    }
}