package com.github.yakanet.vsf.operations

import com.github.yakanet.vsf.Node
import picocli.CommandLine

class ExecuteOperation(private val command: List<String>, node: Node) : Operation(node) {
    override val name = "Execute command ${command.joinToString(" ")}"

    override fun execute() {
        ProcessBuilder(*command.toTypedArray())
            .directory(toFile())
            .start()
            .waitFor()
    }

    override fun format(): String {
        return CommandLine.Help.Ansi.AUTO.string("@|blue E|@ ${command.joinToString(" ")} ")
    }

}
