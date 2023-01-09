package com.example.blesdktest.smartwatch

import com.veepoo.protocol.model.datas.*
import com.veepoo.protocol.model.enums.ECameraStatus


interface IBleSdkCallback {
    //    fun onConnectDevice(mac: String, deviceName: String)
//    fun onConfirmPwd(writeResponse: FiturTest.WriteResponse)
//    fun onSyncPersonInfo(writeResponse: FiturTest.WriteResponse)
//    fun onSportModelStateChange(var1: SportModelStateData?)
//    fun onReadOriginProgress(progress: Float)
    fun onHeartDataChange(heartData: HeartData)
    fun onTmpDataChange(tmpDetectData: TemptureDetectData)
    fun onVerificationpwData(pwdData: PwdData)
    fun onVerificationFuntionSupport(functionDeviceSupportData: FunctionDeviceSupportData)
    fun onSyncProfile()
    fun onSportDataChange(sportData: SportData)
    fun onResponse(state: Int)
    fun onWeatherDataChange(weatherStatusData: WeatherStatusData)
    fun onUpdateAGPS(bigTranType: Int, isSupportAgps: Boolean)
    fun onLowerPowerDataDataChange(lowPowerData: LowPowerData)
    fun onDataChange(bpFunctionData: BpFunctionData)

    //    fun onCameraDataChange(operateStatus: ECameraStatus)
//    fun onHeartWaringDataChange(heartWaringData: HeartWaringData)
    //    fun onPwdDataChange(pwd: PwdData)

//    fun onSportStopped()
//    fun onStartDetectHeart()


}