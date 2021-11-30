package com.github.yakanet

import com.github.yakanet.cli.*
import picocli.CommandLine
import picocli.CommandLine.Command
import kotlin.system.exitProcess

fun main(args: Array<String>): Unit = CommandLine(MainCli()).run {
    isCaseInsensitiveEnumValuesAllowed = true
    exitProcess(execute(*args))
}


@Command(
    mixinStandardHelpOptions = true,
    description = ["Allow to interact with advent of code website using command line"],
    subcommands = [
        LoginCli::class,
        LogoutCli::class,
        CreateWorkspaceCli::class,
        StatusCli::class,
        PuzzleGetCli::class
    ],
)
class MainCli