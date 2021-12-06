package com.github.yakanet.store

import com.github.yakanet.vsf.Tree
import com.github.yakanet.workspace.Language
import com.github.yakanet.workspace.Workspace
import com.github.yakanet.workspace.getWorkspace
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.reflect.KProperty

class Store(val tree: Tree) {
    val publicConfigurationFile = "aoccli.properties"
    val privateConfigurationFile = ".aoccli.properties"

    fun create(): Store {
        tree.writeFile(publicConfigurationFile, "")
        tree.writeFile(privateConfigurationFile, "")
        return this
    }

    fun loadWorkspace(): Workspace {
        return language?.let {
            getWorkspace(Language.valueOf(it), tree)
        } ?: throw StoreNotExistsException()
    }

    var credentials: String? by StoreProperty(tree, privateConfigurationFile)

    var browser: String? by StoreProperty(tree, publicConfigurationFile)
    var language: String? by StoreProperty(tree, publicConfigurationFile)

    var useGit: String? by StoreProperty(tree, publicConfigurationFile)
}

private class StoreProperty(private val tree: Tree, private val configurationFile: String) {
    operator fun getValue(store: Store, property: KProperty<*>): String? {
        val propertyContent = tree.readFile(configurationFile) ?: throw StoreNotExistsException()
        return Properties().apply { load(ByteArrayInputStream(propertyContent.toByteArray())) }
            .getProperty(property.name)
    }

    operator fun setValue(store: Store, property: KProperty<*>, s: String?) {
        val propertyContent = tree.readFile(configurationFile) ?: throw StoreNotExistsException()
        val properties = Properties().apply { load(ByteArrayInputStream(propertyContent.toByteArray())) }
        properties[property.name] = s
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