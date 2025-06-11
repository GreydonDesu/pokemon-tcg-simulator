package de.thro.packsimulator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.thro.packsimulator.model.SetModel
import de.thro.packsimulator.service.ApiService
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okio.Buffer

class SetViewModel : ViewModel() {
  private val _sets = MutableStateFlow<List<SetModel>>(emptyList())
  val sets: StateFlow<List<SetModel>>
    get() = _sets

  private val _errorMessage = MutableStateFlow("")
  val errorMessage: StateFlow<String>
    get() = _errorMessage

  private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
    println("Unhandled exception: ${throwable.message}") // Log the unexpected exception
  }

  fun fetchAllSets() {
    viewModelScope.launch(exceptionHandler) {
      val response = ApiService.getAllSets()

      when (response.code) {
        HttpStatusCode.OK.value -> {
          // Deserialize the JSON string into List<SetModel>
          val sets = response.body?.let { body ->
            val buffer = Buffer()
            body.writeTo(buffer)
            Json.decodeFromString<List<SetModel>>(buffer.readUtf8())
          }
          _sets.value = sets ?: emptyList()
          _errorMessage.value = ""
        }
        else -> {
          val errorMessage = response.body?.let { body ->
            val buffer = Buffer()
            body.writeTo(buffer)
            buffer.readUtf8()
          } ?: "Unknown error"
          _errorMessage.value = "HTTP error (${response.code}): $errorMessage"
        }
      }
    }
  }
}
