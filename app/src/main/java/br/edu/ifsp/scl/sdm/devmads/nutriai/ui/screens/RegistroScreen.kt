package br.edu.ifsp.scl.sdm.devmads.nutriai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(onBack: () -> Unit) {
    var textoRefeicao by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo Registro") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                "O que você comeu agora?",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = textoRefeicao,
                onValueChange = { textoRefeicao = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                placeholder = { Text("Ex: 2 colheres de arroz, 1 concha de feijão e uma salada de tomate...") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Próxima etapa: Chamar Gemini */ },
                modifier = Modifier.fillMaxWidth(),
                enabled = textoRefeicao.isNotBlank()
            ) {
                Text("Analisar com IA")
            }
        }
    }
}