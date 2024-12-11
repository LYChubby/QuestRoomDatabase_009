package com.example.mvvmdatabase.viemodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmdatabase.data.entity.Mahasiswa
import com.example.mvvmdatabase.repository.RepositoryMhs
import kotlinx.coroutines.launch

// Data Class Variabel Yang Menyimpan
// Data Input Form
data class MahasiswaEvent(
    val nim: String = "",
    val nama: String = "",
    val jenisKelamin: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val angkatan: String = ""
)

// Menyimpan Input Form Ke Dalam Entity
fun MahasiswaEvent.toMahasiswaEntity(): Mahasiswa = Mahasiswa(
        nim = nim,
        nama = nama,
        jenisKelamin = jenisKelamin,
        alamat = alamat,
        kelas = kelas,
        angkatan = angkatan
)

data class FormErrorState(
    val nim: String? = null,
    val nama: String? = null,
    val jenisKelamin: String? = null,
    val alamat: String? = null,
    val kelas: String? = null,
    val angkatan: String? = null
) {
    fun isValid(): Boolean {
        return nim == null && nama == null && jenisKelamin == null &&
                alamat == null && kelas == null && angkatan == null
    }
}

data class MhsUIState(
    val mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackbarMessage: String? = null,
)

class MahasiswaViewModel(
    private val repositoryMhs: RepositoryMhs
) : ViewModel() {

    var uiState by mutableStateOf(MhsUIState())

    // Memperbarui State Berdasarkan Input Pengguna
    fun updateState(mahasiswaEvent: MahasiswaEvent) {
        uiState = uiState.copy(
            mahasiswaEvent = mahasiswaEvent,
        )
    }

    // Validasi Data Input Pengguna
    private fun validateFields(): Boolean {
        val event = uiState.mahasiswaEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isEmpty()) "NIM Tidak Boleh Kosong" else null,
            nama = if (event.nama.isEmpty()) "Nama Tidak Boleh Kosong" else null,
            jenisKelamin = if (event.jenisKelamin.isEmpty()) "Jenis Kelamin Tidak Boleh Kosong" else null,
            alamat = if (event.alamat.isEmpty()) "Alamat Tidak Boleh Kosong" else null,
            kelas = if (event.kelas.isEmpty()) "Kelas Tidak Boleh Kosong" else null,
            angkatan = if (event.angkatan.isEmpty()) "Angkatan Tidak Boleh Kosong" else null
        )

        uiState = uiState.copy(
            isEntryValid = errorState
        )

        return errorState.isValid()
    }

    // Menyimpan Data Mahasiswa Ke Repository
    fun saveData() {
        val currentEvent = uiState.mahasiswaEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMhs.insertMhs(currentEvent.toMahasiswaEntity())
                    uiState = uiState.copy(
                        snackbarMessage = "Data Berhasil Disimpan",
                        mahasiswaEvent = MahasiswaEvent(), // Reset Input Form
                        isEntryValid = FormErrorState() // Reset Error State
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackbarMessage = "Data Gagal Disimpan"
                    )
                }
            }
        } else  {
            uiState = uiState.copy(
                snackbarMessage = "Input Tidak Valid. Periksa Kembali Data Anda"
            )
        }
    }

    // Reset Pesan Snackbar Setelah Ditampilkan
    fun resetSnackbarMessage() {
        uiState = uiState.copy(
            snackbarMessage = null
        )
    }
}