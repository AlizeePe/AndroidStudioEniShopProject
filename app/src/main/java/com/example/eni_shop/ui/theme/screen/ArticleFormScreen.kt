package com.example.eni_shop.ui.theme.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eni_shop.ui.theme.common.EniShopScaffold
import com.example.eni_shop.ui.theme.common.EniShopTextField
import com.example.eni_shop.vm.ArticleFormViewModel

@Composable
fun ArticleFormScreen(
    navigationIcon: @Composable () -> Unit,
    articleFormViewModel: ArticleFormViewModel = viewModel(factory = ArticleFormViewModel.Factory)
) {
    val context = LocalContext.current
    var title by rememberSaveable {
        mutableStateOf("")
    }

    val categories by articleFormViewModel.categories.collectAsState()

    EniShopScaffold(navigationIcon = navigationIcon) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            ArticleForm(title = title, onTitleChange = { title = it }, categories = categories)
            Button(onClick = {
                Toast.makeText(context, "$title ajouté", Toast.LENGTH_SHORT).show()

            }) {
                Text(text = "Enregistrer")
            }
        }
    }
}

@Composable
fun ArticleForm(
    categories: List<String>,
    modifier: Modifier = Modifier,
    title: String,
    onTitleChange: (String) -> Unit
) {

    var description by rememberSaveable {
        mutableStateOf("")
    }

    var price by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        EniShopTextField(label = "Titre", value = title, onValueChange = { onTitleChange(it) })
        EniShopTextField(
            label = "Description",
            value = description,
            onValueChange = { description = it })
        EniShopTextField(
            label = "Prix",
            value = price,
            onValueChange = {
                price = if (it.isNotEmpty() && it.toDoubleOrNull() != null) {
                    it
                } else {
                    ""
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        DropDownMenuCategories(categories = categories)
    }
}

@Composable
fun DropDownMenuCategories(categories: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var category by remember { mutableStateOf("") }

    Column {
        EniShopTextField(
            label = "Categorie",
            value = category,
            onValueChange = {},
            enabled = false,
            placeholder = { Text("Choisir une categorie") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "DropDown"
                )
            },
            modifier = Modifier
                .clickable { expanded = !expanded }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            categories.forEach { item ->
                DropdownMenuItem(onClick = {
                    category = item
                    expanded = false
                }, text = { Text(text = item) })
            }
        }
    }
}

@Preview
@Composable
fun FormPreview() {
    // ArticleFormScreen()
}