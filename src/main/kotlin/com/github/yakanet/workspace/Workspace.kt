package com.github.yakanet.workspace

import com.github.yakanet.aoc.puzzle.Puzzle
import com.github.yakanet.store.Store
import com.github.yakanet.vsf.NodeType
import com.github.yakanet.vsf.Tree

abstract class Workspace(protected val tree: Tree) {
    abstract fun createWorkspace()
    abstract fun createPuzzle(puzzle: Puzzle, override: Boolean)
    fun createGitConfiguration(store: Store) {
        tree.executeCommand("", "git", "init", ".")
        tree.writeFile(
            ".gitignore",
            """
            ${store.privateConfigurationFile}
            /.idea
            /input
            /build
            /.gradle
            """.trimIndent()
        )
        tree.executeCommand("", "git", "add", ".")
    }

    fun gitAdd() {
        val files = tree.listOperations()
            .filter { operation -> operation.node.type === NodeType.FILE }
            .map { operation -> operation.node.path }
        tree.executeCommand("", "git", "add", *files.toTypedArray())
    }
}

fun getWorkspace(language: Language, tree: Tree): Workspace = when (language) {
    Language.KOTLIN -> KotlinWorkspace(tree)
    Language.PYTHON -> PythonWorkspace(tree)
    Language.DENO -> DenoWorkspace(tree)
    Language.PHP -> PhpWorkspace(tree)
    Language.JAVA -> JavaWorkspace(tree)
}