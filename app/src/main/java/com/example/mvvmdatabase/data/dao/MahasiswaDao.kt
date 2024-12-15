package com.example.mvvmdatabase.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mvvmdatabase.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow

@Dao
interface MahasiswaDao {
    @Insert
    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)

    @Update
    suspend fun updateMahasiswa(mahasiswa: Mahasiswa)

    @Delete
    suspend fun deleteMahasiswa(mahasiswa: Mahasiswa)


}