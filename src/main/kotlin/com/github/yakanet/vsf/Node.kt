package com.github.yakanet.vsf

import java.nio.file.Path

data class Node(val cwd: Path, val path: String, val type: NodeType, var content: String?)
