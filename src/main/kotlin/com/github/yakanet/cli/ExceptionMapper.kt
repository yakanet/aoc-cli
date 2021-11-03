package com.github.yakanet.cli

import com.github.yakanet.aoc.auth.exception.BrowserInstanciationException
import com.github.yakanet.store.StoreNotExistsException
import picocli.CommandLine

fun handleCliError(exception: Exception): Int {
    return when (exception) {
        is StoreNotExistsException -> {
            CommandLine.Help.Ansi.AUTO.string(
                """
                @|red Error: Not a valid workspace.|@
                  Try to create a workspace using: aoc new
                """.trimIndent()
            ).let { println(it) }
            1
        }
        is BrowserInstanciationException -> {
            CommandLine.Help.Ansi.AUTO.string(
                """
                @|red Error: Unable to launch ${exception.browser.browserName}.|@
                  Try with another browser using: aoc login --browser=@|bold,yellow chrome|@
                """.trimIndent()
            ).let { println(it) }
            2
        }
        else -> {
            CommandLine.Help.Ansi.AUTO.string("@|red Error: ${exception.message}")
                .let { println(it) }
            9
        }
    }
}