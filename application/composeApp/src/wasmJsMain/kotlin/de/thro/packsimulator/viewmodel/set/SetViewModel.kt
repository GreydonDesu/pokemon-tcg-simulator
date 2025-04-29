import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.thro.packsimulator.manager.APIManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import de.thro.packsimulator.data.set.Set
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope

object SetViewModel {
    /*
    * Observable state for List of Set.
    */
    private val _setData = MutableStateFlow<Set?>(null)
    val setData: StateFlow<Set?> = _setData

    /*
    * Fetches the card set and updates the state.
    */
    @OptIn(DelicateCoroutinesApi::class)
    fun fetchSetData(setId: String) {
        GlobalScope.launch {
            val set = APIManager.getSet(setId)
            _setData.value = set
        }
    }
}
