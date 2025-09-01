package com.example.jasangovor.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jasangovor.data.fearedsounds.FearedSound
import com.example.jasangovor.data.fearedsounds.FearedSoundManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FearedSoundsViewModel: ViewModel() {
    private val _fearedSounds = MutableStateFlow<List<FearedSound>>(emptyList())
    val fearedSounds: StateFlow<List<FearedSound>> = _fearedSounds

    private val fearedSoundManager = FearedSoundManager()

    fun getFearedSounds() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _fearedSounds.value = fearedSoundManager.getFearedSounds()
            } catch (e: Exception) {
                Log.e("FearedSoundsViewModel", "Error with fetching feared sounds", e)
            }
        }
    }

    fun getFearedSoundById(id: String): FearedSound? {
        return fearedSounds.value.find { it.sound == id }
    }
}