package com.example.suitmediaapp.view

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.suitmediaapp.R
import com.example.suitmediaapp.data.response.ApiResponse
import com.example.suitmediaapp.data.response.DataItem
import com.example.suitmediaapp.data.retrofit.ApiConfig
import com.example.suitmediaapp.databinding.ActivityScreenThreeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScreenThreeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScreenThreeBinding
    private lateinit var viewModel: ScreenViewModel
    private var currentPage = 1
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

         // set layout untuk recycle view
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        // Set up pull-to-refresh
        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            currentPage += 1 // increment current page
            getUser() // mengambil data
            swipeRefreshLayout.isRefreshing = false
        }

        // megnambil data
        getUser()

        // ketika tombol kembali ditekan
        binding.arrowBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getUser() {
        showLoading(true)
        val client = ApiConfig.getApiService().getUsers(currentPage)
        client.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) setUserData(responseBody.data)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    // melakukan set recycle view dari data yang sudah didapat
    private fun setUserData(items: List<DataItem>) {
        if (items.isEmpty()){
            // jika data kosong
            binding.txtDataEmpty.text = getString(R.string.no_data_found)
            binding.rvUser.adapter = null
        }else{
            // jika data tidak kosong maka akan ditampilkan
            val adapter = UserAdapter()
            adapter.submitList(items)
            binding.rvUser.adapter = adapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}