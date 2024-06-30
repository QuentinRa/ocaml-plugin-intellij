package com.dune.language.parser

object DuneKeywords {
    const val LIBRARY = "library"
    const val EXECUTABLE = "executable"
    const val EXECUTABLES = "executables"
    const val NAME = "name"
    const val NAMES = "names"
}

enum class DuneTargetExtension(val value: String) {
    EXECUTABLE(".exe"),
    LIBRARY(".a"),
}