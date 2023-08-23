package com.ispolska.myApplication.task_2.data.model

import java.util.UUID

data class User( // TODO: id?
    val name: String,
    val career: String,
    val photo: String = "",
    val id: UUID = UUID.randomUUID()
)
