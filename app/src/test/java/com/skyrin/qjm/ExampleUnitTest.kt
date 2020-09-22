package com.skyrin.qjm

import android.os.SystemClock
import org.junit.Test

import org.junit.Assert.*
import java.util.*
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `log random num`(){
        print(1599271436000 - (24*60*60*1000))
    }
}