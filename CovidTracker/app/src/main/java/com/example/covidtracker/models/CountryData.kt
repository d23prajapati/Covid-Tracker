package com.example.covidtracker.models

data class CountryData(
        val activeCases: String,
        val critical: String,
        val newCases: String,
        val newDeaths: String,
        val newRecovered: String,
        val totalCases: String,
        val totalDeaths: String,
        val totalRecovered: String,
        val totalTests: String
)