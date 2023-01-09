package com.example.blesdktest.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blesdktest.smartwatch.IBleSdkCallback
import com.example.blesdktest.smartwatch.SmartWImp
import com.veepoo.protocol.model.datas.*

class SmartWViewModel : ViewModel() {
    private var _sportData = MutableLiveData<SportData>()
    val sportData: LiveData<SportData> = _sportData

    private var _heartRate = MutableLiveData<HeartData>()
    val heartRate: LiveData<HeartData> = _heartRate

    private var _tempData = MutableLiveData<Float>()
    val tmpData: LiveData<Float> = _tempData

    private var _pwData = MutableLiveData<PwdData>()
    val pwdData: LiveData<PwdData> = _pwData

    private var _functionSupportData = MutableLiveData<FunctionDeviceSupportData>()
    val functioinSupportData: LiveData<FunctionDeviceSupportData> = _functionSupportData

    private var _onResponseState = MutableLiveData<Int>()
    val onResponseState: LiveData<Int> = _onResponseState

    private var _msgWeatherData = MutableLiveData<String>()
    val msgWeatherData: LiveData<String> = _msgWeatherData

    //uiAGPS
    private var _bigTranType = MutableLiveData<Int>()
    val bigTranType: LiveData<Int> = _bigTranType

    private var _isSupportAgps = MutableLiveData<Boolean>()
    val isSupportAgps: LiveData<Boolean> = _isSupportAgps

    private var _msgWeatherDataSetting = MutableLiveData<String>()
    val msgWeatherDataSetting: LiveData<String> = _msgWeatherDataSetting

    private var _lowPowerData = MutableLiveData<LowPowerData>()
    val lowPowerData: LiveData<LowPowerData> = _lowPowerData

    private var _BPFunctionData = MutableLiveData<BpFunctionData>()
    val BpFunctionData: LiveData<BpFunctionData> = _BPFunctionData


    fun cekTmp() {
        swi.checkTemp()
    }

    fun stopCekTemp() {
        swi.stopCheckTemp()
    }

    fun checkHr() {
        swi.checkHeartRate()
    }

    fun cekStep() {
        swi.readStep()
    }

    fun verification() {
        swi.verification()
    }

    fun syncProfile() {
        swi.syncProfile()
    }

    fun setWatchTime() {
        swi.setWatchTime()
    }

    fun readWeather() {
        swi.readWeather()
    }

    fun onUpdateUIAGPS() {
        swi.uiUpdateAGPS()
    }

    fun settingWeatherData() {
        swi.settingWeatherStatusInfo()
    }

    fun readLowPowerData() {
        swi.readLowPower()
    }

    fun settingLowPower() {
        swi.settingLowPower()
    }

    fun closeLowPower() {
        swi.closeLowPower()
    }

    fun bpFunctionData() {
        swi.readBloodPFunction()
    }


    private val swi = SmartWImp(object : IBleSdkCallback {
        override fun onHeartDataChange(heartData: HeartData) {
            _heartRate.value = heartData
        }

        override fun onSportDataChange(sportData: SportData) {
            _sportData.value = sportData
        }

        override fun onResponse(state: Int) {
            _onResponseState.value = state
        }

        override fun onWeatherDataChange(weatherStatusData: WeatherStatusData) {
            _msgWeatherData.value =
                "readWeatherStatusInfo onWeatherDataChange read:\n$weatherStatusData"

            _msgWeatherDataSetting.value =
                "settingWeatherStatusInfo onWeatherDataChange read:\n$weatherStatusData"
        }

        override fun onUpdateAGPS(bigTranType: Int, isSupportAgps: Boolean) {
            _bigTranType.value = bigTranType
            _isSupportAgps.value = isSupportAgps
        }

        override fun onLowerPowerDataDataChange(lowPowerData: LowPowerData) {
            _lowPowerData.value = lowPowerData
        }

        override fun onDataChange(bpFunctionData: BpFunctionData) {
            _BPFunctionData.value = bpFunctionData
        }

        override fun onTmpDataChange(tmpDetectData: TemptureDetectData) {
            _tempData.value = tmpDetectData.tempture
        }

        override fun onVerificationpwData(pwdData: PwdData) {
            _pwData.value = pwdData
        }

        override fun onVerificationFuntionSupport(functionDeviceSupportData: FunctionDeviceSupportData) {
            _functionSupportData.value = functionDeviceSupportData
        }

        override fun onSyncProfile() {

        }

    })


}