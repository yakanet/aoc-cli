package com.github.yakanet.workspace

import com.github.yakanet.aoc.puzzle.Puzzle
import com.github.yakanet.vsf.Tree
import java.text.DateFormat
import java.util.*

class PhpWorkspace(tree: Tree) : Workspace(tree) {
    override fun createWorkspace() {
        tree.mkdirs("src/main/php")
        tree.mkdirs("input")
        tree.writeFile("README.md",
            """
            # Advent of code
            
            Project created using [AOC Cli](https://github.com/yakanet/aoc-cli)
        """.trimIndent())
    }

    override fun createPuzzle(puzzle: Puzzle, override: Boolean) {
        tree.mkdirs("src/main/php/advent${puzzle.year}")
        tree.writeFile(
            "src/main/php/advent${puzzle.year}/advent${puzzle.day.normalize()}.php",
            """
            |#!/usr/bin/php
            |<?php
            |
            |/**
            | * ## ${puzzle.title}
            | * 
            | * @see <a href="${puzzle.url}">${puzzle.url}</a>  
            | * @since ${DateFormat.getDateInstance().format(Date())}
            | * @author ${System.getProperty("user.name")}
            | */
            |class Advent${puzzle.day.normalize()} {
            |    function __construct(private string ${'$'}file) {
            |    }
            |
            |    function load(){
            |        ${'$'}lines = file(${'$'}this->file);
            |        return ${'$'}lines;
            |    }
            |
            |    function part1(){
            |        ${'$'}input = ${'$'}this->load();
            |        echo count(${'$'}input), PHP_EOL;
            |    }
            |
            |    function part2(){
            |        ${'$'}input = ${'$'}this->load();
            |        echo count(${'$'}input), PHP_EOL;
            |    }
            |}
            |
            |${'$'}puzzle = new Advent${puzzle.day.normalize()}('input/${puzzle.year}/input${puzzle.day.normalize()}.txt');
            |${'$'}puzzle->part1();
            |${'$'}puzzle->part2();
            |""".trimMargin(),
            override
        )

        tree.mkdirs("input/${puzzle.year}")
        tree.writeFile("input/${puzzle.year}/input${puzzle.day.normalize()}.txt", puzzle.input)
    }
}