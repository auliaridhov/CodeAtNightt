package com.harvdev.codeatnightt.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.harvdev.codeatnightt.R
import com.harvdev.codeatnightt.db.BarangDatabase
import com.harvdev.codeatnightt.db.BarangDb
import com.harvdev.codeatnightt.models.ResponsModel
import com.harvdev.codeatnightt.network.BASE_URL_FILE
import com.harvdev.codeatnightt.network.BarangApi
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

class DetailActivity : BaseActivity() {
    var idBarang: Int = 0
    var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        id = intent.getStringExtra("id").toString()

        back.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

        fav.visibility = View.GONE
        unfav.visibility = View.GONE

        fetchBarang()


    }

    fun fetchBarang(){
        progresBar.visibility = View.VISIBLE
        BarangApi()
            .getDetailBarang(id).enqueue(object: Callback<ResponsModel> {
                override fun onFailure(call: Call<ResponsModel>, t: Throwable) {
                    progresBar.visibility = View.GONE
                    Toast.makeText(this@DetailActivity, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<ResponsModel>,
                    response: Response<ResponsModel>
                ) {
                    progresBar.visibility = View.GONE
                    val respon = response.body()
                    if (respon != null) {
                        namaBarangDetail.text = respon.barang.nama
                        hargaBarangDetail.text = Converter.rupiah(respon.barang.harga.toDouble())
                        ukuranBarangDetail.text = respon.barang.ukuran
                        ketBarangDetail.text = respon.barang.ket
                        idBarang = respon.barang.id.toInt()

                        Glide.with(this@DetailActivity)
                            .load(BASE_URL_FILE +respon.barang.gambar)
                            .into(imageBarangDetail)

                        btnBeliSkrg.setOnClickListener(View.OnClickListener {
                            val url = "https://api.whatsapp.com/send?phone=6283169077441&text=Hallo%20kak%20barang%20dengan%20nama%20"+respon.barang.nama+"%20masih%20ada%20tidak%20yaa%3F"
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(url)
                            startActivity(i)
                        })

                        //cekFavorit(idBarang)

                        this@DetailActivity.let {
                            launch {
                                if (BarangDatabase(it).getBarangDao().isFavorit(idBarang)){
                                    unfav.visibility = View.VISIBLE
                                    fav.visibility = View.GONE
                                } else {
                                    fav.visibility = View.VISIBLE
                                    unfav.visibility = View.GONE
                                }
                            }
                        }

                        fav.setOnClickListener{
                            this@DetailActivity.let {
                                launch {
                                    val barang = BarangDb(respon.barang.id, respon.barang.nama, respon.barang.harga, respon.barang.gambar)
                                    BarangDatabase(it).getBarangDao().addFav(barang)
                                    Toast.makeText(this@DetailActivity, "Disimpan di favorit", Toast.LENGTH_LONG).show()
                                }
                                fetchBarang()
                            }

                        }

                        unfav.setOnClickListener{
                            this@DetailActivity.let {
                                launch {
                                    BarangDatabase(it).getBarangDao().deleteByIdBarang(idBarang)
                                    Toast.makeText(this@DetailActivity, "Dihapus dari favorit", Toast.LENGTH_LONG).show()
                                }
                                fetchBarang()
                            }

                        }

                    }

                }

            })
    }

    class Converter {
        companion object {
            fun rupiah(number: Double): String{
                val localeID =  Locale("in", "ID")
                val numberFormat = NumberFormat.getCurrencyInstance(localeID)
                return numberFormat.format(number).toString()
            }
        }
    }

//    fun cekFavorit(idBarang: Int){
//        class cekFav : AsyncTask<Void, Void, Void>(){
//            override fun doInBackground(vararg p0: Void?): Void? {
//
//                runOnUiThread {
//                    if (BarangDatabase(this@DetailActivity).getBarangDao().isFavorit(idBarang)){
//                        fav.visibility = View.GONE
//                        unfav.visibility = View.VISIBLE
//                    } else {
//                        fav.visibility = View.VISIBLE
//                        unfav.visibility = View.GONE
//                    }
//                }
//
//                return null
//
//            }
//
//            override fun onPostExecute(result: Void?) {
//                super.onPostExecute(result)
//                Toast.makeText(this@DetailActivity, "Fav Check", Toast.LENGTH_LONG).show()
//            }
//
//
//
//        }
//        cekFav().execute()
//    }


}