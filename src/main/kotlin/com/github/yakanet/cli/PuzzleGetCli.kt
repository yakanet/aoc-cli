package com.github.yakanet.cli

import com.github.yakanet.aoc.auth.selenium.SeleniumAuthentication
import com.github.yakanet.aoc.puzzle.PuzzleRetriever
import com.github.yakanet.model.Day
import com.github.yakanet.model.Year
import com.github.yakanet.store.Store
import com.github.yakanet.vsf.toTree
import com.github.yakanet.workspace.Language
import com.github.yakanet.workspace.getWorkspace
import picocli.CommandLine
import java.nio.file.Path
import java.time.LocalDateTime
import java.util.concurrent.Callable

@CommandLine.Command(name = "puzzle", description = ["Get puzzle from AoC"])
class PuzzleGetCli : Callable<Int> {
    @CommandLine.Option(
        names = ["-y", "--year"],
        description = ["Puzzle year"],
    )
    var year: Int = LocalDateTime.now().year

    override fun call(): Int {
        return try {
            val tree = Path.of(".").toTree()
            val store = Store(tree)
            val workspace = getWorkspace(Language.valueOf(store.language), tree)
            val authenticator = SeleniumAuthentication(store)
            val puzzleRetriever = PuzzleRetriever(authenticator)
            val puzzle = puzzleRetriever.getSingle(Year(year), Day(2))
            workspace.createPuzzle(puzzle, true)
            if (store.useGit) {
                workspace.gitAdd()
            }
            tree.commit()
            return 0;
        } catch (e: Exception) {
            handleCliError(e)
        }
    }

}