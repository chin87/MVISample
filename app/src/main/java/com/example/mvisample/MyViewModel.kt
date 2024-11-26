package com.example.mvisample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import view.ViewIntent
import view.ViewState

class MyViewModel(private val repository: MyRepository = MyRepository()) : ViewModel() {
    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> get() = _viewState

    fun processIntent(intent: ViewIntent) {
        /*when (intent) {
            is ViewIntent.LoadData -> loadData()
        }*/
    }

    private fun loadData() {
        _viewState.value = ViewState.Loading
        viewModelScope.launch {
            try {
                val data = repository.fetchData().first()
                _viewState.value = ViewState.Data(data)
            } catch (e: Exception) {
                _viewState.value = ViewState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}
