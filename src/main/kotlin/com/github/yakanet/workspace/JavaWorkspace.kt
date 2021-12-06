package com.github.yakanet.workspace

import com.github.yakanet.aoc.puzzle.Puzzle
import com.github.yakanet.vsf.Tree
import java.text.DateFormat
import java.util.*

class JavaWorkspace(tree: Tree) : Workspace(tree) {

    override fun createWorkspace() {
        tree.mkdirs("src/main/java")
        tree.mkdirs("input")

        tree.writeFile(
            "build.gradle",
            """
            plugins {
                id 'java'
            }
            repositories {
                mavenCentral()
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
        tree.mkdirs("src/main/java/advent${puzzle.year}")
        tree.writeFile(
            "src/main/java/advent${puzzle.year}/Advent${puzzle.day.normalize()}.java",
            """
            |package advent${puzzle.year};
            |
            |import java.io.*;
            |import java.nio.file.Files;
            |import java.util.*;
            |
            |/**
            | * ## ${puzzle.title}
            | * 
            | * @see <a href="${puzzle.url}">${puzzle.url}</a>  
            | * @since ${DateFormat.getDateInstance().format(Date())}
            | * @author ${System.getProperty("user.name")}
            | */
            |class Advent${puzzle.day.normalize()} {
            |    private final File puzzleFile;
            |
            |    private Advent${puzzle.day.normalize()}(File puzzleFile) {
            |        this.puzzleFile = puzzleFile;
            |    }
            |
            |    public List<String> load() {
            |        try {
            |            return Files.readAllLines(puzzleFile.toPath());
            |        } catch (IOException e) {
            |            throw new RuntimeException(e);
            |        }
            |    }
            |
            |    public void part1() {
            |        var input = load();
            |        System.out.println(input.size());
            |    }
            |
            |    public void part2() {
            |        var input = load();
            |        System.out.println(input.size());
            |    }
            |
            |    public static void main(String[] args) {
            |        var puzzle = new Advent${puzzle.day.normalize()}(new File("input/${puzzle.year}/input${puzzle.day.normalize()}.txt"));
            |        puzzle.part1();
            |        puzzle.part2();
            |    }
            |}
            |
            |""".trimMargin(),
            override
        )

        tree.mkdirs("input/${puzzle.year}")
        tree.writeFile("input/${puzzle.year}/input${puzzle.day.normalize()}.txt", puzzle.input)
    }
}