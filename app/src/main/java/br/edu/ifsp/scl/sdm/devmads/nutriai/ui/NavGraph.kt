package br.edu.ifsp.scl.sdm.devmads.nutriai.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.edu.ifsp.scl.sdm.devmads.nutriai.ui.screens.DashboardScreen
import br.edu.ifsp.scl.sdm.devmads.nutriai.ui.screens.RegistroScreen

// Definição das rotas
sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Registro : Screen("registro")
}

@Composable
fun NutriAppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        // Rota da Dashboard
        composable(Screen.Dashboard.route) {
            DashboardScreen(onNavigateToRegistro = {
                navController.navigate(Screen.Registro.route)
            })
        }

        // Rota do Registro de Refeição
        composable(Screen.Registro.route) {
            RegistroScreen(onBack = {
                navController.popBackStack()
            })
        }
    }
}