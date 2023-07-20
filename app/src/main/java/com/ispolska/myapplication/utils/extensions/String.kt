package com.ispolska.myapplication.utils.extensions

fun String.capitalizeFirstChar(): String = this.replaceFirstChar { it.uppercaseChar() }