import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.thro.packsimulator.manager.APIManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import de.thro.packsimulator.data.set.Set

class SetViewModel : ViewModel() {
    private val _setData = MutableStateFlow<Set?>(null)
    val setData: StateFlow<Set?> = _setData

    fun fetchSetData(setId: String) {
        viewModelScope.launch {
            val set = APIManager.getSet(setId)
            _setData.value = set
        }
    }
}
