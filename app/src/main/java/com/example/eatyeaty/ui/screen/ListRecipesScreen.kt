package com.example.eatyeaty.ui

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.eatyeaty.repositories.Recipe

@Composable
fun ListScreen(
    onCreateClick: () -> Unit,
    onRecipeSelect: (r: Recipe) -> Unit,
    onRecipeEdit: (r: Recipe) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateClick) {
                Icon(Icons.Default.Add, contentDescription = "")
            }
        }
    ) {
        RecipeList(
            recipes = listOf(
                Recipe(title = "Lsange"),
                Recipe(title = "Pizza"),
            ),
            onRecipeSelect = onRecipeSelect,
            onRecipeEdit = onRecipeEdit,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ListScreenPreview() {
    ListScreen(
        onCreateClick = {},
        onRecipeSelect = {},
        onRecipeEdit = {},
    )
}