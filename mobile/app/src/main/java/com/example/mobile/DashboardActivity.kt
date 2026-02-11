package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mobile.api.RetrofitClient
import com.example.mobile.databinding.ActivityDashboardBinding
import com.example.mobile.util.SessionManager
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        val token = sessionManager.getToken()

        if (token.isNullOrEmpty()) {
            logout()
            return
        }

        fetchProfile(token)

        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun fetchProfile(token: String) {
        lifecycleScope.launch {
            try {
                // Backend requires "Bearer <token>"
                val res = RetrofitClient.instance.getProfile("Bearer $token")
                if (res.isSuccessful && res.body() != null) {
                    val user = res.body()!!
                    binding.tvWelcome.text = "Welcome to the Dashboard, ${user.username}!"
                    binding.tvUsername.text = "Username: ${user.username }"
                    binding.tvEmail.text = "Email: ${user.email}"
                    binding.tvfirstName.text = "First Name: ${user.firstName}"
                    binding.tvlastName.text = "Last Name: ${user.lastName}"
                } else {
                    Toast.makeText(this@DashboardActivity, "Session Expired", Toast.LENGTH_SHORT).show()
                    logout()
                }
            } catch (e: Exception) {
                Toast.makeText(this@DashboardActivity, "Error fetching profile", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun logout() {
        sessionManager.logout()
        Toast.makeText(this, "You have been logged out successfully", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
