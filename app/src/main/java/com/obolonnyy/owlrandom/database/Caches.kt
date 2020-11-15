package com.obolonnyy.owlrandom.database

import com.obolonnyy.owlrandom.core.SimpleStore
import com.obolonnyy.owlrandom.core.SimpleStoreImpl

object Caches {
    val wordsCache: SimpleStore = SimpleStoreImpl()
}