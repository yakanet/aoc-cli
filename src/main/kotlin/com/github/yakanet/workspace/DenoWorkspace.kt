package com.github.yakanet.workspace

import com.github.yakanet.aoc.puzzle.Puzzle
import com.github.yakanet.vsf.Tree
import java.text.DateFormat
import java.util.*

class DenoWorkspace(tree: Tree) : Workspace(tree) {

    override fun createWorkspace() {
        tree.mkdirs("src/main/deno")
        tree.mkdirs("input")
        tree.writeFile(
            "README.md",
            """
            # Advent of code
            
            Project created using [AOC Cli](https://github.com/yakanet/aoc-cli)
        """.trimIndent()
        )
    }

    override fun createPuzzle(puzzle: Puzzle, override: Boolean) {
        tree.mkdirs("src/main/deno/advent${puzzle.year}")
        tree.writeFile(
            "src/main/deno/advent${puzzle.year}/Advent${puzzle.day.normalize()}.ts",
            """
            |/**
            | * ## ${puzzle.title}
            | * 
            | * @see <a href="${puzzle.url}">${puzzle.url}</a>  
            | * @since ${DateFormat.getDateInstance().format(Date())}
            | * @author ${System.getProperty("user.name")}
            | */
            |class Advent${puzzle.day.normalize()} {
            |    async load() {
            |        const file = await Deno.readTextFile('input/${puzzle.year}/input${puzzle.day.normalize()}.txt');
            |        return file.split('\n');
            |    }
            |
            |    async part1() {
            |        const input = await this.load();
            |        console.log(input.length);
            |    }
            |
            |    async part2() {
            |        const input = await this.load();
            |        console.log(input.length);
            |    }
            |}
            |
            |const puzzle = new Advent${puzzle.day.normalize()}()
            |await puzzle.part1()
            |await puzzle.part2()
            |""".trimMargin(),
            override
        )
        tree.mkdirs("input/${puzzle.year}")
        tree.writeFile("input/${puzzle.year}/input${puzzle.day.normalize()}.txt", puzzle.input)
    }
}