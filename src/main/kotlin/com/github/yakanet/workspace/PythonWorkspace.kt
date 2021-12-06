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
            |class Advent${puzzle.day.normalize()}:
            |    def __init__(self, input_file):
            |        self.input_file = input_file
            |
            |    def load(self):
            |        file = open(self.input_file, 'r')
            |        return file.readlines()
            |
            |    def part1(self):
            |        input = self.load()
            |        print(len(input))
            |
            |    def part2(self):
            |        input = self.load()
            |        print(len(input))
            |
            |puzzle = Advent${puzzle.day.normalize()}('input/${puzzle.year}/input${puzzle.day.normalize()}.txt')
            |puzzle.part1()
            |puzzle.part2()
            |""".trimMargin(),
            override
        )

        tree.mkdirs("input/${puzzle.year}")
        tree.writeFile("input/${puzzle.year}/input${puzzle.day.normalize()}.txt", puzzle.input)
    }
}