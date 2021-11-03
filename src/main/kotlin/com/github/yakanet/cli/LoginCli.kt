package com.github.yakanet.cli

import com.github.yakanet.aoc.auth.selenium.SeleniumAuthentication
import com.github.yakanet.store.Store
import com.github.yakanet.vsf.toTree
import io.github.bonigarcia.wdm.config.DriverManagerType
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import java.nio.file.Path
import java.util.concurrent.Callable

@Command(name = "login", description = ["Login to AoC"])
class LoginCli : Callable<Int> {

    @Option(
        names = ["-b", "--browser"],
        description = ["Select browser to use for authentication: chrome, chromium, edge, firefox (default), opera, safari"],
    )
    var browser: DriverManagerType = DriverManagerType.FIREFOX

    @Option(
        names = ["-d", "--dry-run"],
        description = ["Dry run"],
    )
    var dryRun: Boolean = false

    override fun call(): Int {
        return try {
            val tree = Path.of(".").toTree()
            val store = Store(tree)
            store.browser = browser.name
            val authenticator = SeleniumAuthentication(store)
            if (authenticator.isAuthenticated()) {
                println("Override existing authentication")
            }
            val token = authenticator.login();
            store.credentials = token
            if (!dryRun) {
                tree.commit()
            }
            0
        } catch (e: Exception) {
            handleCliError(e)
        }
    }
}