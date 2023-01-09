package com.example.blesdktest

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.blesdktest.datastring.Oprate.Companion.SPORT_MODE_START_INDOOR
import com.example.blesdktest.datastring.Oprate.Companion.oprateStr
import com.example.blesdktest.adapter.GridAdapter
import com.example.blesdktest.databinding.ActivityFiturTestBinding
import com.example.blesdktest.smartwatch.WriteResponse
import com.example.blesdktest.ui.HeartRateActivity
import com.example.blesdktest.ui.SportDataActivity
import com.example.blesdktest.ui.SuhuActivity
import com.example.blesdktest.viewModel.SmartWViewModel
import com.orhanobut.logger.Logger
import com.veepoo.protocol.VPOperateManager
import com.veepoo.protocol.listener.base.IBleWriteResponse
import com.veepoo.protocol.listener.data.ISocialMsgDataListener
import com.veepoo.protocol.listener.data.ISportModelStateListener
import com.veepoo.protocol.model.datas.FunctionSocailMsgData
import com.veepoo.protocol.model.datas.PersonInfoData
import com.veepoo.protocol.model.datas.SportModelStateData
import com.veepoo.protocol.model.enums.EBPDetectModel
import com.veepoo.protocol.model.enums.EFunctionStatus
import com.veepoo.protocol.model.enums.ESex
import com.veepoo.protocol.model.enums.ESportType

class TestingFeature : AppCompatActivity() {
    //ui
    private lateinit var binding: ActivityFiturTestBinding
    private lateinit var deviceaddress: String
    var mGridData = ArrayList<String>()
    var mContext: Context = this@TestingFeature

    private lateinit var smartWViewModel: SmartWViewModel

    private var writeResponse: WriteResponse = WriteResponse()

    class WriteResponse : IBleWriteResponse {
        override fun onResponse(code: Int) {
            Log.i(TAG, "write cmd status:$code")
        }
    }

    /**
     * Password verification to obtain the following information
     */
    var watchDataDay = 3
    var weatherStyle = 0
    var contactMsgLength = 0
    var allMsgLenght = 4
    private var deviceNumber = -1
    private var deviceVersion: String? = null
    private var deviceTestVersion: String? = null
    var isOadModel = false
    var isNewSportCalc = false
    var isInPttModel = false


    private fun sendMsg(message: String, what: Int) {
        AlertDialog.Builder(this).apply {
            setTitle(what.toString())
            setMessage(message)
            setPositiveButton("OK") { dialog, _ ->
                dialog.cancel()
            }
            create()
            show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFiturTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mContext = applicationContext
        deviceaddress = intent.getStringExtra("deviceaddress").toString()
        initGridView()
        initViewModel()


    }

    private fun initViewModel() {
        smartWViewModel = ViewModelProvider(this)[SmartWViewModel::class.java]
        smartWViewModel.verification()
        smartWViewModel.syncProfile()
    }

    private fun initGridView() {
        var i = 0
        while (i < oprateStr.size) {
            val s: String = oprateStr[i]
            mGridData.add(s)
            i++
        }
        val layoutManager = GridLayoutManager(this, 3)
        binding.mainGridview.layoutManager = layoutManager

        val gridAdapter = GridAdapter(mGridData)
        gridAdapter.setOnItemClickCallback(object : GridAdapter.OnItemCliCkCallback {
            override fun onItemClicked(i: Int) {
                onClicked(i)
            }
        })
        binding.mainGridview.adapter = gridAdapter

    }

    private fun onClicked(i: Int) {
        when (i) {
            7 -> {
                startActivity(Intent(this@TestingFeature, HeartRateActivity::class.java))
            }
            9 -> {
                startActivity(Intent(this@TestingFeature, SuhuActivity::class.java))
            }
            11 -> {
                VPOperateManager.getMangerInstance(mContext).stopDetectTempture(
                    writeResponse
                ) { temptureDetectData ->
                    val message = "stopDetectTempture temptureDetectData:\n$temptureDetectData"
                    Toast.makeText(
                        this@TestingFeature,
                        "Temperature stop :$message",
                        Toast.LENGTH_SHORT
                    ).show()
                    Logger.t(TAG).i(message)
                    sendMsg(message, 1)
                }
            }
            12 -> {
                VPOperateManager.getMangerInstance(mContext).startDetectBP(
                    writeResponse,
                    { bpData ->
                        val message = "BpData date statues:\n$bpData"
                        Logger.t(TAG).i(message)
                        sendMsg(message, 1)
                        Toast.makeText(
                            this@TestingFeature,
                            "BpData date statues:\n $bpData",
                            Toast.LENGTH_SHORT
                        ).show()
                    }, EBPDetectModel.DETECT_MODEL_PUBLIC
                )
            }
            18 -> {
                startActivity(Intent(this@TestingFeature, SportDataActivity::class.java))
            }
            89 -> {
                VPOperateManager.getMangerInstance(mContext)
                    .startMultSportModel(writeResponse, object : ISportModelStateListener {
                        override fun onSportModelStateChange(sportModelStateData: SportModelStateData) {
                            val message = "indoor walk$sportModelStateData"
                            Toast.makeText(this@TestingFeature, "$message", Toast.LENGTH_SHORT)
                                .show()
                            Logger.t(TAG).i(message)
                        }

                        override fun onSportStopped() {
                            Logger.t(TAG)
                                .i(SPORT_MODE_START_INDOOR + "================================sports end @_@")
                        }
                    }, ESportType.INDOOR_WALK)
            }
        }
    }


    //mulai


    companion object {
        private val TAG = TestingFeature::class.java.simpleName

    }
}