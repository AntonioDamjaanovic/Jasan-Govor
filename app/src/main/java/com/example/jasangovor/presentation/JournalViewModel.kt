package com.example.jasangovor.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jasangovor.data.notes.JournalManager
import com.example.jasangovor.data.notes.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JournalViewModel: ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val journalManager = JournalManager()

    fun getNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                _notes.value = journalManager.getNotes()
            } catch (e: Exception) {
                Log.e("JournalViewModel", "Error with fetching notes", e)
            } finally {
                _loading.value = false
            }
        }
    }

    fun addNote(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                journalManager.addNote(text)
            } catch (e: Exception) {
                Log.e("JournalViewModel", "Error with adding or updating note", e)
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateNote(date: String, text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                journalManager.updateNote(date, text)
            } catch (e: Exception) {
                Log.e("JournalViewModel", "Error with adding or updating note", e)
            } finally {
                _loading.value = false
            }
        }
    }

    fun getNoteById(id: String): Note? {
        return notes.value.find { it.id == id }
    }

    fun deleteNote(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                journalManager.deleteNote(date)
            } catch (e: Exception) {
                Log.e("JournalViewModel", "Error with deleting note", e)
            } finally {
                _loading.value = false
            }
        }
    }
}