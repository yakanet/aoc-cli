package com.github.yakanet.aoc.auth.selenium

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.yakanet.store.Store
import io.github.bonigarcia.wdm.config.DriverManagerType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.openqa.selenium.Cookie
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebDriverException
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun Store.parseBrowser() = DriverManagerType.valueOf(browser!!)

fun Store.loadCookies(): Set<Cookie> {
    return try {
        val credential = credentials
        val rawCookies = ObjectMapper().readValue(credential, object : TypeReference<List<HashMap<String, Any?>>>() {})
            ?: return setOf()
        return rawCookies.mapNotNull { data ->
            Cookie.Builder(data["name"] as String, data["value"] as String)
                .domain(data["domain"] as String)
                .expiresOn(Date(data["expiry"] as Long))
                .path(data["path"] as String)
                .isHttpOnly(data["httpOnly"] as Boolean)
                .isSecure(data["secure"] as Boolean)
                .build()
                .let { cookie -> if (cookie.expiry.after(Date())) cookie else null }
        }.toSet()
    } catch (e: Exception) {
        System.err.println("Unable to retrieve a previous credential token")
        setOf()
    }
}

suspend fun WebDriver.waitUntilCookiePresent(cookieName: String) = suspendCoroutine<Boolean> {
    CoroutineScope(Dispatchers.Default).launch {
        try {
            do {
                delay(1000)
            } while (manage().cookies.firstOrNull { c -> c.name == cookieName } == null)
            it.resume(true)
        } catch (e: WebDriverException) {
            it.resumeWithException(e)
        }
    }
}