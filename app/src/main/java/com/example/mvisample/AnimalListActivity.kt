package com.example.mvisample

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import api.AnimalService
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.mvisample.ui.theme.MVISampleTheme
import kotlinx.coroutines.launch
import model.Animal
import view.MainViewModel
import view.ViewIntent
import view.ViewModelFactory
import view.ViewState

class AnimalListActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel =
            ViewModelProvider(this, ViewModelFactory(AnimalService.api))[MainViewModel::class.java]
        val onButtonClick: () -> Unit = {
           lifecycleScope.launch {
               mainViewModel.userIntent.send(ViewIntent.FetchAnimals)
           }
        }

        enableEdgeToEdge()
        setContent {
            MVISampleTheme {
                MainScreen(vm = mainViewModel, onButtonClick = onButtonClick)
            }
        }
    }

    @Composable
    fun MainScreen(vm: MainViewModel, onButtonClick:() -> Unit){
        val state = vm.state.value

        when(state){
            is ViewState.Data -> LoadingScreen()
            ViewState.Idle -> IdleScreen(onButtonClick)
            ViewState.Loading -> LoadingScreen()
            is ViewState.Animals -> AnimalList(animals = state.animal)
            is ViewState.Error -> {
                IdleScreen(onButtonClick)
                Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Composable
    fun IdleScreen(onButtonClick:() -> Unit){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Button(onClick = onButtonClick) {
                Text(
                    text = "Get Animals"
                )
            }
        }
    }

    @Composable
    fun LoadingScreen(){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    }

    @Composable
    fun AnimalList(animals: List<Animal>){
        LazyColumn {
            items(items = animals){
                AnimalItem(animal = it)
                Divider(color = Color.LightGray, modifier = Modifier.padding(top = 4.dp))
            }
        }
    }

    @Composable
    fun AnimalItem(animal: Animal) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            val url = AnimalService.BASE_URL + animal.image

            Image(
                painter = rememberAsyncImagePainter(model = url),
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.FillHeight
            )
            Column( modifier = Modifier.fillMaxSize().padding(start = 4.dp)){
                Text(text = animal.name, fontWeight = FontWeight.Bold)
                Text(text = animal.location)
            }
        }
    }
}