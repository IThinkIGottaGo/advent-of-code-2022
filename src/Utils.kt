import java.io.File
import java.lang.StackWalker.Option
import java.lang.StackWalker.StackFrame
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/${getDirectCaller().packageName}", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

inline fun <T, K, V, G> Iterable<T>.groupBySkipNullKey(
    keySelector: (T) -> K?,
    valueTransform: (T) -> V,
    groupProvider: () -> G,
    groupValueCombinator: (G, V) -> G
): Map<K, G> {
    val destination = LinkedHashMap<K, G>()
    for (element in this) {
        val key = keySelector(element)
        if (key != null) {
            val group = destination.getOrPut(key) { groupProvider() }
            destination[key] = groupValueCombinator(group, valueTransform(element))
        }
    }
    return destination
}

private fun getDirectCaller(): Class<*> =
    StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE)
        .walk { stream ->
            stream.map(StackFrame::getDeclaringClass)
                .skip(2)
                .findFirst()
                .orElseThrow()
        }