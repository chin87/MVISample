package view

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.AnimalRepo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AnimalRepo) : ViewModel() {

    val userIntent = Channel<ViewIntent>(Channel.UNLIMITED)
    val state = mutableStateOf<ViewState>(ViewState.Idle)

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { collector ->
                when (collector) {
                    is ViewIntent.FetchAnimals -> fetchAnimals()
                }
            }
        }
    }

    private fun fetchAnimals() {
        viewModelScope.launch {
            state.value = ViewState.Loading
            state.value = try {
                ViewState.Animals(repository.getAnimals())
            } catch (e: Exception) {
                ViewState.Error(e.localizedMessage ?: "")
            }
        }
    }
}