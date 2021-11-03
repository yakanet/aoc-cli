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
                id 'org.jetbrains.kotlin.jvm' version '1.5.31'
            }
            repositories {
                mavenCentral()
            }
            dependencies {
                implementation platform("org.jetbrains.kotlin:kotlin-bom")
            }
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
            |/**
            | * ## ${puzzle.title}
            | * 
            | * @see <a href="${puzzle.url}">${puzzle.url}</a>  
            | * @since ${DateFormat.getDateInstance().format(Date())}
            | * @author ${System.getProperty("user.name")}
            | */
            |fun main() {
            |   val lines = File("input/${puzzle.year}/input${puzzle.day.normalize()}.txt").readLines()
            |   println(lines)
            |}
            |""".trimMargin(),
            override
        )

        tree.mkdirs("input/${puzzle.year}")
        tree.writeFile("input/${puzzle.year}/input${puzzle.day.normalize()}.txt", puzzle.input)
    }
}