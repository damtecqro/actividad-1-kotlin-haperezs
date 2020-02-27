package com.test.tarea1

/*
 *  Hugo Arturo Pérez Soní A01273106
 *  Problems 18 and 46
 */

/*
 *  P18:
 *  FUNCTION slice(i, k, list):
 *      newlist <- empty list
 *      FOR x = i to k
 *          ADD list[x] TO newlist
 *      RETURN newlist
 *
 */
fun <T> slice(i: Int, k: Int, list: List<T>): List<T> {
    var newlist: MutableList<T> = mutableListOf<T>()

    var start: Int = i
    var end: Int = k

    //  If i is bigger than k flip them to make sense
    if(i > k){
        start = k
        end = i
    }

    // If i and k are the same return only the element with that index
    if(i == k){
        newlist.add(list[i])
        return newlist
    }

    // Return elements with index i to k-1
    for (x in start..end-1) {
        newlist.add(list[x])
    }
    return newlist
}

/*
 *  P46:
 *  FUNCTION not(a):
 *      IF a == True
 *          RETURN False
 *      IF b == False
 *          RETURN True
 *
 *  FUNCTION and(a, b):
 *      IF a == True AND b == True
 *          RETURN True
 *      IF a == True AND b == False
 *          RETURN False
 *      IF a == False AND b == True
 *          RETURN False
 *      IF a == False AND b == False
 *          RETURN False
 *
 *  FUNCTION or(a, b):
 *      IF a == True AND b == True
 *          RETURN True
 *      IF a == True AND b == False
 *          RETURN True
 *      IF a == False AND b == True
 *          RETURN True
 *      IF a == False AND b == False
 *          RETURN False
 *
 *  FUNCTION nand(a, b):
 *      IF a == True AND b == True
 *          RETURN False
 *      IF a == True AND b == False
 *          RETURN True
 *      IF a == False AND b == True
 *          RETURN True
 *      IF a == False AND b == False
 *          RETURN True
 *
 *  FUNCTION nor(a, b):
 *      IF a == True AND b == True
 *          RETURN False
 *      IF a == True AND b == False
 *          RETURN False
 *      IF a == False AND b == True
 *          RETURN False
 *      IF a == False AND b == False
 *          RETURN True
 *
 *  FUNCTION xor(a, b):
 *      IF a == True AND b == True
 *          RETURN False
 *      IF a == True AND b == False
 *          RETURN True
 *      IF a == False AND b == True
 *          RETURN True
 *      IF a == False AND b == False
 *          RETURN False
 *
 *  FUNCTION equ(a, b):
 *      IF a == True AND b == True
 *          RETURN True
 *      IF a == True AND b == False
 *          RETURN False
 *      IF a == False AND b == True
 *          RETURN False
 *      IF a == False AND b == False
 *          RETURN True
 *
 *
 */
fun Boolean.not_() = !this
fun Boolean.and_(other: Boolean) = this && other
fun Boolean.or_(other: Boolean) = this || other
fun Boolean.nand_(other: Boolean) = this.and_(other).not_()
fun Boolean.nor_(other: Boolean) = this.or_(other).not_()
fun Boolean.xor_(other: Boolean) = this.xor(other)
fun Boolean.equ_(other: Boolean) = this.xor_(other).not_()

fun truthTable(f: (Boolean, Boolean) -> Boolean): List<Row> =
    listOf(Pair(true, true), Pair(true, false), Pair(false, true), Pair(false, false)).map {
        Row(it.first, it.second, f(it.first, it.second))
    }

fun printTruthTable(f: (Boolean, Boolean) -> Boolean) {
    println("a   \tb   \tresult")
    truthTable(f).forEach {
        println(listOf(it.a, it.b, it.result).joinToString("\t"))
    }
}

data class Row(val a: Boolean, val b: Boolean, val result: Boolean)


//  Tests
println("\nP18:")
assertThat(slice(3, 7, "abcdefghijk".toList()), ("defg".toList()))
assertThat(slice(0, 6, "qwertyuiop".toList()), ("qwerty".toList()))
assertThat(slice(7, 11, "holasoypepe".toList()), ("pepe".toList()))
assertThat(slice(4, 0, "holasoypepe".toList()), ("hola".toList()))
assertThat(slice(3, 3, "holasoypepe".toList()), ("a".toList()))

println("\nP46:")
assertThat(true.and_(true), true)
assertThat(true.xor_(true), false)
assertThat(true.or_(false), true)
assertThat(false.or_(false), false)
assertThat(true.not_(), false)
assertThat(false.not_(), true)


printTruthTable { a, b -> a.and_(b) }
assertThat(truthTable{a,b->a.and_(b)}, listOf(
    Row(true, true, true),
    Row(true, false, false),
    Row(false, true, false),
    Row(false, false, false)
))

printTruthTable { a, b -> a.xor_(b) }
assertThat(truthTable{a,b->a.xor_(b)}, listOf(
    Row(true, true, false),
    Row(true, false, true),
    Row(false, true, true),
    Row(false, false, false)
))

printTruthTable { a, b -> a.equ_(b) }
assertThat(truthTable{a,b->a.equ_(b)}, listOf(
    Row(true, true, true),
    Row(true, false, false),
    Row(false, true, false),
    Row(false, false, true)
))

printTruthTable { a, b -> a.and_(a.or_(b.not_())) }
assertThat(truthTable { a, b -> a.and_(a.or_(b.not_())) }, listOf(
    Row(true, true, true),
    Row(true, false, true),
    Row(false, true, false),
    Row(false, false, false)
))

printTruthTable { a, b -> a.or_(b) }
assertThat(truthTable{a,b->a.or_(b)}, listOf(
    Row(true, true, true),
    Row(true, false, true),
    Row(false, true, true),
    Row(false, false, false)
))

printTruthTable { a, b -> a.nand_(b) }
assertThat(truthTable{a,b->a.nand_(b)}, listOf(
    Row(true, true, false),
    Row(true, false, true),
    Row(false, true, true),
    Row(false, false, true)
))


var testNumber = 1
fun <T> assertThat(test: T, answer: T) {
    if (test != answer) {
        throw Error("\n-> Error in test %d:\n\nExpected %s, got %s".format(
            testNumber, answer.toString(), test.toString())
        )
    }
    println("Test %d: Passed".format(testNumber))
    testNumber += 1
}