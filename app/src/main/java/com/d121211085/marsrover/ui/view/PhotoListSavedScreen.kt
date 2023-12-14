package com.d121211085.marsrover.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.d121211085.marsrover.domain.model.RoverPhotoUiState
import com.d121211085.marsrover.ui.savedlist.MarsRoverSavedViewModel
import com.d121211085.marsrover.ui.view.Error
import com.d121211085.marsrover.ui.view.Loading
import com.d121211085.marsrover.ui.view.PhotoList

@Composable
fun PhotoListSavedScreen(
    modifier: Modifier = Modifier,
    marsRoverSavedViewModel: MarsRoverSavedViewModel
) {
    val viewState by marsRoverSavedViewModel.marsPhotoUiSavedState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        marsRoverSavedViewModel.getAllSaved()
    }

    when (val roverPhotoUiState = viewState) {
        RoverPhotoUiState.Error -> Error()
        RoverPhotoUiState.Loading -> Loading()
        is RoverPhotoUiState.Success -> PhotoList(
            modifier = modifier,
            roverPhotoUiModelList = roverPhotoUiState.roverPhotoUiModelList,
            onClick = { roverPhotoUiModel ->
                marsRoverSavedViewModel.changeSaveStatus(roverPhotoUiModel)
            }
        )
    }
}