package cl.bootcamp.mobistore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.bootcamp.mobistore.view.ProductDetailView
import cl.bootcamp.mobistore.view.ProductListView
import cl.bootcamp.mobistore.viewModel.ProductViewModel

@Composable
fun NavManager( viewModel: ProductViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "productListView") {
        composable("productListView") {
            ProductListView(viewModel, navController)
        }
        composable("productDetailView/{id}", arguments = listOf(
            navArgument("id") { type = NavType.IntType }
        )) {
            val id = it.arguments?.getInt("id") ?: 0
            ProductDetailView(viewModel, navController ,id)
        }

    }


}