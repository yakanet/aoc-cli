package com.github.yakanet.cli

import com.github.yakanet.store.Store
import com.github.yakanet.vsf.toTree
import com.github.yakanet.workspace.Language
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import java.util.concurrent.Callable
import kotlin.io.path.Path

@Command(name = "new", description = ["Create a new workspace"])
class CreateWorkspaceCli : Callable<Int> {

    @Option(
        names = ["-l", "--language"],
        description = [""],
    )
    var language: Language = Language.KOTLIN

    @Option(
        names = ["-g", "--git"],
        description = [""],
    )
    var git: Boolean = true

    @Option(
        names = ["-d", "--dry-run"],
        description = ["Dry run"],
    )
    var dryRun: Boolean = false

    override fun call(): Int {
        val tree = Path(".").toTree()
        val store = Store(tree).create()
        store.language = language.name
        store.useGit = git.toString()
        val workspace = store.loadWorkspace().apply {
            createWorkspace()
        }

        if (git) {
            workspace.createGitConfiguration(store)
        }

        if (!dryRun) {
            tree.commit()
        }
        return 0
    }
}