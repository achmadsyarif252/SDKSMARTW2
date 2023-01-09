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
//    fun onCameraDataChange(operateStatus: ECameraStatus)
//    fun onHeartWaringDataChange(heartWaringData: HeartWaringData)
    fun onSportDataChange(sportData: SportData)
//    fun onPwdDataChange(pwd: PwdData)
    fun onTmpDataChange(tmpDetectData: TemptureDetectData)
//    fun onSportStopped()
//    fun onStartDetectHeart()


}