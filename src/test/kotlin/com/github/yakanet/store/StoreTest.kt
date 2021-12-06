package com.github.yakanet.store

import com.github.yakanet.vsf.Tree
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.nio.file.Path
import kotlin.test.assertContains
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class StoreTest {
    @Test
    fun `should not throw Exception if trying to access a stored property in a created store`() {
        assertDoesNotThrow {
            Store(Tree(Path.of("."))).create().apply {
                browser
            }
        }
    }

    @Test
    fun `should throw Exception if trying to access a stored property in a not created store`() {
        assertThrows<StoreNotExistsException> {
            Store(Tree(Path.of("."))).apply {
                browser
            }
        }
    }

    @Test
    fun `should store the existing configuration`() {
        // Given
        val tree = Tree(Path.of("."))
        val store = Store(tree).create()

        // When
        store.language = "ABC"
        store.useGit = "true"
        store.credentials = "DEF"

        // Then
        tree.readFile(store.publicConfigurationFile).let { content ->
            assertNotNull(content)
            assertContains(content, "useGit=true")
            assertContains(content, "language=ABC")

        }
        tree.readFile(store.privateConfigurationFile).let { content ->
            assertNotNull(content)
            assertContains(content, "credentials=DEF")

        }
    }
}