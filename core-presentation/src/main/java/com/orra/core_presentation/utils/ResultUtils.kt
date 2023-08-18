package com.orra.core_presentation.utils


inline fun <T> asResult(block: () -> T) = runCatching(block)
