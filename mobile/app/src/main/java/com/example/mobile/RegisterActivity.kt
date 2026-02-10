package com.example.mobile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mobile.api.RetrofitClient
import com.example.mobile.databinding.ActivityLoginBinding
import com.example.mobile.databinding.ActivityRegisterBinding
import com.example.mobile.model.RegisterRequest
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity(){

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegister.setOnClickListener {
            val user = binding.etRegUser.text.toString()
            val email = binding.etRegEmail.text.toString()
            val first = binding.etRegFirst.text.toString()
            val last = binding.etRegLast.text.toString()
            val pass = binding.etRegPass.text.toString()
            if (user.isEmpty()||pass.isEmpty()){
                Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show()
            }
            val req = RegisterRequest(
                username = user,
                email = email,
                firstName = first,
                lastName = last,
                password = pass
            )
            lifecycleScope.launch {
                try {
                    val res = RetrofitClient.instance.register(req)
                    if (res.isSuccessful) {
                        Toast.makeText(this@RegisterActivity, "Success", Toast.LENGTH_SHORT).show()
                        finish() // Goes back to Login
                    } else {
                        Toast.makeText(this@RegisterActivity, "Error: ${res.code()}", Toast.LENGTH_SHORT).show()

                    }
                }catch (e: Exception){
                    Toast.makeText(this@RegisterActivity, "Connection Error: ${e.message}", Toast.LENGTH_SHORT).show()

                }
            }
        }
        binding.tvToLogin.setOnClickListener {
            finish() // Return to Login Activity
        }
    }
}