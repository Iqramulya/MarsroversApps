package com.d121211085.marsrover.ui.photolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d121211085.marsrover.data.MarsRoverPhotoRepo
import com.d121211085.marsrover.di.IoDispatcher
import com.d121211085.marsrover.domain.model.RoverPhotoUiModel
import com.d121211085.marsrover.domain.model.RoverPhotoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarsRoverPhotoViewModel @Inject constructor(
    private val marsRoverPhotoRepo: MarsRoverPhotoRepo,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _roverPhotoUiState: MutableStateFlow<RoverPhotoUiState> =
        MutableStateFlow(RoverPhotoUiState.Loading)

    val roverPhotoUiState: StateFlow<RoverPhotoUiState>
        get() = _roverPhotoUiState

    fun getMarsRoverPhoto(roverName: String, sol: String) {
        viewModelScope.launch(ioDispatcher) {
            _roverPhotoUiState.value = RoverPhotoUiState.Loading
            marsRoverPhotoRepo.getMarsRoverPhoto(roverName, sol).collect {
                _roverPhotoUiState.value = it
            }
        }
    }

    fun changeSaveStatus(roverPhotoUiModel: RoverPhotoUiModel) {
        viewModelScope.launch(ioDispatcher) {
            if (roverPhotoUiModel.isSaved) {
                marsRoverPhotoRepo.removePhoto(roverPhotoUiModel)
            } else {
                marsRoverPhotoRepo.savePhoto(roverPhotoUiModel = roverPhotoUiModel)
            }
        }
    }
}