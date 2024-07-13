package com.faizulla.music.remote.dataModel

data class Artist(
    val external_urls: ExternalUrlsX,
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)