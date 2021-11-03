package com.github.yakanet.vsf.operations

import com.github.yakanet.vsf.Node
import picocli.CommandLine

class MkDirOperation(node: Node) : Operation(node) {
    override val name: String = "Create folder"

    override fun execute() {
        toFile().mkdir()
    }

    override fun format(): String {
        return CommandLine.Help.Ansi.AUTO.string("@|green C|@ ${toPath()} ")
    }
}