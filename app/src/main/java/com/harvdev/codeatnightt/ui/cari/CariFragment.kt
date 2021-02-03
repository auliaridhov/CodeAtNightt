package com.harvdev.codeatnightt.ui.cari

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.harvdev.codeatnightt.R
import com.harvdev.codeatnightt.adapter.SemuaBarangAdapter
import com.harvdev.codeatnightt.models.Barang
import com.harvdev.codeatnightt.network.BarangApi
import kotlinx.android.synthetic.main.fragment_cari.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CariFragment : Fragment() {

    private lateinit var root: View

    companion object {
        fun newInstance() = CariFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_cari, container, false)

        (activity as AppCompatActivity?)!!.setSupportActionBar(root.toolbarCari)

        root.refreshLayoutCari.setOnRefreshListener {
            fetchBarang("")
        }

        fetchBarang("")


        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
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
        return super.onCreateOptionsMenu(menu, inflater)

    }

    private fun fetchBarang(query: String){
        root.shimmerlayoutCari.startShimmerAnimation()
        root.shimmerlayoutCari.visibility = View.VISIBLE
        root.refreshLayoutCari.isRefreshing = true
        BarangApi()
            .getCari(query).enqueue(object: Callback<List<Barang>> {
                override fun onFailure(call: Call<List<Barang>>, t: Throwable) {
                    root.refreshLayoutCari.isRefreshing = false
                    Toast.makeText(activity, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<List<Barang>>, response: Response<List<Barang>>) {
                    root.shimmerlayoutCari.visibility = View.GONE
                    root.refreshLayoutCari.isRefreshing = false
                    val barangs = response.body()
                    barangs?.let {
                        showBarang(it)
                    }
                }

            })
    }

    private fun showBarang(barangs: List<Barang>){
        root.recyclerViewCari.layoutManager = GridLayoutManager(activity, 2)
        root.recyclerViewCari.adapter =
            SemuaBarangAdapter(barangs)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }
}