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
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.ifsp.scl.sdm.devmads.nutriai.ui.viewmodel.NutriViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    onBack: () -> Unit,
    viewModel: NutriViewModel = viewModel() // Instancia o ViewModel aqui
) {
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
            OutlinedTextField(
                value = textoRefeicao,
                onValueChange = { textoRefeicao = it },
                modifier = Modifier.fillMaxWidth().height(150.dp),
                placeholder = { Text("Ex: 1 pão francês com manteiga e café com leite") },
                enabled = !viewModel.carregando
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.analisarRefeicao(textoRefeicao) },
                modifier = Modifier.fillMaxWidth(),
                enabled = textoRefeicao.isNotBlank() && !viewModel.carregando
            ) {
                if (viewModel.carregando) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                } else {
                    Text("Analisar com IA")
                }
            }

            // EXIBIÇÃO DO RESULTADO
            if (viewModel.analiseResultado.isNotBlank()) {
                Spacer(modifier = Modifier.height(24.dp))
                Text("Análise da NutriAI:", style = MaterialTheme.typography.titleMedium)
                Card(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text(
                        text = viewModel.analiseResultado,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
