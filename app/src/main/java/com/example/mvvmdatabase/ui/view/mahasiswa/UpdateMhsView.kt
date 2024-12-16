package com.example.mvvmdatabase.ui.view.mahasiswa

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.ColumnInfo
import com.example.mvvmdatabase.ui.customwidget.TopAppBar
import com.example.mvvmdatabase.viemodel.PenyediaViewModel
import com.example.mvvmdatabase.viemodel.UpdateMhsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UpdateMhsView(
    onBack: () -> Unit = { },
    onNavigate: () -> Unit = { },
    modifier: Modifier = Modifier,
    viewModel: UpdateMhsViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.updateUIState // Ambil UI State Dari ViewModel
    val snackBarHostState = remember { SnackbarHostState() } // Ambil Snackbar Host State Dari ViewModel
    val coroutineScope = rememberCoroutineScope() // Ambil Coroutine Scope Dari ViewModel

    // Observasi Perubahan Snackbar Message Dari ViewModel

    LaunchedEffect(uiState.snackbarMessage) {
        println("LaunchedEffect Triggered")
        uiState.snackbarMessage?.let { message ->
            println("Snackbar Message Received: $message")
            coroutineScope.launch {
                println("Launching Coroutine For Snackbar")
                snackBarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState)},
        topBar = {
            TopAppBar(
                judul = "Edit Mahasiswa",
                showBackButton = true,
                onBack = onBack
            )
        }
    ){
        padding ->
        Column (
            modifier = Modifier.padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ){
            InsertBodyMhs(
                uiState = uiState,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent) // Update State Pada ViewModel
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()) {
                            viewModel.updateData()
                            delay(600)
                            withContext(Dispatchers.Main) {
                                onNavigate() // Navigasi Di Main Thread
                            }
                        }
                    }
                }
            )
        }
    }
}