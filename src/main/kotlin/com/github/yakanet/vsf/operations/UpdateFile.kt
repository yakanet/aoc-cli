package com.github.yakanet.vsf.operations

import com.github.yakanet.vsf.Node
import picocli.CommandLine

class UpdateFile(node: Node) : Operation(node) {
    override val name: String = "Update file"

    override fun execute() {
        toFile().writeText(node.content!!)
    }

    override fun format(): String {
        return CommandLine.Help.Ansi.AUTO.string("@|green U|@ ${toPath()} ")
    }
}