package com.harvdev.codeatnightt.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harvdev.codeatnightt.R
import com.harvdev.codeatnightt.models.Barang
import com.harvdev.codeatnightt.network.BASE_URL_FILE
import com.harvdev.codeatnightt.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.item_barang.view.*
import kotlinx.android.synthetic.main.item_placeholder_layout.view.*
import java.text.NumberFormat
import java.util.*

class BarangAdapter(val barangs : List<Barang>) : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        return BarangViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_barang_fix,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = barangs.size

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val barang = barangs[position]

        holder.view.namaBarang.text = barang.nama
        holder.view.hargaBarang.text = Converter.rupiah(barang.harga.toDouble())

        holder.view.cvAdapterFix.setOnClickListener(View.OnClickListener {
            val context = holder.view.context
            val intent = Intent(context, DetailActivity::class.java)
            // To pass any data to next activity
            intent.putExtra("id", barang.id)
            // start your next activity
            context.startActivity(intent)
        })

        Glide.with(holder.view.context)
            .load(BASE_URL_FILE +barang.gambar)
            .into(holder.view.imageBarang)
    }


    class BarangViewHolder(val view: View) : RecyclerView.ViewHolder(view)


    class Converter {
        companion object {
            fun rupiah(number: Double): String{
                val localeID =  Locale("in", "ID")
                val numberFormat = NumberFormat.getCurrencyInstance(localeID)
                return numberFormat.format(number).toString()
            }
        }
    }
}