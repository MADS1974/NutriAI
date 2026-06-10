package br.edu.ifsp.scl.sdm.devmads.nutriai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(onNavigateToRegistro: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("NutriAI") }) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToRegistro,
                icon = { Icon(Icons.Default.Add, "Adicionar") },
                text = { Text("Registrar Refeição") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Card de Resumo de Calorias
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Total de hoje", style = MaterialTheme.typography.titleMedium)
                    Text("1.250 kcal", style = MaterialTheme.typography.headlineLarge)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Refeições Recentes", style = MaterialTheme.typography.titleLarge)

            // Lista Placeholder (na Etapa 4 conectaremos ao Room)
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    ListItem(
                        headlineContent = { Text("Almoço") },
                        supportingContent = { Text("Arroz, feijão e frango • 450 kcal") },
                        trailingContent = { Text("12:30") }
                    )
                }
            }
        }
    }
}