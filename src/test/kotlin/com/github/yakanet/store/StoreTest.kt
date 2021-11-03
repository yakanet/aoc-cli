package com.github.yakanet.store

import com.github.yakanet.vsf.Tree
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.nio.file.Path
import kotlin.test.assertContains
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class StoreTest {
    @Test
    fun `should not fail if the configuration file does not exists and failOnNonExists is false`() {
        Store(Tree(Path.of(".")), failOnNonExists = false)
        assertTrue(true)
    }

    @Test
    fun `should fail if the configuration file does not exists and failOnNonExists is true`() {
        assertThrows<StoreNotExistsException> {
            Store(Tree(Path.of(".")), failOnNonExists = true)
        }
    }

    @Test
    fun `should store the existing configuration`() {
        // Given
        val tree = Tree(Path.of("."))
        val store = Store(tree, false)

        // When
        store.language = "ABC"
        store.useGit = true
        store.credentials = "DEF"

        // Then
        val content = tree.readFile(store.configurationFile)
        assertNotNull(content)
        assertContains(content, "git=true")
        assertContains(content, "language=ABC")
        assertContains(content, "credentials=DEF")
    }
}