package com.example.eatyeaty.ui

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eatyeaty.repositories.ImageLoader
import com.example.eatyeaty.repositories.Recipe
import com.example.eatyeaty.ui.screen.CreateRecipeScreen
import com.example.eatyeaty.ui.screen.ShowRecipeScreen
import com.example.eatyeaty.ui.theme.EatyEatyTheme
import kotlinx.coroutines.launch

abstract class Route(val route: String) {
    class List: Route("list")
    class Create: Route("create")
    class Show: Route("show")
}


@Composable
fun App(

) {
    val controller = rememberNavController()
    val (recipe, setRecipe) = remember {
        mutableStateOf(Recipe())
    }

    EatyEatyTheme {
        NavHost(navController = controller, startDestination = Route.List().route) {

            composable(Route.List().route) {
                val scope = rememberCoroutineScope()
                var showDialog by remember { mutableStateOf(false) }
                ListScreen(
                    onCreateClick = {
                        showDialog = true
                    },
                    onRecipeEdit = {
                        // TODO: Navigate til show screen
                    },
                    onRecipeSelect = {
                        setRecipe(it)
                        controller.navigate(Route.Show().route)
                    })

                if (showDialog)
                    RecipeUrlDialog(
                        onDismissRequest = { showDialog = false },
                        onSuccess = {

                            // TODO: Try moving this logic into the viewmodel later
                            scope.launch {
                                setRecipe(
                                    Recipe(
                                        title = it.title,
                                        instructions = it.instructions,
                                        ingredients = it.ingredients,
                                        image = it.imageUrl.let {
                                            if (it.isNotEmpty())
                                                ImageLoader.getInstance().load(it)
                                            else
                                                null
                                        },
                                        url = it.url
                                    )
                                )
                                controller.navigate(Route.Create().route)
                            }
                        }
                    )
            }

            composable(Route.Create().route) {
                CreateRecipeScreen(
                    recipe = recipe,
                    onRecipeChange = setRecipe,
                    onCreateClick = {}
                )
            }

            composable(Route.Show().route) {
                ShowRecipeScreen(
                    recipe = recipe,
                    openUrl = { TODO("Trigger intent to open url") }
                )
            }
        }
    }
}
