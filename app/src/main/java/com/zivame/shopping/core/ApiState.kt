package com.zivame.shopping.core

data class ApiState<T> (
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: Any? = null
)