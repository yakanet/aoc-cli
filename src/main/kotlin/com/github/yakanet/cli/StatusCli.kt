package com.github.yakanet.cli

import com.github.yakanet.aoc.auth.selenium.SeleniumAuthentication
import com.github.yakanet.store.Store
import com.github.yakanet.vsf.toTree
import picocli.CommandLine
import java.nio.file.Path
import java.util.concurrent.Callable

@CommandLine.Command(name = "status", description = ["Workspace status"])
class StatusCli : Callable<Int> {
    override fun call(): Int {
        return try {
            val tree = Path.of(".").toTree()
            val store = Store(tree)
            val authenticator = SeleniumAuthentication(store)
            CommandLine.Help.Ansi.AUTO.string(
                """
                Language: @|bold,green ${store.language}|@
                Authenticated:  @|bold,${if (authenticator.isAuthenticated()) "green ✔" else "red ❌"}|@
                """.trimIndent()
            ).let { println(it) }
            return 0
        } catch (e: Exception) {
            handleCliError(e)
        }
    }
}