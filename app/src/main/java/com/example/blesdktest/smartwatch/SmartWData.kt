package com.example.blesdktest.smartwatch

interface SmartWData {
    fun checkHeartRate()
    fun checkTemp()
    fun stopCheckTemp()
    fun readStep()
    fun verification()
    fun syncProfile()
    fun setWatchTime()
    fun readWeather()
    fun uiUpdateAGPS()
    fun settingWeatherStatusInfo()
    fun readLowPower()
    fun settingLowPower()
    fun closeLowPower()
    fun readBloodPFunction()
}