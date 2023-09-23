package com.ispolska.myApplication.task_2.domain.model

import java.util.UUID

data class User(
    val name: String,
    val career: String,
    val photo: String = "",
    val id: UUID = UUID.randomUUID()
)
