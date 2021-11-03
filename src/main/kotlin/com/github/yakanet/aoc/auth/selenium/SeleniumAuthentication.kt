package com.github.yakanet.aoc.auth.selenium

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.yakanet.aoc.auth.AoCAuthentication
import com.github.yakanet.aoc.auth.CredentialBuilder
import com.github.yakanet.aoc.auth.exception.AuthenticationFailedException
import com.github.yakanet.aoc.auth.exception.BrowserInstanciationException
import com.github.yakanet.store.Store
import io.github.bonigarcia.wdm.WebDriverManager
import kotlinx.coroutines.runBlocking
import java.util.*

class SeleniumAuthentication(
    private val store: Store
) : AoCAuthentication {

    override fun login(): String {
        val browser = store.parseBrowser()
        val manager = WebDriverManager.getInstance(browser).apply {
            setup()
        }
        val driver = manager.create() ?: throw BrowserInstanciationException(browser)

        try {
            // Go to login URL
            val year = Calendar.getInstance().get(Calendar.YEAR)
            driver.get("https://adventofcode.com/$year/auth/login")

            // Wait for cookies to be defined
            runBlocking {
                driver.waitUntilCookiePresent("session")
            }

            // Store cookies
            return ObjectMapper().writeValueAsString(driver.manage().cookies)
        } catch (e: Exception) {
            throw AuthenticationFailedException()
        } finally {
            manager.quit(driver)
        }
    }

    override fun isAuthenticated(): Boolean {
        return store.loadCookies().firstOrNull { c -> c.name == "session" } != null
    }

    override fun addCredentials(builder: CredentialBuilder) {
        builder.addHeader("cookie", store.loadCookies().joinToString("; ") { cookie ->
            "${cookie.name}=${cookie.value}"
        })
    }
}