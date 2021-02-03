package com.harvdev.codeatnightt.ui.barang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.harvdev.codeatnightt.R
import com.harvdev.codeatnightt.adapter.SemuaBarangAdapter
import com.harvdev.codeatnightt.models.Barang
import com.harvdev.codeatnightt.network.BarangApi
import kotlinx.android.synthetic.main.activity_semua_barang.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SemuaBarangActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_semua_barang)

        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        refreshLayout.setOnRefreshListener {
            fetchBarang("")
        }

        fetchBarang("")
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val item = menu!!.findItem(R.id.action_search)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                fetchBarang(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun fetchBarang(query: String){
        shimmerlayoutSemuabarang.visibility= View.VISIBLE
        shimmerlayoutSemuabarang.startShimmerAnimation()
        refreshLayout.isRefreshing = true
        BarangApi()
            .getCari(query).enqueue(object: Callback<List<Barang>> {
                override fun onFailure(call: Call<List<Barang>>, t: Throwable) {
                    refreshLayout.isRefreshing = false
                    Toast.makeText(this@SemuaBarangActivity, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<List<Barang>>, response: Response<List<Barang>>) {
                    shimmerlayoutSemuabarang.visibility= View.GONE
                    refreshLayout.isRefreshing = false
                    val barangs = response.body()
                    barangs?.let {
                        showBarang(it)
                    }
                }

            })
    }

    private fun showBarang(barangs: List<Barang>){

        recyclerView.layoutManager = GridLayoutManager(this@SemuaBarangActivity, 2)
        recyclerView.adapter =
            SemuaBarangAdapter(barangs)
    }
}