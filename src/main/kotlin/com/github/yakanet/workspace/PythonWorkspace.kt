package com.github.yakanet.workspace

import com.github.yakanet.aoc.puzzle.Puzzle
import com.github.yakanet.vsf.Tree
import java.text.DateFormat
import java.util.*

class PythonWorkspace(tree: Tree) : Workspace(tree) {
    override fun createWorkspace() {
        tree.mkdirs("src/main/python")
        tree.mkdirs("input")
        tree.writeFile("README.md",
            """
            # Advent of code
            
            Project created using [AOC Cli](https://github.com/yakanet/aoc-cli)
        """.trimIndent())
    }

    override fun createPuzzle(puzzle: Puzzle, override: Boolean) {
        tree.mkdirs("src/main/python/advent${puzzle.year}")
        tree.writeFile(
            "src/main/python/advent${puzzle.year}/advent${puzzle.day.normalize()}.py",
            """
            |#!/usr/bin/env python
            |
            |""${'"'}
            |## ${puzzle.title}
            |
            |@see <a href="${puzzle.url}">${puzzle.url}</a>  
            |@since ${DateFormat.getDateInstance().format(Date())}
            |@author ${System.getProperty("user.name")}
            |""${'"'}
            |
            |file = open('../../../../input/${puzzle.year}/input${puzzle.day.normalize()}.txt', 'r')
            |lines = file.readlines()
            |for line in lines:
            |    print(line)
            |
            |""".trimMargin(),
            override
        )

        tree.mkdirs("input/${puzzle.year}")
        tree.writeFile("input/${puzzle.year}/input${puzzle.day.normalize()}.txt", puzzle.input)
    }
}