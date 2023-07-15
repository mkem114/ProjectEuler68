import java.util.ArrayList

fun main() {
    val numbers = listOf(9,8,7,6,5,4,3,2,1, 10)
    val permutations = digitPermutations(numbers)

    val solutions = permutations
        .map { rawDigitsToCandidate(it) }
        .filter { is16Digits(it) }
        .filter { isValid(it) }
        .map { value(it) }
        .toList()

    println(solutions.max())
}

fun digitPermutations(remainingDigits: List<Int>): List<List<Int>> {
    if (remainingDigits.size == 1) {
        return listOf(remainingDigits)
    }

    val collection = ArrayList<List<Int>>()
    for (i in remainingDigits.indices) {
        val newRemainingDigits = mutableListOf<Int>()
        newRemainingDigits.addAll(remainingDigits.subList(0, i))
        newRemainingDigits.addAll(remainingDigits.subList(i + 1, remainingDigits.size))
        digitPermutations(newRemainingDigits)
            .forEach {
                val working = mutableListOf<Int>()
                working.add(remainingDigits[i])
                working.addAll(it)
                collection.add(working)
            }
    }

    return collection
}

fun rawDigitsToCandidate(digits: List<Int>) =
    listOf(
        digits[0], digits[1], digits[2],
        digits[3], digits[2], digits[4],
        digits[5], digits[4], digits[6],
        digits[7], digits[6], digits[8],
        digits[9], digits[8], digits[1],
    )

fun isValid(candidateSolutionSet: List<Int>): Boolean {
    val arm1 = candidateSolutionSet[0] + candidateSolutionSet[1] + candidateSolutionSet[2]
    val arm2 = candidateSolutionSet[3] + candidateSolutionSet[4] + candidateSolutionSet[5]
    val arm3 = candidateSolutionSet[6] + candidateSolutionSet[7] + candidateSolutionSet[8]
    val arm4 = candidateSolutionSet[9] + candidateSolutionSet[10] + candidateSolutionSet[11]
    val arm5 = candidateSolutionSet[12] + candidateSolutionSet[13] + candidateSolutionSet[14]
    return arm1 == arm2 && arm2 == arm3 && arm3 == arm4 && arm4 == arm5
}

fun is16Digits(candidateSolutionSet: List<Int>) = candidateSolutionSet.count { it == 10 } == 1
fun value(solutionSet: List<Int>): Long {
    var minIndex = 0
    var minValue = 999
    if (solutionSet[0] < minValue) {
        minIndex = 0
        minValue = solutionSet[0]
    }
    if (solutionSet[3] < minValue) {
        minIndex = 3
        minValue = solutionSet[3]
    }
    if (solutionSet[6] < minValue) {
        minIndex = 6
        minValue = solutionSet[6]
    }
    if (solutionSet[9] < minValue) {
        minIndex = 9
        minValue = solutionSet[9]
    }
    if (solutionSet[12] < minValue) {
        minIndex = 12
    }

    val working = ArrayList<Int>()
    working.addAll(solutionSet.subList(minIndex, solutionSet.size))
    working.addAll(solutionSet.subList(0, minIndex))

    return working.joinToString("").toLong()
}