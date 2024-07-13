package com.faizulla.music.remote.dataModel

import com.faizulla.music.remote.dataModel.Item

data class Tracks(
    val items: List<Item>,
    val limit: Int,
    val next: Any,
    val offset: Int,
    val previous: Any,
    val total: Int
)