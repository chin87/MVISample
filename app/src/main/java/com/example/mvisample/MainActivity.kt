package com.example.mvisample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.mvisample.ui.theme.MVISampleTheme
import model.Animal
import view.ViewIntent
import view.ViewState

class MainActivity : ComponentActivity() {
    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]
        myViewModel.viewState.observe(this) { state ->
            render(state)
        }
        enableEdgeToEdge()
        setContent {
            MVISampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        {
                           // myViewModel.processIntent(ViewIntent.LoadData)
                        }
                    )
                }
            }
        }
    }

    private fun render(state: ViewState) {
        when (state) {
            is ViewState.Loading -> showLoading()
            is ViewState.Data -> showData(state.items)
            is ViewState.Error -> showError(state.message)

            is ViewState.Animals -> showAnimalData(state.animal)
            is ViewState.Idle -> showLoading()
        }
    }

    private fun showLoading() {
        // Show loading UI
    }

    private fun showData(data: List<String>) {
        // Show data in the UI
    }

    private fun showError(message: String) {
        // Show error message in UI
    }

    private fun showAnimalData(animals: List<Animal>) {
        // Show data in the UI
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, buttonClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Button(onClick = buttonClick) {
            Text(
                text = "Data",
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MVISampleTheme {
        Greeting("Android", buttonClick = {})
    }
}