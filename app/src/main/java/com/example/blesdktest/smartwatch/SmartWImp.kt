package com.example.blesdktest.smartwatch

import android.util.Log
import com.orhanobut.logger.Logger
import com.veepoo.protocol.VPOperateManager
import com.veepoo.protocol.listener.data.ISocialMsgDataListener
import com.veepoo.protocol.model.datas.FunctionSocailMsgData
import com.veepoo.protocol.model.datas.PersonInfoData
import com.veepoo.protocol.model.enums.ESex

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

    companion object {
        private val TAG = SmartWImp::class.java.simpleName
    }

}