package br.com.broscoder.tips.extensions

fun String.notContains(other: String): Boolean {
    return !this.contains(other)
}

fun String.isEqualTest(): Boolean {
    return this.equals("Teste")
}