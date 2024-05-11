package com.app.chat.retrofitwithroomdatabase

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.chat.retrofitwithroomdatabase.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: Adapter
    private val productList = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressbar.visibility = View.VISIBLE

        adapter = Adapter(productList)
        binding.recyclerview.adapter = adapter

        loadFromDatabase()

        loadDataFromNetwork()
    }

    private fun loadFromDatabase() {
        GlobalScope.launch(Dispatchers.IO) {
            val products = MyDatabase.getDatabase(this@MainActivity).productDao().readalldata()
            withContext(Dispatchers.Main) {
                productList.clear()
                productList.addAll(products)
                adapter.notifyDataSetChanged()
                binding.progressbar.visibility = View.GONE
            }
        }
    }

    private fun loadDataFromNetwork() {
        val retrofit = BuildService.serviceBuilder(ApiInterface::class.java)
        retrofit.getdataproduct().enqueue(object : Callback<Prodectlist> {
            override fun onResponse(call: Call<Prodectlist>, response: Response<Prodectlist>) {
                binding.progressbar.visibility = View.GONE
                if (response.isSuccessful) {
                    val data = response.body()?.products
                    data?.let {
                        GlobalScope.launch(Dispatchers.IO) {
                            MyDatabase.getDatabase(this@MainActivity).productDao().insertdata(it)
                            withContext(Dispatchers.Main) {
                                loadFromDatabase()
                            }
                        }
                    }
                }
                else {
                    Toast.makeText(this@MainActivity, "Wrong Data", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Prodectlist>, t: Throwable) {
                binding.progressbar.visibility = View.GONE
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
