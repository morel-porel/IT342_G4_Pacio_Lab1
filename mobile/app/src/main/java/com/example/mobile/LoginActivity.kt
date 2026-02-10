package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mobile.api.RetrofitClient
import com.example.mobile.databinding.ActivityLoginBinding
import com.example.mobile.model.LoginRequest
import com.example.mobile.util.SessionManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(){
    private lateinit var binding:ActivityLoginBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        //Check if logged in
        if (!sessionManager.getToken().isNullOrEmpty()) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val user = binding.etUsername.text.toString()
            val pass = binding.etPassword.text.toString()

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Username and Password required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val res = RetrofitClient.instance.login(LoginRequest(user, pass))
                    if (res.isSuccessful && res.body() != null) {
                        val token = res.body()!!.token
                        sessionManager.saveToken(token)

                        Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                        finish() // Prevent going back to login with back button
                    } else {
                        Toast.makeText(this@LoginActivity, "Invalid Credentials", Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Connection Error: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.tvToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
