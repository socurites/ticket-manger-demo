package com.socurites.issueservice.domain

enum class IssueType {
    BUG, TASK;

    companion object {
//        fun of(type: String) = valueOf(type.uppercase())
        operator fun invoke(type: String) = valueOf(type.uppercase())
    }
}

fun main() {
//    val of = IssueType.of("BUG")
    val of = IssueType("BUG")
}