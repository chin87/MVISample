package com.example.mvisample

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MyRepository {
    fun fetchData(): Flow<List<String>> {
        return flow {
            // Emit a list of strings
            emit(listOf("apple", "banana", "cherry"))
        }
    }
}
