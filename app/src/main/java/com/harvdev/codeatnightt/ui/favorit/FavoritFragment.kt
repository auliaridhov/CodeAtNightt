package com.harvdev.codeatnightt.ui.favorit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.harvdev.codeatnightt.R
import com.harvdev.codeatnightt.adapter.BarangDbAdapter
import com.harvdev.codeatnightt.db.BarangDatabase
import kotlinx.android.synthetic.main.fragment_favorit.*
import kotlinx.coroutines.launch

class FavoritFragment : BaseFragment() {

    private lateinit var favoritViewModel: FavoritViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoritViewModel =
            ViewModelProviders.of(this).get(FavoritViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_favorit, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycle_view_barang.setHasFixedSize(true)
        recycle_view_barang.layoutManager = GridLayoutManager(activity, 2)

        launch {
            context?.let {
                val barangs = BarangDatabase(it).getBarangDao().getAllFav()
                recycle_view_barang.adapter = BarangDbAdapter(barangs)
            }

        }
    }
}