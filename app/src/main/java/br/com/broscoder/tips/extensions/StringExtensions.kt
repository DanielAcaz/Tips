package br.com.broscoder.tips.extensions

fun String.notContains(other: String): Boolean {
    return !this.contains(other)
}