package com.obolonnyy.owlrandom.core

interface SimpleStore {
    fun set(key: String, value: Any, lifetimeSeconds: Long = -1)
    fun get(key: String): Any?
    fun clear(key: String)
}

class SimpleStoreImpl(
    private val clock: Clock = RealtimeClock()
) : SimpleStore {
    private val storage = mutableMapOf<String, Any>()
    private val lifeTime = mutableMapOf<String, Long>()

    override fun clear(key: String) {
        storage.remove(key)
        lifeTime.remove(key)
    }

    override fun set(key: String, value: Any, lifetimeSeconds: Long) {
        if (lifetimeSeconds > 0L) {
            lifeTime[key] = clock.nowSeconds() + lifetimeSeconds
        } else {
            lifeTime.remove(key)
        }
        storage[key] = value
    }

    override fun get(key: String): Any? {
        val isExpired = lifeTime[key]?.let { clock.nowSeconds() - it >= 0 } ?: false
        if (isExpired) {
            storage.remove(key)
            lifeTime.remove(key)
        }
        return storage[key]
    }
}