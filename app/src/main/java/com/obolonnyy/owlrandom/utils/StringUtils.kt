package com.obolonnyy.owlrandom.utils

fun String.digitsOnly(): String = replace("""[^\d]""".toRegex(), "")

fun String.digitsWithSign(): String = replace("""[^\d.,]""".toRegex(), "")
