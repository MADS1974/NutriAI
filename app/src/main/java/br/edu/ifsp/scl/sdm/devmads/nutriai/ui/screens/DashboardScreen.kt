package br.edu.ifsp.scl.sdm.devmads.nutriai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.ifsp.scl.sdm.devmads.nutriai.ui.viewmodel.NutriViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToRegistro: () -> Unit,
    viewModel: NutriViewModel = viewModel()
) {
    // Coleta as refeições do banco de dados.
    val refeicoesSalvas by viewModel.todasRefeicoes.collectAsState(initial = emptyList())

    // Estado para controlar a exibição do diálogo de confirmação
    var mostrarDialogoSaude by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("NutrIA", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00913F),
                    titleContentColor = Color.White
                ),
                actions = {
                    // Ícone para limpar o histórico (ideal para seu vídeo)
                    IconButton(onClick = { mostrarDialogoSaude = true }) {
                        Icon(
                            imageVector = Icons.Default.DeleteSweep,
                            contentDescription = "Limpar Histórico",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToRegistro,
                icon = { Icon(Icons.Default.Add, "Adicionar") },
                text = { Text("Registrar Refeição") },
                containerColor = Color(0xFF00913F),
                contentColor = Color.White
            )
        }
    ) { padding ->
        // Diálogo de confirmação para não apagar por acidente
        if (mostrarDialogoSaude) {
            AlertDialog(
                onDismissRequest = { mostrarDialogoSaude = false },
                title = { Text("Limpar Histórico") },
                text = { Text("Deseja apagar todos os registros permanentemente?") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.limparHistorico()
                        mostrarDialogoSaude = false
                    }) {
                        Text("Sim, Limpar", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogoSaude = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Card de Resumo
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF00913F).copy(alpha = 0.1f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Total de registros", style = MaterialTheme.typography.titleMedium, color = Color(0xFF00913F))
                    Text("${refeicoesSalvas.size} análises", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Refeições Recentes", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (refeicoesSalvas.isEmpty()) {
                    item {
                        Text(
                            "Nenhuma refeição registrada ainda.",
                            modifier = Modifier.padding(top = 16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                } else {
                    items(refeicoesSalvas) { refeicao ->
                        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
                        val horaFormatada = sdf.format(Date(refeicao.data))

                        ListItem(
                            headlineContent = { Text(refeicao.descricao) },
                            supportingContent = {
                                Text(
                                    text = refeicao.analiseIA.take(100) + "...",
                                    maxLines = 2,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            trailingContent = { Text(horaFormatada) },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                        )
                        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                    }
                }
            }
        }
    }
}