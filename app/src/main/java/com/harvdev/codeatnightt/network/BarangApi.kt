package com.harvdev.codeatnightt.network

import com.harvdev.codeatnightt.models.Barang
import com.harvdev.codeatnightt.models.ResponsModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "http://192.168.100.9/codeadmin/api/"

const val BASE_URL_FILE = "http://192.168.100.9/codeadmin/"

interface BarangApi {

    @GET("readbarang.php")
    fun getBarang( @Query("kategori") kategori:String
    ) : Call<List<Barang>>

    @GET("cari.php")
    fun getCari( @Query("query") query:String
    ) : Call<List<Barang>>

    @GET("new.php")
    fun getNew( @Query("kategori") kategori:String
    ) : Call<List<Barang>>

    @GET("detailbarang.php")
    fun getDetailBarang( @Query("id") id:String
    ) : Call<ResponsModel>

    companion object{
        operator fun invoke() : BarangApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BarangApi::class.java)
        }
    }

}