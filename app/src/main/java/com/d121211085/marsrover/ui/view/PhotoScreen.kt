package com.d121211085.marsrover.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.d121211085.marsrover.domain.model.RoverPhotoUiState
import com.d121211085.marsrover.ui.photolist.MarsRoverPhotoViewModel
import com.d121211085.marsrover.ui.view.Error
import com.d121211085.marsrover.ui.view.Loading
import com.d121211085.marsrover.ui.view.PhotoList

@Composable
fun PhotoScreen(
    modifier: Modifier,
    roverName: String?,
    sol: String?,
    marsRoverPhotoViewModel: MarsRoverPhotoViewModel
) {
    val viewState by marsRoverPhotoViewModel.roverPhotoUiState.collectAsStateWithLifecycle()

    if (roverName != null && sol != null) {
        LaunchedEffect(Unit) {
            marsRoverPhotoViewModel.getMarsRoverPhoto(roverName, sol)
        }
        when(val roverPhotoUiState = viewState) {
            RoverPhotoUiState.Error -> Error()
            RoverPhotoUiState.Loading -> Loading()
            is RoverPhotoUiState.Success -> PhotoList(
                modifier = modifier,
                roverPhotoUiModelList = roverPhotoUiState.roverPhotoUiModelList
            ) { roverPhotoUiModel ->
                marsRoverPhotoViewModel.changeSaveStatus(roverPhotoUiModel)
            }
        }
    }
}