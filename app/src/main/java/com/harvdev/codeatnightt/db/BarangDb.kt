package com.harvdev.codeatnightt.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class BarangDb(
    val idBarang: String,
    val nama: String,
    val harga: String,
    val gambar: String
):Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}