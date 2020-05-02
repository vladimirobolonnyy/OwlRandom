package com.obolonnyy.owlrandom.presentation.details

import java.util.*
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

    fun <T> divideByNTeams(n: Int, list: MutableList<T>): List<MutableList<T>> {
        if (list.size < n || n < 0) return listOf(list)
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