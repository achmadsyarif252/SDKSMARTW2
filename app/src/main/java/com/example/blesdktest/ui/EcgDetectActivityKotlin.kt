package com.example.blesdktest.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.blesdktest.R
import com.example.blesdktest.databinding.ActivityEcgdetectBinding
import com.goodix.ble.libcomx.annotation.Nullable
import com.orhanobut.logger.Logger
import com.veepoo.protocol.VPOperateManager
import com.veepoo.protocol.listener.base.IBleWriteResponse
import com.veepoo.protocol.listener.data.IECGDetectListener
import com.veepoo.protocol.model.datas.EcgDetectInfo
import com.veepoo.protocol.model.datas.EcgDetectResult
import com.veepoo.protocol.model.datas.EcgDetectState
import java.util.*


class EcgDetectActivityKotlin : Activity(), View.OnClickListener {
    var mEcgHeartView: EcgHeartRealthView? = null
    private lateinit var binding: ActivityEcgdetectBinding

    var mContext: Context? = null
    var writeResponse: WriteResponse = WriteResponse()
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEcgdetectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mContext = this@EcgDetectActivityKotlin
        mEcgHeartView = binding.ecgRealView

        binding.greenlightdata.setOnClickListener(this)
        binding.start.setOnClickListener(this)
        binding.stop.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.start -> VPOperateManager.getMangerInstance(mContext)
                .startDetectECG(writeResponse, true, object : IECGDetectListener {
                    override fun onEcgDetectInfoChange(ecgDetectInfo: EcgDetectInfo) {
                        val message = "ecgDetectInfo-1:$ecgDetectInfo"
                        Logger.t(TAG).i(message)
                    }

                    override fun onEcgDetectStateChange(ecgDetectState: EcgDetectState) {
                        val message = "ecgDetectResultState-2:$ecgDetectState"
                        Logger.t(TAG).i(message)
                    }

                    override fun onEcgDetectResultChange(ecgDetectResult: EcgDetectResult) {
                        val message = "ecgDetectResult-3:$ecgDetectResult"
                        Logger.t(TAG).i(message)
                    }

                    override fun onEcgADCChange(ecgData: IntArray) {
                        val message = "ecgDetectADC-0:" + Arrays.toString(ecgData)
                        Logger.t(TAG).i(message)
                        mEcgHeartView!!.changeData(ecgData, 20)
                    }
                })
            R.id.stop -> {
                mEcgHeartView!!.clearData()
                VPOperateManager.getMangerInstance(mContext)
                    .stopDetectECG(writeResponse, true, null)
            }
        }
    }

    /**
     * The status of the write is returned
     */
    inner class WriteResponse : IBleWriteResponse {
        override fun onResponse(code: Int) {
            Logger.t(TAG).i("write cmd status:$code")
        }
    }

    companion object {
        private val TAG = EcgDetectActivity::class.java.simpleName
    }
}