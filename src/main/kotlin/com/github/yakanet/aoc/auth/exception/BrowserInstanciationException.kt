package com.github.yakanet.aoc.auth.exception

import io.github.bonigarcia.wdm.config.DriverManagerType

class BrowserInstanciationException(val browser: DriverManagerType) : RuntimeException()