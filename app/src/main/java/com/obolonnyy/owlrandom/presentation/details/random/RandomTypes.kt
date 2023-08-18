package com.obolonnyy.owlrandom.presentation.details.random

enum class RandomTypes(val index: Int, val text: String) {
    RANDOMIZE_ALL(0, "Randomize all"),
    ONE(1, "Pick one"),
    TWO(2, "Pick two"),
    THREE(3, "Pick three"),
    FOUR(4, "Pick four"),
    FIVE(5, "Pick five"),
    DIVIDE_TWO(6, "Divide in tho teams"),
    DIVIDE_THEE(7, "Divide in three teams"),
    DIVIDE_FOUR(8, "Divide in four teams"),
    DIVIDE_FIVE(9, "Divide in five teams");

    companion object {
        fun get(int: Int): RandomTypes {
            return values().firstOrNull { it.index == int } ?: RANDOMIZE_ALL
        }
    }
}