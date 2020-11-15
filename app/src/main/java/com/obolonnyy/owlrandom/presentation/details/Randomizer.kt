package com.obolonnyy.owlrandom.presentation.details

import com.obolonnyy.owlrandom.core.Clock
import com.obolonnyy.owlrandom.core.RealtimeClock
import java.util.*
import kotlin.random.Random

class Randomizer(
    val clock: Clock = RealtimeClock(),
    private val random: Random = Random(clock.nowSeconds())
) {

    fun <T> shuffle(list: MutableList<T>): MutableList<T> {
        list.shuffle(random)
        return list
    }

    fun <T> shuffleFirstN(n: Int, list: MutableList<T>): MutableList<T> {
        if (list.size < (n + 1) || n < 0) return list
        val newItems = LinkedList<T>()
        for (i in 0..n) {
            val r = random.nextInt(0, list.size)
            newItems.add(list.removeAt(r))
        }
        newItems.addAll(list)
        return newItems
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