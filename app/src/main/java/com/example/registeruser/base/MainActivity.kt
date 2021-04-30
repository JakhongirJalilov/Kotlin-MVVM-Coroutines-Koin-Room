package com.example.registeruser.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.registeruser.databinding.ActivityMainBinding
import com.example.registeruser.fragments.RegisterUserFragment
import com.example.registeruser.fragments.UserInfoFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.register.setOnClickListener {
            val d = RegisterUserFragment()
            this.supportFragmentManager.let { it1 -> d.show(it1, "register_user") }
        }

        binding.userInfo.setOnClickListener {
            val d = UserInfoFragment()
            this.supportFragmentManager.let { it1 -> d.show(it1, "user_info") }
        }
    }
}