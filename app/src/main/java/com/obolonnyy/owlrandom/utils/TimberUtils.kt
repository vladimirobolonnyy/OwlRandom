package com.obolonnyy.owlrandom.utils

import timber.log.Timber

fun log(msg: String) = Timber.i("#log, $msg")

fun warning(msg: String) = Timber.w("#warning, $msg")
fun warning(th: Throwable?) = Timber.w("#warning, $th")

