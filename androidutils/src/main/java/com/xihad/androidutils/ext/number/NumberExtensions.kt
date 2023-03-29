@file:Suppress("NOTHING_TO_INLINE")

package com.xihad.androidutils.ext.number

import kotlin.math.*

inline fun Long.even() = this % 2L == 0L
inline fun Number.even() = toLong().even()

inline fun Long.odd() = !even()
inline fun Number.odd() = toLong().odd()

inline fun Double.floor(): Long = kotlin.math.floor(this).toLong()
inline fun Double.ceil(): Long = kotlin.math.ceil(this).toLong()

inline fun Float.floor(): Long = toDouble().floor()
inline fun Float.ceil(): Long = toDouble().ceil()

inline fun Double.clamp(min: Double, max: Double): Double =
    min.coerceAtLeast(this.coerceAtMost(max))

inline fun Float.clamp(min: Float, max: Float): Float = min.coerceAtLeast(this.coerceAtMost(max))
inline fun Long.clamp(min: Long, max: Long): Long = min.coerceAtLeast(this.coerceAtMost(max))
inline fun Int.clamp(min: Int, max: Int): Int = min.coerceAtLeast(this.coerceAtMost(max))
inline fun Short.clamp(min: Short, max: Short): Short =
    toLong().clamp(min.toLong(), max.toLong()).toShort()

inline fun Byte.clamp(min: Byte, max: Byte): Byte =
    toLong().clamp(min.toLong(), max.toLong()).toByte()

inline fun Number.clamp(min: Number, max: Number): Long = toLong().clamp(min.toLong(), max.toLong())

