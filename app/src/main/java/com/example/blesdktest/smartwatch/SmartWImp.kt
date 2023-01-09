package com.example.blesdktest.smartwatch

import android.util.Log
import com.orhanobut.logger.Logger
import com.veepoo.protocol.VPOperateManager
import com.veepoo.protocol.listener.data.ISocialMsgDataListener
import com.veepoo.protocol.model.datas.FunctionSocailMsgData
import com.veepoo.protocol.model.datas.PersonInfoData
import com.veepoo.protocol.model.enums.ESex
import com.veepoo.protocol.model.enums.ETimeMode
import com.veepoo.protocol.model.enums.EWeatherType
import com.veepoo.protocol.model.settings.DeviceTimeSetting
import com.veepoo.protocol.model.settings.WeatherStatusSetting
import com.veepoo.protocol.shareprence.VpSpGetUtil

class SmartWImp(private val callback: IBleSdkCallback) : SmartWData {
    private val writeResponse: WriteResponse = WriteResponse()

    private var socialMsgDataListener: ISocialMsgDataListener = object : ISocialMsgDataListener {
        override fun onSocialMsgSupportDataChange(socailMsgData: FunctionSocailMsgData) {
            val message = "FunctionSocailMsgData:\n$socailMsgData"
            Logger.t(TAG).i(message)
        }

        override fun onSocialMsgSupportDataChange2(socailMsgData: FunctionSocailMsgData) {
            val message = "FunctionSocailMsgData2:\n$socailMsgData"
            Log.i(TAG, message)
        }
    }

    override fun checkHeartRate() {
        VPOperateManager.getMangerInstance(VPOperateManager.mContext).startDetectHeart(
            writeResponse
        ) {
            callback.onHeartDataChange(it)
        }
    }

    override fun checkTemp() {
        VPOperateManager.getMangerInstance(VPOperateManager.mContext).startDetectTempture(
            writeResponse
        ) { temptureDetectData ->
            callback.onTmpDataChange(temptureDetectData)
        }
    }

    override fun stopCheckTemp() {
        VPOperateManager.getMangerInstance(VPOperateManager.mContext).stopDetectTempture(
            writeResponse
        ) { temptureDetectData ->
            Log.i(
                TAG,
                "Stop Detect Temp : Current Temperature  ${temptureDetectData.tempture}"
            )
            callback.onTmpDataChange(temptureDetectData)
        }
    }

    override fun readStep() {
        VPOperateManager.getMangerInstance(VPOperateManager.mContext).readSportStep(
            writeResponse
        ) { sportData ->
            callback.onSportDataChange(sportData)
        }
    }

    override fun verification() {
        val is24Hourmodel = false
        VPOperateManager.getMangerInstance(VPOperateManager.mContext)
            .confirmDevicePwd(writeResponse,
                { pwdData ->
                    callback.onVerificationpwData(pwdData)
                }, { functionSupport ->
                    callback.onVerificationFuntionSupport(functionSupport)
                    val message = "FunctionDeviceSupportData:\n$functionSupport"
                    Log.i(TAG, message)
                }, socialMsgDataListener,
                { customSettingData ->
                    val message = "CustomSettingData:\n$customSettingData"
                    Log.i(TAG, message)
                }, "0000", is24Hourmodel
            )
    }

    override fun syncProfile() {
        VPOperateManager.getMangerInstance(VPOperateManager.mContext).syncPersonInfo(
            writeResponse,
            { EOperateStatus ->
                val message = "Synchronize personal information:\n$EOperateStatus"
                Logger.t(TAG).i(message)
            }, PersonInfoData(ESex.MAN, 178, 60, 20, 8000)
        )
    }

    override fun setWatchTime() {
        val deviceTimeSetting = DeviceTimeSetting(2020, 11, 6, 15, 30, 14, ETimeMode.MODE_12)
        VPOperateManager.getMangerInstance(VPOperateManager.mContext).settingTime(
            writeResponse,
            { state ->
                val message = "settingTime response :\n$state"
                callback.onResponse(state)
                Log.i(TAG, message)
            }, deviceTimeSetting
        )
    }

    override fun readWeather() {
        VPOperateManager.getMangerInstance(VPOperateManager.mContext).readWeatherStatusInfo(
            writeResponse
        ) { weatherStatusData ->
            callback.onWeatherDataChange(weatherStatusData)
        }
    }

    override fun uiUpdateAGPS() {
        val bigTranType = VpSpGetUtil.getVpSpVariInstance(VPOperateManager.mContext).bigTranType
        val isSupportAgps = VpSpGetUtil.getVpSpVariInstance(VPOperateManager.mContext).isSupoortAGPS
        callback.onUpdateAGPS(bigTranType, isSupportAgps)
    }

    override fun settingWeatherStatusInfo() {
        val weatherStatusSetting = WeatherStatusSetting(0, true, EWeatherType.C)
        VPOperateManager.getMangerInstance(VPOperateManager.mContext).settingWeatherStatusInfo(
            writeResponse, weatherStatusSetting
        ) { weatherStatusData ->
            callback.onWeatherDataChange(weatherStatusData)
        }
    }

    override fun readLowPower() {
        VPOperateManager.getMangerInstance(VPOperateManager.mContext).readLowPower(
            writeResponse
        ) { lowPowerData ->
            callback.onLowerPowerDataDataChange(lowPowerData)
        }
    }

    override fun settingLowPower() {
        VPOperateManager.getMangerInstance(VPOperateManager.mContext).settingLowpower(
            writeResponse,
            { lowPowerData ->
                callback.onLowerPowerDataDataChange(lowPowerData)
            }, true
        )
    }

    override fun closeLowPower() {
        VPOperateManager.getMangerInstance(VPOperateManager.mContext).settingLowpower(
            writeResponse,
            { lowPowerData ->
                callback.onLowerPowerDataDataChange(lowPowerData)
            }, false
        )
    }

    override fun readBloodPFunction() {
        VPOperateManager.getMangerInstance(VPOperateManager.mContext).readBpFunctionState(
            writeResponse
        ) { bpFunctionData ->
            callback.onDataChange(bpFunctionData)
        }
    }


    companion object {
        private val TAG = SmartWImp::class.java.simpleName
    }

}