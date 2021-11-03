package com.github.yakanet.aoc.puzzle

import com.github.yakanet.aoc.auth.AoCAuthentication
import com.github.yakanet.aoc.auth.CredentialBuilder
import com.github.yakanet.model.Day
import com.github.yakanet.model.Year
import org.apache.hc.client5.http.classic.methods.HttpGet
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class PuzzleRetriever(private val authentication: AoCAuthentication) {

    fun getSingle(year: Year, day: Day): Puzzle {
        val puzzleUrl = "https://adventofcode.com/${year}/day/${day}"
        val input = getInput("https://adventofcode.com/${year}/day/${day}/input")
        val title = getPuzzleContentHtml(puzzleUrl)
            .selectFirst("h2")!!.text().trim('-')
        return Puzzle(year, day, title, input, puzzleUrl)
    }

    private fun getPuzzleContentHtml(puzzleUrl: String): Document {
        var documentBuilder = Jsoup.connect(puzzleUrl)
        authentication.addCredentials(object : CredentialBuilder {
            override fun addHeader(key: String, value: String) {
                documentBuilder = documentBuilder.header(key, value)
            }
        })
        try {
            return documentBuilder.get()
        } catch (e: Exception) {
            TODO("${e.message}")
        }
    }

    private fun getInput(puzzleInputUrl: String): String {
        val get = HttpGet(puzzleInputUrl)
        authentication.addCredentials(object : CredentialBuilder {
            override fun addHeader(key: String, value: String) {
                get.addHeader(key, value)
            }
        })
        return HttpClients.createDefault().use { client ->
            client.execute(get) { response ->
                if (response.code != 200) {
                    TODO("Unknown code ${response.code}")
                }
                response.entity.content.readAllBytes().toString(Charsets.UTF_8)
            }
        }
    }
}