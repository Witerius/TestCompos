package com.example.testcompose.domain.entities

class TestAppResponse : ArrayList<TestAppItem>()

data class TestAppItem(
    val description: String,
    val icon: String,
    val id: String,
    val name: String,
    val price: Double
)