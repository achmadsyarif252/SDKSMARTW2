package com.example.blesdktest.smartwatch

import com.veepoo.protocol.VPOperateManager

class SmartWImp(private val callback: IBleSdkCallback) : SmartWData {
    private val writeResponse: WriteResponse = WriteResponse()

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

    override fun readStep() {
        VPOperateManager.getMangerInstance(VPOperateManager.mContext).readSportStep(
            writeResponse
        ) { sportData ->
            callback.onSportDataChange(sportData)
        }
    }
}