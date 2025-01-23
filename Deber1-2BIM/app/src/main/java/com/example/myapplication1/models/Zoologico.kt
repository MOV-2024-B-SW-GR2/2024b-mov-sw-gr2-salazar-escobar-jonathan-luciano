package com.example.myapplication1.models

class Zoologico (
    var id: Int,
    var name: String,
    var startDate: String,
    var animalsCount: Int,
    var isOpen: Boolean,
    var anualIncome: Double,
    var animals: MutableList<Animal>
)
