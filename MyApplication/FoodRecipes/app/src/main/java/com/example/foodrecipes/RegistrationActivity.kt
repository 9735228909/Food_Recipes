package com.example.foodrecipes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodrecipes.Api.Apiinterface
import com.example.foodrecipes.Api.BuiledService
import com.example.foodrecipes.Model.LoginData
import com.example.foodrecipes.Model.Response
import com.example.foodrecipes.databinding.ActivityRegistrationBinding
import retrofit2.Call
import retrofit2.Callback

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.login.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnsignup.setOnClickListener {
            val email = binding.edtemail.text.toString()
            val password = binding.edtpassword.text.toString()

            binding.progressbarsignup.visibility= View.VISIBLE

            if (validation(email,password)){
                requestdata(email,password)
                val intent = Intent(this@RegistrationActivity,LoginActivity::class.java)
                startActivity(intent)
                finish()

            }
            else{
                binding.progressbarsignup.visibility= View.GONE
                Toast.makeText(this@RegistrationActivity, "Please Enter your detailes", Toast.LENGTH_LONG).show()
            }
        }


    }

        private fun requestdata(email: String, password: String) {
        val obj = LoginData(email,password)
        val retrofit = BuiledService.serviceBuilder(Apiinterface::class.java)
        retrofit.requestdata(obj).enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, responsse:retrofit2.Response<Response>) {
                binding.progressbarsignup.visibility= View.GONE
                if (responsse.isSuccessful) {
                    val data = responsse.body()?.error
                    if (data == null) {
                        val intent =
                            Intent(this@RegistrationActivity, Drawer_Layout_Activity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@RegistrationActivity, "${data}", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
            override fun onFailure(p0: Call<Response>, t: Throwable) {
                binding.progressbarsignup.visibility= View.GONE
               Toast.makeText(this@RegistrationActivity,t.message,Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun validation(email: String, password: String): Boolean {
        if (email.isBlank() || password.isBlank()){
            if (email.isBlank()){
                binding.edtemail.error = "Please Enter your Email"
            }
            if (password.isBlank()){
                binding.edtpassword.error = "Please Enter your Email"
            }
            return false
        }
        return true
    }
}