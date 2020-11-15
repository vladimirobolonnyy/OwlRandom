package com.obolonnyy.owlrandom.model

data class Word(
    val rowNumber: Int,
    val english: List<String>,
    val russian: List<String>,
    val showed: Int = 0,
    val answered: Int = 0,
    val notAnswered: Int = 0,
)