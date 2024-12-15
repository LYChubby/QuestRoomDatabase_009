package com.example.mvvmdatabase.viemodel

import com.example.mvvmdatabase.data.entity.Mahasiswa

data class HomeUiState(
    val listMhs: List<Mahasiswa> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessages: String = ""
)