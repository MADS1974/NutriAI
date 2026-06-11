package br.edu.ifsp.scl.sdm.devmads.nutriai.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.ifsp.scl.sdm.devmads.nutriai.ui.viewmodel.NutriViewModel

val IFSPVerde = Color(0xFF00913F)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    onBack: () -> Unit,
    viewModel: NutriViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // Launcher para GALERIA
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            }
            viewModel.imagemSelecionada = bitmap
        }
    }

    // Launcher para CÂMERA
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            viewModel.imagemSelecionada = bitmap
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("NutrIA - Registro") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = IFSPVerde, titleContentColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Text("Descreva sua refeição ou use uma foto:", color = IFSPVerde, style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = viewModel.textoRefeicao,
                onValueChange = { viewModel.textoRefeicao = it },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                placeholder = { Text("Ex: Arroz, feijão e frango...") },
                trailingIcon = {
                    if (viewModel.textoRefeicao.isNotEmpty() || viewModel.imagemSelecionada != null) {
                        IconButton(onClick = { viewModel.limparCampos() }) {
                            Icon(Icons.Default.Clear, contentDescription = "Limpar")
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { galleryLauncher.launch("image/*") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = IFSPVerde)
                ) {
                    Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Galeria")
                }

                OutlinedButton(
                    onClick = { cameraLauncher.launch() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = IFSPVerde)
                ) {
                    Icon(Icons.Default.AddAPhoto, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Câmera")
                }
            }

            // EXIBIR MINIATURA DA FOTO SELECIONADA (Puxando do ViewModel)
            viewModel.imagemSelecionada?.let { btm ->
                Card(
                    modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth().height(200.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Image(
                        bitmap = btm.asImageBitmap(),
                        contentDescription = "Imagem selecionada",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.analisarRefeicao() },
                modifier = Modifier.fillMaxWidth(),
                enabled = (viewModel.textoRefeicao.isNotBlank() || viewModel.imagemSelecionada != null) && !viewModel.carregando,
                colors = ButtonDefaults.buttonColors(containerColor = IFSPVerde)
            ) {
                if (viewModel.carregando) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White, strokeWidth = 2.dp)
                } else {
                    Text("Consultar NutrIA", color = Color.White)
                }
            }

            if (viewModel.analiseResultado.isNotBlank()) {
                Card(
                    modifier = Modifier.padding(top = 16.dp, bottom = 32.dp).fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9))
                ) {
                    Text(viewModel.analiseResultado, modifier = Modifier.padding(16.dp), color = Color.Black)
                }
            }
        }
    }
}