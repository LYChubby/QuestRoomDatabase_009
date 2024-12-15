package com.example.mvvmdatabase.viemodel

import com.example.mvvmdatabase.data.entity.Mahasiswa

data class DetailUiState(
    val detailUiEvent: MahasiswaEvent = MahasiswaEvent(),
    val isLoadings: Boolean = false,
    val isError: Boolean = false,
    val errorMessages: String = ""
) {
    val isUiEventEmpty: Boolean
    get() = detailUiEvent == MahasiswaEvent()

    val isUiEventNotEmpty: Boolean
    get() = detailUiEvent != MahasiswaEvent()
}

// Data Class Untuk Menampung Data Yang Akan Ditampilkan Di UI

// Memindahkan Data Dari Entity Ke UI

fun Mahasiswa.toDetailUiState(): MahasiswaEvent {
    return MahasiswaEvent(
        nim = nim,
        nama = nama,
        jenisKelamin = jenisKelamin,
        alamat = alamat,
        kelas = kelas,
        angkatan = angkatan
    )
}