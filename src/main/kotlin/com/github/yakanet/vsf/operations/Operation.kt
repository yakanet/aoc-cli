package com.github.yakanet.vsf.operations

import com.github.yakanet.vsf.Node
import java.io.File
import java.nio.file.Path

abstract class Operation(val node: Node) {
    abstract val name: String
    abstract fun execute()
    abstract fun format(): String
}

fun Operation.toPath(): Path = node.cwd.resolve(node.path)
fun Operation.toFile(): File = toPath().toFile()
