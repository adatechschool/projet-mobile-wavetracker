//package com.example.wavetrackercompose.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.wavetrackercompose.model.ResponseModel
//import com.example.wavetrackercompose.network.SpotsApi
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//
//sealed class SpotsState{
//    object Loading:SpotsState()
//    data class Success(val spotsData: ResponseModel):SpotsState()
//    data class Error(val message:String):SpotsState()
//
//}
//
//class SpotsViewModel : ViewModel() {
//    private val _spotsState = MutableStateFlow<SpotsState>(SpotsState.Loading)
//    val spotsState: StateFlow<SpotsState> = _spotsState
//
//    fun fetchSpotsData() {
//        viewModelScope.launch {
//            try {
//                val response = SpotsApi.spotsService.getSpots()
//                _spotsState.value = SpotsState.Success(response)
//            } catch (e: Exception) {
//                _spotsState.value = SpotsState.Error("Failed to fetch spots data.")
//            }
//        }
//    }
//}