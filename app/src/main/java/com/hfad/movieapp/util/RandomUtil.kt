package com.hfad.movieapp.util

import kotlin.random.Random

class RandomUtil {
    companion object {
        fun rand(start: Int, end: Int): Int {
            require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }
            return Random(System.nanoTime()).nextInt(end - start + 1) + start
        }
    }
}