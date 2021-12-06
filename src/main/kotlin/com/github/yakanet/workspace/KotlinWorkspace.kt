package com.github.yakanet.workspace

import com.github.yakanet.aoc.puzzle.Puzzle
import com.github.yakanet.vsf.Tree
import java.text.DateFormat
import java.util.*

class KotlinWorkspace(tree: Tree) : Workspace(tree) {

    override fun createWorkspace() {
        tree.mkdirs("src/main/kotlin")
        tree.mkdirs("input")

        tree.writeFile(
            "build.gradle",
            """
            plugins {
                id 'org.jetbrains.kotlin.jvm' version '1.6.0'
            }
            repositories {
                mavenCentral()
            }
            dependencies {
                implementation platform("org.jetbrains.kotlin:kotlin-bom")
            }
        """.trimIndent(),
        )
        tree.writeFile(
            "settings.gradle",
            """rootProject.name = 'aoc'"""
        )
        tree.writeFile(
            "README.md",
            """
            # Advent of code
            
            Project created using [AOC Cli](https://github.com/yakanet/aoc-cli)
        """.trimIndent()
        )
    }

    override fun createPuzzle(puzzle: Puzzle, override: Boolean) {
        tree.mkdirs("src/main/kotlin/advent${puzzle.year}")
        tree.writeFile(
            "src/main/kotlin/advent${puzzle.year}/Advent${puzzle.day.normalize()}.kt",
            """
            |package advent${puzzle.year}
            |
            |import java.io.File
            |
            |/**
            | * ## ${puzzle.title}
            | * 
            | * @see <a href="${puzzle.url}">${puzzle.url}</a>  
            | * @since ${DateFormat.getDateInstance().format(Date())}
            | * @author ${System.getProperty("user.name")}
            | */
            |private class Advent${puzzle.day.normalize()}(private val puzzleFile: File) {
            |    fun load() = puzzleFile.readLines()
            |
            |    fun part1() {
            |        val input = load()
            |        println(input.size)
            |    }
            |
            |    fun part2() {
            |        val input = load()
            |        println(input.size)
            |    }
            |}
            |
            |fun main() {
            |    val puzzle = Advent${puzzle.day.normalize()}(File("input/${puzzle.year}/input${puzzle.day.normalize()}.txt"))
            |    puzzle.part1()
            |    puzzle.part2()
            |}
            |""".trimMargin(),
            override
        )

        tree.mkdirs("input/${puzzle.year}")
        tree.writeFile("input/${puzzle.year}/input${puzzle.day.normalize()}.txt", puzzle.input)
    }
}