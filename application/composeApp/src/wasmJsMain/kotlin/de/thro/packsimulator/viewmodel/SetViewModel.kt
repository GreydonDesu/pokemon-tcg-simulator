package de.thro.packsimulator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.thro.packsimulator.model.SetModel
import de.thro.packsimulator.service.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SetViewModel : ViewModel() {
    private val _sets = MutableStateFlow<List<SetModel>>(emptyList())
    val sets: StateFlow<List<SetModel>> get() = _sets

    private val _selectedSet = MutableStateFlow<SetModel?>(null)
    val selectedSet: StateFlow<SetModel?> get() = _selectedSet

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> get() = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun fetchAllSets() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val allSets = ApiService.getAllSets()
                _sets.value = allSets
                _errorMessage.value = ""
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error fetching sets"
            } finally {
                _isLoading.value = false
            }
        }
    }

}