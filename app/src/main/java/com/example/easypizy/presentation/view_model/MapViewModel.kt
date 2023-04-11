package com.example.easypizy.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.easypizy.data.data_source.local.entity.SmokePlaceEntity
import com.example.easypizy.data.repository.SmokePlaceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(private val repository: SmokePlaceRepository) : ViewModel() {
    var smokePlaces: LiveData<List<SmokePlaceEntity>> = repository.smokePlaces.asLiveData()
        private set

    fun getSmokePlaces() =
        CoroutineScope(Dispatchers.IO).launch {
            repository.getAllSmokePlaceEntity()
            Log.d("MapViewModel:", " getSmokePlaces - " + smokePlaces.value?.size.toString())
        }

}

class MapViewModelFactory(private val repository: SmokePlaceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}