package com.example.foodrecipes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodrecipes.Api.Apiinterface
import com.example.foodrecipes.Api.BuiledService
import com.example.foodrecipes.Model.LoginData
import com.example.foodrecipes.Model.Response
import com.example.foodrecipes.databinding.ActivityLoginBinding
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import retrofit2.Call
import retrofit2.Callback

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("srt", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        binding.singup.setOnClickListener {
            val intent = Intent(this,RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.btnLogin.setOnClickListener {
            val email = binding.edtemail.text.toString()
            val password = binding.edtpassword.text.toString()

            binding.progressbarlogin.visibility= View.VISIBLE
            
            if (validation(email,password)){
                requestdata(email,password)

                editor.putInt("sta",1)
                editor.putString("email",email)
                editor.putString("password",password)
                editor.apply()

                val intent = Intent(this@LoginActivity,Drawer_Layout_Activity::class.java)
                intent.putExtra("email",email)
                startActivity(intent)
                finish()

            }
            else{
                binding.progressbarlogin.visibility= View.GONE
                Toast.makeText(this@LoginActivity, "Please Enter your detailes",Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun requestdata(email: String, password: String) {
        val obj = LoginData(email,password)
        val retrofit = BuiledService.serviceBuilder(Apiinterface::class.java)
        retrofit.requestdata(obj).enqueue(object : Callback<Response>{
            override fun onResponse(call: Call<Response>, responsse:retrofit2.Response<Response>) {
                binding.progressbarlogin.visibility = View.GONE
                if (responsse.isSuccessful) {
                    val data = responsse.body()?.error
                    if (data == null) {
                        val intent = Intent(this@LoginActivity, Drawer_Layout_Activity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "${data}", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(p0: Call<Response>, t: Throwable) {
                binding.progressbarlogin.visibility= View.GONE
               Toast.makeText(this@LoginActivity,t.message,Toast.LENGTH_LONG).show()
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