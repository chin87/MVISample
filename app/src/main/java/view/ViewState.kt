package view

import model.Animal

sealed class ViewState{
    object Idle : ViewState()
    object Loading : ViewState()
    data class Data(val items: List<String>) : ViewState()
    data class Error(val message: String) : ViewState()
    data class Animals(val animal: List<Animal>): ViewState()
}