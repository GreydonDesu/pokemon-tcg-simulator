package de.thro.packsimulator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.thro.packsimulator.model.CardModel
import de.thro.packsimulator.service.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PackViewModel : ViewModel() {
  private val _cards = MutableStateFlow<List<CardModel>>(emptyList())
  val cards: StateFlow<List<CardModel>>
    get() = _cards

  private val _errorMessage = MutableStateFlow("")
  val errorMessage: StateFlow<String>
    get() = _errorMessage

  fun openPack(setId: String) {
    viewModelScope.launch {
      try {
        val openedCards = ApiService.openPack(setId)
        _cards.value = openedCards
        _errorMessage.value = ""
      } catch (e: Exception) {
        _errorMessage.value = e.message ?: "Error opening pack"
      }
    }
  }
}
