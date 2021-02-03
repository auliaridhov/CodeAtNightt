package com.harvdev.codeatnightt.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.harvdev.codeatnightt.db.BarangDb

@Dao
interface BarangDao {

    @Insert
    suspend fun addFav(barangDb: BarangDb)

    @Query("SELECT * FROM barangdb ORDER BY id DESC")
    suspend fun getAllFav(): List<BarangDb>

    @Delete
    suspend fun deleteBarang(barangDb: BarangDb)

    @Query("DELETE FROM barangdb WHERE idBarang = :idBarang")
    suspend fun deleteByIdBarang(idBarang: Int)

    @Query("SELECT EXISTS(SELECT * FROM barangdb WHERE idBarang = :idBarang)")
    suspend fun isFavorit(idBarang : Int) : Boolean

}