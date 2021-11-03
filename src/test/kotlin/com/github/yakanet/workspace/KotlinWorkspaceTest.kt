package com.github.yakanet.workspace

import com.github.yakanet.aoc.puzzle.Puzzle
import com.github.yakanet.model.Day
import com.github.yakanet.model.Year
import com.github.yakanet.vsf.Tree
import com.github.yakanet.vsf.toTree
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Path
import kotlin.test.assertTrue

internal class KotlinWorkspaceTest {

    lateinit var tree: Tree
    lateinit var workspace: KotlinWorkspace

    @BeforeEach
    fun setup() {
        tree = Path.of("xxx").toTree()
        workspace = KotlinWorkspace(tree)
        workspace.createWorkspace()
    }

    @Test
    fun `when creating puzzle with override`() {
        val puzzle = Puzzle(Year(2020), Day(3), "Bonjour à tous", "112\n222", "https://google.com")
        workspace.createPuzzle(puzzle, true)
        assertTrue(tree.hasFile("src/main/kotlin/advent2020/Advent03.kt"))
        assertTrue(tree.hasFile("input/2020/input03.txt"))
    }
}
