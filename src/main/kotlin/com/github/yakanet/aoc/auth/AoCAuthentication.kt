package com.github.yakanet.aoc.auth

interface AoCAuthentication {
    /**
     * Launch the process of login
     *
     * @return A valid credential token
     * @throws com.github.yakanet.aoc.auth.exception.AuthenticationFailedException if unable to authenticate
     */
    fun login(): String

    /**
     * Check if a user is already authenticated
     *
     * @return true if the credential is valid
     */
    fun isAuthenticated(): Boolean

    /**
     * Format credentials
     */
    fun addCredentials(builder: CredentialBuilder)
}

interface CredentialBuilder {
    fun addHeader(key: String, value: String)
}