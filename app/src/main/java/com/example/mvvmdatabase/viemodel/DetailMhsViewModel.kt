package com.example.mvvmdatabase.viemodel

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