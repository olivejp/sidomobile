package nc.opt.sidomobile

import org.junit.Test

import org.junit.Assert.*

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

    private fun incrementColonneName(colonneLetter: String): String {
        val result: String
        var lastLetter = colonneLetter[colonneLetter.length - 1]
        lastLetter++
        if (colonneLetter.length > 1) {
            if (lastLetter > 'Z') {
                result = incrementColonneName(colonneLetter.substring(0, colonneLetter.length - 1)) + "A"
            } else {
                result = colonneLetter.substring(0, colonneLetter.length - 1) + lastLetter.toString()
            }
        } else {
            if (lastLetter > 'Z') {
                result = "AA"
            } else {
                result = lastLetter.toString()
            }
        }
        return result
    }

    @Test
    fun testIncrementColonne() {
        assertEquals("B", incrementColonneName("A"))
        assertEquals("AA", incrementColonneName("Z"))
        assertEquals("AB", incrementColonneName("AA"))
        assertEquals("ABA", incrementColonneName("AAZ"))
    }
}
