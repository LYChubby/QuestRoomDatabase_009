package com.example.mvvmdatabase.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvmdatabase.data.dao.MahasiswaDao
import com.example.mvvmdatabase.data.entity.Mahasiswa

@Database(entities = [Mahasiswa::class], version = 1, exportSchema = false)
abstract class KrsDatabase : RoomDatabase() {

    // Deklarasi fungsi abstrak untuk Mengakses Data Mahasiswa
    abstract fun mahasiswaDao(): MahasiswaDao

    companion object {
        @Volatile // Memastikan bahwa Nilai Variabel Instance Selalu Sama Disemua
        private var Instance: KrsDatabase? = null

        fun getDatabase(context: Context): KrsDatabase {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    KrsDatabase::class.java,  // Class Database
                    "krsDatabase"      // Nama Database
                )
                    .build().also { Instance = it }
            })
        }
    }
}