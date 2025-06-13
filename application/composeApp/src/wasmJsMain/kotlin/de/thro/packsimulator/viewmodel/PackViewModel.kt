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

  private val _statusMessage = MutableStateFlow("")
  val statusMessage: StateFlow<String>
    get() = _statusMessage

  fun openPack(setId: String) {
    viewModelScope.launch {
      val result = ApiService.openPack(setId)
      result.onSuccess { cards ->
        _cards.value = cards
        _statusMessage.value = "Pack opened successfully!"
      }.onFailure { error ->
        _statusMessage.value = error.message ?: "An unknown error occurred"
      }
    }
  }

  fun clearStatusMessage() {
    _statusMessage.value = ""
  }
}
