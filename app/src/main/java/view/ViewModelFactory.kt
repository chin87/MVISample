package view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import api.AnimalApi
import api.AnimalRepo

class ViewModelFactory(private val api: AnimalApi):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(AnimalRepo(api)) as T
        }
        return super.create(modelClass)
    }
}