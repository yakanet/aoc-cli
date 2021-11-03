package com.github.yakanet.store

import com.github.yakanet.vsf.Tree
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*

class Store(val tree: Tree, failOnNonExists: Boolean = true) {
    val configurationFile = "aoccli.properties"
    private val properties = Properties().apply {
        val propertyContent = tree.readFile(configurationFile)
        if (propertyContent === null && failOnNonExists) {
            throw StoreNotExistsException()
        }
        propertyContent?.let { load(ByteArrayInputStream(it.toByteArray())) }
    }

    var credentials: String?
        get() = properties.getProperty("credentials")
        set(value) {
            properties.setProperty("credentials", value)
            commit()
        }

    var browser: String?
        get() = properties.getProperty("browser")
        set(value) {
            properties.setProperty("browser", value)
            commit()
        }
    var language: String
        get() = properties.getProperty("language")
        set(value) {
            properties.setProperty("language", value)
            commit()
        }

    var useGit: Boolean
        get() = properties.getProperty("git", "true").toBoolean()
        set(value) {
            properties.setProperty("git", value.toString())
            commit()
        }

    private fun commit() {
        tree.writeFile(
            configurationFile,
            ByteArrayOutputStream().use {
                properties.store(it, "DO NOT EDIT MANUALLY. THIS FILE IS MANAGED BY aoecli.")
                it
            }.toString(),
            true
        )
    }
}