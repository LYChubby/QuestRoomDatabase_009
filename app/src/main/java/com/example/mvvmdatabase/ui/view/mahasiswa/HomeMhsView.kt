package com.example.mvvmdatabase.ui.view.mahasiswa

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mvvmdatabase.data.entity.Mahasiswa
import com.example.mvvmdatabase.ui.customwidget.TopAppBar
import com.example.mvvmdatabase.viemodel.HomeMhsViewModel
import com.example.mvvmdatabase.viemodel.HomeUiState
import com.example.mvvmdatabase.viemodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn (ExperimentalMaterial3Api::class)
@Composable
fun CardMhs (
    mhs: Mahasiswa,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick
    ) {
        Column (
            modifier = Modifier.padding(8.dp)

        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = mhs.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = mhs.nim,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = mhs.kelas,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
fun ListMahasiswa(
    listMhs: List<Mahasiswa>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = listMhs,
            itemContent = { mhs ->
                CardMhs(
                    mhs = mhs,
                    onClick = { onClick(mhs.nim) }
                )
            }
        )
    }
}

@Composable
fun BodyHomeMhsView(
    homeUiState: HomeUiState,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    when {
        homeUiState.isLoading -> {
            // Menampilkan Indikator Loading
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        homeUiState.isError -> {
            // Menampilkan Snackbar jika terjadi kesalahan
            LaunchedEffect(homeUiState.errorMessages) {
                homeUiState.errorMessages?.let { message ->
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(message) // Tampilkan Snackbar
                    }
                }
            }
        }

        homeUiState.listMhs.isEmpty() -> {
            // Menampilkan pesan jika daftar mahasiswa kosong
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak Ada Data Mahasiswa",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            // Menampilkan daftar mahasiswa jika tidak ada kesalahan
            ListMahasiswa(
                listMhs = homeUiState.listMhs,
                onClick = {
                    onClick(it)
                    println(it)
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun HomeMhsView(
    viewModel: HomeMhsViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddMhs: () -> Unit = { },
    onDetailClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
) {
    Scaffold (
        modifier = modifier,
        topBar = {
            TopAppBar(
                judul = "Daftar Mahasiswa",
                showBackButton = false,
                onBack = { },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddMhs,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Mahasiswa"
                )
            }
        }
    ) {
        innerPadding ->
        val homeUiState by viewModel.homeUiState.collectAsState()

        BodyHomeMhsView(
            homeUiState = homeUiState,
            onClick = {
                    onDetailClick(it)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}