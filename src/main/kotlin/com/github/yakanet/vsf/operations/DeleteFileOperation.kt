package com.github.yakanet.vsf.operations

import com.github.yakanet.vsf.Node
import picocli.CommandLine
import java.nio.file.Files

class DeleteFileOperation(node: Node) : Operation(node) {
    override val name = "Delete file"

    override fun execute() {
        Files.delete(toPath())
    }

    override fun format(): String {
        return CommandLine.Help.Ansi.AUTO.string("@|red D|@ ${toPath()} ")
    }
}