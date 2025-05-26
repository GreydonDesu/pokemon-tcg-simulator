package de.thro.packsimulator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import de.thro.packsimulator.model.SetModel
import de.thro.packsimulator.service.ApiService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

class SetViewModel : ViewModel() {
    private val _sets = MutableStateFlow<List<SetModel>>(emptyList())
    val sets: StateFlow<List<SetModel>> get() = _sets

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> get() = _errorMessage

    private val _isLoading = MutableStateFlow(false)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Unhandled exception: ${throwable.message}") // Log the unexpected exception
    }

    fun fetchAllSets() {
        viewModelScope.launch(exceptionHandler) {
            try {
                _isLoading.value = true
                val allSets = ApiService.getAllSets()
                _sets.value = allSets
                _errorMessage.value = ""
            } catch (e: IOException) {
                // Handle network-related exceptions
                _errorMessage.value = "Network error: ${e.message}"
                println("IOException during fetchAllSets: ${e.message}") // Log the exception
            } catch (e: HttpException) {
                // Handle HTTP errors (e.g., 401 Unauthorized, 404 Not Found)
                _errorMessage.value = "HTTP error: ${e.message}"
                println("HttpException during fetchAllSets: ${e.message}") // Log the exception
            } catch (e: SerializationException) {
                // Handle JSON parsing errors
                _errorMessage.value = "Invalid server response"
                println("SerializationException during fetchAllSets: ${e.message}") // Log the exception
            } finally {
                _isLoading.value = false
            }
        }
    }
}
