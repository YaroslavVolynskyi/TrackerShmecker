package com.example.trackershmecker.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.trackershmecker.ui.main.MainScreen
import com.example.trackershmecker.ui.main.MainViewModel
import com.example.trackershmecker.ui.yeartotals.YearTotalsScreen
import com.example.trackershmecker.ui.yeartotals.YearTotalsViewModel
import java.time.YearMonth

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { backStackEntry ->
            val mainViewModel: MainViewModel = hiltViewModel()

            val savedStateHandle = backStackEntry.savedStateHandle
            val targetYear = savedStateHandle.get<Int>("targetYear")
            val targetMonthVal = savedStateHandle.get<Int>("targetMonth")
            val targetMonth = if (targetYear != null && targetMonthVal != null) {
                savedStateHandle.remove<Int>("targetYear")
                savedStateHandle.remove<Int>("targetMonth")
                YearMonth.of(targetYear, targetMonthVal)
            } else {
                null
            }

            MainScreen(
                viewModel = mainViewModel,
                targetMonth = targetMonth,
                onYearTap = { year ->
                    navController.navigate("yearTotals/$year")
                },
            )
        }

        composable(
            route = "yearTotals/{year}",
            arguments = listOf(navArgument("year") { type = NavType.IntType }),
        ) {
            val yearTotalsViewModel: YearTotalsViewModel = hiltViewModel()
            YearTotalsScreen(
                viewModel = yearTotalsViewModel,
                onBack = { navController.popBackStack() },
                onPickMonth = { y, m ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("targetYear", y)
                    navController.previousBackStackEntry?.savedStateHandle?.set("targetMonth", m)
                    navController.popBackStack()
                },
            )
        }
    }
}
