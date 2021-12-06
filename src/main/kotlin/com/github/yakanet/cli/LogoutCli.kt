package com.github.yakanet.cli

import com.github.yakanet.store.Store
import com.github.yakanet.vsf.toTree
import picocli.CommandLine.Command
import java.nio.file.Path
import java.util.concurrent.Callable

@Command(name = "logout", description = ["Clear previous authentication on AoC"])
class LogoutCli : Callable<Int> {

    override fun call(): Int {
        return try {
            val tree = Path.of(".").toTree()
            val store = Store(tree)
            store.credentials = null
            tree.commit()
            return 0
        } catch (e: Exception) {
            handleCliError(e)
        }
    }
}