package com.obolonnyy.owlrandom.presentation.details.random

import java.util.LinkedList
import kotlin.random.Random

class Randomizer {

    private val random = Random(1001)

    fun <T> shuffle(list: MutableList<T>): MutableList<T> {
        list.shuffle(random)
        return list
    }

    fun <T> shuffleFirstN(n: Int, list: MutableList<T>): MutableList<T> {
        if (list.size < (n + 1) || n < 0) return list
        val oldItems = LinkedList<T>()
        for (i in 0..n) {
            val r = random.nextInt(0, list.size)
            oldItems.add(list.removeAt(r))
        }
        oldItems.addAll(list)
        return oldItems
    }

    fun <T> divideByNTeams(wantedN: Int, list: MutableList<T>): List<MutableList<T>> {
        val n = if (list.size < wantedN || wantedN < 0) list.size else wantedN
        val resultLists = ArrayList<MutableList<T>>(n)
        for (i in 0..n) {
            resultLists.add(mutableListOf())
        }
        for (i in 0 until list.size) {
            val r = random.nextInt(0, list.size)
            resultLists[i % n].add(list.removeAt(r))
        }
        return resultLists
    }
}