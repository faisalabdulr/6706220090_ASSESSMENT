package com.example.a6706220090_assesment2.ui.Screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.a6706220090_assesment2.R
import com.example.a6706220090_assesment2.database.MenuDb
import com.example.a6706220090_assesment2.ui.theme._6706220090_ASSESMENT2Theme
import com.example.a6706220090_assesment2.util.ViewModelFactory

const val KEY_ID = "idMenu"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null){
    val context = LocalContext.current
    val db = MenuDb.getInstace(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var harga by remember {
        mutableStateOf("")
    }
    var nama by remember {
        mutableStateOf("")
    }
    var kategori by remember {
        mutableStateOf("")
    }

    LaunchedEffect(true){
        if (id == null) return@LaunchedEffect

        val data = viewModel.getMenu(id) ?: return@LaunchedEffect
        harga = data.harga.toString()
        nama = data.nama
        kategori = data.kategori

    }
    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null) Text(text = stringResource(id = R.string.tambah_menu))
                    else Text(text = stringResource(id = R.string.edit_menu))
                },

                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(
                        onClick = {
                            if (nama.isEmpty() || harga.isEmpty() || kategori.isEmpty()) {
                                Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                                return@IconButton
                            }
                            if (id == null) viewModel.insert(nama, harga.toFloat(), kategori)
                            else viewModel.update(id, nama, harga.toFloat(), kategori)
                            navController.popBackStack()
                        }
                    ) {
                        Icon(imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(id = R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null){
                        DeleteById {
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        },
    ){
            padding ->
        FormMenu(
            id = id,
            harga = harga,
            onHargaChange = { harga = it },
            nama =  nama,
            onNamaChange = { nama = it},
            kategori = kategori,
            onKategoriChange = { kategori = it},
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun FormMenu(
    id: Long?,
    harga: String,
    onHargaChange: (String) -> Unit,
    nama: String,
    onNamaChange: (String) -> Unit,
    kategori: String,
    onKategoriChange: (String) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = nama,
            onValueChange = { onNamaChange(it) },
            label = { Text(text = stringResource(id = R.string.isi_menu)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = harga,
            onValueChange = { onHargaChange(it) },
            label = { Text(text = stringResource(id = R.string.harga)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Column {
            Text(text = "Pilih Kategori")
            listOf("MakananUtama", "MakananPenutup", "MakananTambahan").forEach { kategoriOption ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        onKategoriChange(kategoriOption)
                    }
                ) {
                    RadioButton(
                        selected = kategori == kategoriOption,
                        onClick = {
                            onKategoriChange(kategoriOption)
                        }
                    )
                    Text(text = kategoriOption)
                }
            }
        }
    }
}

@Composable
fun DeleteById(delete: () -> Unit){
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(onClick = { expanded = true }) {
        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = stringResource(id = R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text(text = stringResource(id = R.string.hapus)) }, onClick = {
                expanded = false
                delete() })
        }
    }
}
