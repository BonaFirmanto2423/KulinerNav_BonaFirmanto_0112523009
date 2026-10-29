package com.example.kulinernav.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.kulinernav.ui.PlaceViewModel
import com.example.kulinernav.ui.screens.DetailScreen
import com.example.kulinernav.ui.screens.HomeScreen
import com.example.kulinernav.ui.screens.PlaceFormScreen

/**
 * Navigation graph: Home -> Detail(id) -> PlaceForm(id)
 *                   Home -> PlaceForm()  (tambah baru)
 *
 * Hanya file ini yang memegang NavController.
 * Semua screen menerima data + callback saja.
 */
@Composable
fun KulinerNavHost(
    navController: NavHostController = rememberNavController(),
    viewModel: PlaceViewModel = viewModel()
) {
    NavHost(navController = navController, startDestination = Home) {

        composable<Home> {
            HomeScreen(
                places = viewModel.places,
                onPlaceClick = { id -> navController.navigate(Detail(id)) },
                onAddClick = { navController.navigate(PlaceForm()) },
                onTestInvalidId = { navController.navigate(Detail(id = 999)) }
            )
        }

        composable<Detail> { backStackEntry ->
            // Membaca argument type-safe
            val route: Detail = backStackEntry.toRoute()
            DetailScreen(
                place = viewModel.getPlace(route.id),
                requestedId = route.id,
                onBack = { navController.popBackStack() },
                onEdit = { id -> navController.navigate(PlaceForm(id)) },
                onDelete = { id ->
                    viewModel.deletePlace(id)
                    // Kembali ke Home yang sudah ada, tanpa membuat Home duplikat
                    navController.popBackStack(Home, inclusive = false)
                }
            )
        }

        composable<PlaceForm> { backStackEntry ->
            val route: PlaceForm = backStackEntry.toRoute()
            val isEditMode = route.id != NEW_PLACE_ID
            PlaceFormScreen(
                initialPlace = if (isEditMode) viewModel.getPlace(route.id) else null,
                isEditMode = isEditMode,
                onSave = { name, category, location, rating, description ->
                    if (isEditMode) {
                        viewModel.getPlace(route.id)?.let {
                            viewModel.updatePlace(
                                it.copy(
                                    name = name, category = category, location = location,
                                    rating = rating, description = description
                                )
                            )
                        }
                        // Kembali ke Detail yang sama
                        navController.popBackStack()
                    } else {
                        val newId = viewModel.addPlace(name, category, location, rating, description)
                        // Buka detail item baru, Form dihapus dari back stack
                        navController.navigate(Detail(newId)) {
                            popUpTo(Home) { inclusive = false }
                        }
                    }
                },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}
