package com.github.yakanet.vsf

import com.github.yakanet.vsf.operations.*
import java.nio.file.Files
import java.nio.file.Path

class Tree(private val cwd: Path) {
    private val fs = mutableMapOf<String, Node>()
    private val operations = mutableListOf<Operation>()

    fun mkdirs(path: String) {
        load(path, NodeType.DIRECTORY)
        val paths = path.split("/")
        var index = 0
        while (paths.size >= ++index) {
            val key = paths.subList(0, index).joinToString("/")
            if (!cwd.resolve(key).toFile().exists())
                operations.add(MkDirOperation(fs[key]!!))
        }
    }

    private fun load(path: String, lastNode: NodeType) {
        val paths = path.split("/")
        var index = 0
        while (paths.size >= ++index) {
            val key = paths.subList(0, index).joinToString("/")
            if (!fs.containsKey(key)) {
                if (paths.size == index) {
                    val file = cwd.resolve(key).toFile()
                    val content = if (file.exists() && lastNode === NodeType.FILE)
                        file.readText()
                    else null
                    fs[key] = Node(cwd, key, lastNode, content)
                } else {
                    fs[key] = Node(cwd, key, NodeType.DIRECTORY, null)
                }
            }
        }
    }

    fun writeFile(file: String, content: String, override: Boolean = false) {
        load(file, NodeType.FILE)
        if (!Files.exists(cwd.resolve(file)) || override) {
            fs[file]!!.content = content
            operations.add(UpdateFile(fs[file]!!))
        }
    }

    fun readFile(file: String): String? {
        load(file, NodeType.FILE)
        return fs[file]!!.content
    }

    fun deleteFile(file: String) {
        load(file, NodeType.FILE)
        operations.add(DeleteFile(fs[file]!!))
    }

    fun commit() {
        for (operation in operations) {
            println(operation.format())
            operation.execute()
        }
    }

    fun listOperations() = operations.toList()

    fun executeCommand(path: String, vararg command: String) {
        load(path, lastNode = NodeType.DIRECTORY)
        operations.add(ExecuteCommandOperation(listOf(*command), fs[path]!!))
    }

    fun hasFile(path: String) = fs.containsKey(path)
}

fun Path.toTree() = Tree(this)