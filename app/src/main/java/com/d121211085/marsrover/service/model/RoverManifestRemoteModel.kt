package com.d121211085.marsrover.service.model

import com.d121211085.marsrover.service.model.PhotoManifestRemoteModel
import com.google.gson.annotations.SerializedName

data class RoverManifestRemoteModel(
    @field:SerializedName("photo_manifest") val photoManifest: PhotoManifestRemoteModel
)