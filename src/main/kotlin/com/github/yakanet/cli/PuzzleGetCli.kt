package com.github.yakanet.cli

import com.github.yakanet.aoc.auth.selenium.SeleniumAuthentication
import com.github.yakanet.aoc.puzzle.PuzzleRetriever
import com.github.yakanet.model.Day
import com.github.yakanet.model.Year
import com.github.yakanet.store.Store
import com.github.yakanet.vsf.toTree
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

    @CommandLine.Option(
        names = ["-d", "--day"],
        description = ["Puzzle day"],
    )
    var day: Int = LocalDateTime.now().dayOfMonth

    override fun call(): Int {
        return try {
            val tree = Path.of(".").toTree()
            val store = Store(tree)
            val workspace = store.loadWorkspace()
            val authenticator = SeleniumAuthentication(store)
            val puzzleRetriever = PuzzleRetriever(authenticator)
            val puzzle = puzzleRetriever.getSingle(Year(year), Day(day))
            workspace.createPuzzle(puzzle, true)
            if (store.useGit.toBoolean()) {
                workspace.gitAdd()
            }
            tree.commit()
            return 0
        } catch (e: Exception) {
            handleCliError(e)
        }
    }

}