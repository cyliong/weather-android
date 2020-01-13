package com.example.ltp.weather.storage

class FakeStorage : Storage {

    private val map = mutableMapOf<String, String>()

    override fun getString(key: String): String {
        return map[key].orEmpty()
    }

    override fun setString(key: String, value: String) {
        map[key] = value
    }

}