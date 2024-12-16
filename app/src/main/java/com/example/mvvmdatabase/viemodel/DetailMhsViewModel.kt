package com.example.mvvmdatabase.viemodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmdatabase.data.entity.Mahasiswa
import com.example.mvvmdatabase.repository.RepositoryMhs
import com.example.mvvmdatabase.ui.navigation.DestinasiDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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

fun Mahasiswa.toDetailUiEvent(): MahasiswaEvent {
    return MahasiswaEvent(
        nim = nim,
        nama = nama,
        jenisKelamin = jenisKelamin,
        alamat = alamat,
        kelas = kelas,
        angkatan = angkatan
    )
}

class DetailMhsViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryMhs: RepositoryMhs,
) : ViewModel() {

    private val _nim: String = checkNotNull(savedStateHandle[DestinasiDetail.NIM])

    val detailUiState: StateFlow<DetailUiState> = repositoryMhs.getMhs(_nim)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoadings = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoadings = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiState(
                    isLoadings = false,
                    isError = true,
                    errorMessages = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoadings = true,
            ),
        )

    fun deleteMhs() {
        detailUiState.value.detailUiEvent.toMahasiswaEntity().let {
            viewModelScope.launch {
                repositoryMhs.deleteMhs(it)
            }
        }
    }
}