package com.example.blesdktest.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.orhanobut.logger.Logger
import com.example.blesdktest.R;
import com.example.blesdktest.databinding.ActivityPttBinding
import com.goodix.ble.libcomx.annotation.Nullable
import com.veepoo.protocol.VPOperateManager
import com.veepoo.protocol.listener.base.IBleWriteResponse
import com.veepoo.protocol.listener.data.IPttDetectListener
import com.veepoo.protocol.model.datas.EcgDetectInfo
import com.veepoo.protocol.model.datas.EcgDetectResult
import com.veepoo.protocol.model.datas.EcgDetectState
import java.util.*


class PttActivity : Activity(), View.OnClickListener {
    private lateinit var binding: ActivityPttBinding
    var writeResponse: WriteResponse = WriteResponse()


    var ecgHeartRealthView: EcgHeartRealthView? = null
    private var iPttDetectListener: IPttDetectListener = object : IPttDetectListener {
        override fun onEcgDetectInfoChange(ecgDetectInfo: EcgDetectInfo) {
            Logger.t(TAG)
                .i("ECG measurement basic information (waveform frequency, sampling frequency):$ecgDetectInfo")
        }

        override fun onEcgDetectStateChange(ecgDetectState: EcgDetectState) {
            Logger.t(TAG).i("Status during ECG measurement, set top text:$ecgDetectState")
        }

        override fun onEcgDetectResultChange(ecgDetectResult: EcgDetectResult) {
            Logger.t(TAG)
                .i("ptt output value package (the final result of ECG measurement, in PTT mode, only when it is abnormal (that is, there is a disease), the value will be output)")
        }

        override fun onEcgADCChange(data: IntArray) {
            runOnUiThread {
                Logger.t(TAG)
                    .i("Waveform data of PTT:" + Arrays.toString(data))
                ecgHeartRealthView!!.changeData(data, 25)
            }
        }

        override fun inPttModel() {
            binding.pttModel.text = "The watch is displayed in PTT mode"
        }

        override fun outPttModel() {
            Logger.t(TAG).i("exit ptt mode")
            binding.pttModel.text = "The watch shows exiting PTT mode"
        }
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPttBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val inPttModel = intent.getBooleanExtra("inPttModel", false)
        val ptStr =
            if (inPttModel) "The watch is displayed in PTT mode" else "The watch shows exiting PTT mode"
        binding.pttModel.text = ptStr
        listenModel()

        binding.pttSignOpen.setOnClickListener(this)
    }

    private fun listenModel() {
        VPOperateManager.getMangerInstance(applicationContext)
            .settingPttModelListener(iPttDetectListener)
    }

    fun enter() {
        ecgHeartRealthView?.clearData()
        Logger.t(TAG).i("read ptt signal")
        VPOperateManager.getMangerInstance(applicationContext)
            .startReadPttSignData(writeResponse, true, iPttDetectListener)
    }

    fun exitModel() {
        Logger.t(TAG).i("close ptt signal")
        VPOperateManager.getMangerInstance(applicationContext)
            .stopReadPttSignData(writeResponse, false, iPttDetectListener)
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
        private val TAG = PttActivity::class.java.simpleName
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ptt_sign_open -> enter()
            R.id.ptt_sign_close -> exitModel()
        }
    }
}
