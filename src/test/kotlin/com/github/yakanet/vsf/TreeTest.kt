package com.github.yakanet.vsf

import com.github.yakanet.vsf.operations.ExecuteCommandOperation
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class TreeTest {

    lateinit var cwd: Path
    lateinit var tree: Tree

    @BeforeEach
    fun setup() {
        cwd = Files.createTempDirectory("test")
        tree = cwd.toTree()
    }

    @AfterEach
    fun tearDown() {
        cwd.toFile().deleteRecursively()
    }

    @Test
    fun `should list create directory operation`() {
        // Given
        cwd.resolve("toto").toFile().mkdir()

        // When
        tree.mkdirs("toto/hello/world/toto/tata")

        // Then
        assertEquals(tree.listOperations().size, 4)
    }

    @Test
    fun `should not write on hard drive until commit had been called`() {
        // Given
        tree.mkdirs("toto")
        tree.writeFile("toto/world.txt", "Bonjour")
        assertFalse(cwd.resolve("toto/world.txt").toFile().exists())

        // When
        tree.commit()

        // Then
        assertTrue(cwd.resolve("toto/world.txt").toFile().exists())
    }

    @Test
    fun `should write content to a non existing file`() {
        val tree = Tree(cwd)
        tree.mkdirs("toto/hello")
        tree.writeFile("toto/hello/world.txt", "Bonjour")
        assertEquals(tree.readFile("toto/hello/world.txt"), "Bonjour")
    }

    @Test
    fun `should delete file on filesystem when using commit`() {
        Files.writeString(cwd.resolve("world.txt"), "Bonjour")
        val tree = Tree(cwd)
        tree.writeFile("world.txt", "Bonjour2")
        tree.deleteFile("world.txt")
        tree.commit()

        assertFalse(Files.exists(cwd.resolve("toto/hello/world.txt")))
    }

    @Test
    fun `should stack a command execution`() {
        // When
        tree.executeCommand("", "a command")

        // Then
        assertTrue(tree.listOperations()[0] is ExecuteCommandOperation)
    }
}