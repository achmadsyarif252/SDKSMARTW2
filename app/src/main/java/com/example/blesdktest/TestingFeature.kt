package com.example.blesdktest

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.blesdktest.adapter.GridAdapter
import com.example.blesdktest.databinding.ActivityFiturTestBinding
import com.example.blesdktest.datastring.Oprate.Companion.SPORT_MODE_START_INDOOR
import com.example.blesdktest.datastring.Oprate.Companion.oprateStr
import com.example.blesdktest.ui.*
import com.example.blesdktest.viewModel.SmartWViewModel
import com.orhanobut.logger.Logger
import com.veepoo.protocol.VPOperateManager
import com.veepoo.protocol.listener.base.IBleWriteResponse
import com.veepoo.protocol.listener.data.ISportModelStateListener
import com.veepoo.protocol.model.datas.PwdData
import com.veepoo.protocol.model.datas.SportModelStateData
import com.veepoo.protocol.model.enums.EBPDetectModel
import com.veepoo.protocol.model.enums.ESportType

class TestingFeature : AppCompatActivity() {
    //ui
    private lateinit var binding: ActivityFiturTestBinding
    private lateinit var deviceaddress: String
    var mGridData = ArrayList<String>()
    var mContext: Context = this@TestingFeature
    private lateinit var pwdData: PwdData

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
        smartWViewModel.pwdData.observe(this) {
            pwdData.deviceNumber = it.deviceNumber
            pwdData.deviceTestVersion = it.deviceTestVersion
            pwdData.deviceVersion = it.deviceVersion
            pwdData.isHaveDrinkData = it.isHaveDrinkData
            pwdData.findPhoneFunction = it.findPhoneFunction
            pwdData.isOpenNightTurnWriste = it.isOpenNightTurnWriste
            pwdData.wearDetectFunction = it.wearDetectFunction
        }
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
            7 -> startActivity(Intent(this@TestingFeature, HeartRateActivity::class.java))
            9 -> startActivity(Intent(this@TestingFeature, SuhuActivity::class.java))
            10 -> {
                smartWViewModel.stopCekTemp()
                smartWViewModel.tmpData.observe(this) {
                    Toast.makeText(
                        this@TestingFeature,
                        "Temperature Check Stop,Current Temp : $it",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            83 -> {
                smartWViewModel.setWatchTime()
                smartWViewModel.onResponseState.observe(this) {
                    Toast.makeText(
                        this@TestingFeature,
                        "Watch Time State : $it",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            96 -> {
                startActivity(Intent(this@TestingFeature, EcgDetectActivity::class.java))
            }

            99 -> {
                smartWViewModel.readLowPowerData()
                smartWViewModel.lowPowerData.observe(this) {
                    AlertDialog.Builder(this).apply {
                        setTitle(getString(R.string.app_name))
                        setMessage(it.toString())
                        setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        create()
                        show()
                    }
                }
            }

            100 -> {
                smartWViewModel.settingLowPower()
                smartWViewModel.lowPowerData.observe(this) {
                    AlertDialog.Builder(this).apply {
                        setTitle(getString(R.string.app_name))
                        setMessage(it.toString())
                        setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        create()
                        show()
                    }
                }
            }

            101 -> {
                smartWViewModel.closeLowPower()
                smartWViewModel.lowPowerData.observe(this) {
                    AlertDialog.Builder(this).apply {
                        setTitle(getString(R.string.app_name))
                        setMessage(it.toString())
                        setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        create()
                        show()
                    }
                }
            }

//            106 -> {
//                val intent = Intent(this@TestingFeature, PttActivity::class.java)
//                intent.putExtra("inPttModel", isInPttModel)
//                startActivity(intent)
//            }

            107 -> {
                smartWViewModel.bpFunctionData()
                smartWViewModel.BpFunctionData.observe(this) {
                    val message = """
                        readBpFunctionState close:
                        ${it.toString()}
                        """.trimIndent()
                    AlertDialog.Builder(this).apply {
                        setTitle("BloodPressure Function Data")
                        setMessage(message)
                        setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        create()
                        show()
                    }
                }
            }

            109 -> {
                smartWViewModel.readWeather()
                smartWViewModel.msgWeatherData.observe(this) {
                    AlertDialog.Builder(this).apply {
                        setTitle(getString(R.string.app_name))
                        setMessage(it)
                        setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        create()
                        show()
                    }
                }
            }

            110 -> {
                smartWViewModel.settingWeatherData()
                smartWViewModel.msgWeatherDataSetting.observe(this) {
                    AlertDialog.Builder(this).apply {
                        setTitle(getString(R.string.app_name))
                        setMessage(it)
                        setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        create()
                        show()
                    }
                }
            }

            115 -> {
                smartWViewModel.onUpdateUIAGPS()
                var bigTranType = 0
                var isSupportAgps = false
                smartWViewModel.bigTranType.observe(this) {
                    bigTranType = it
                }
                smartWViewModel.isSupportAgps.observe(this) {
                    isSupportAgps = it
                }
                if (bigTranType == 2 && isSupportAgps) {
                    val intent = Intent(this@TestingFeature, UIUpdateAPGSActivity::class.java)
                    intent.putExtra("deviceNumber", pwdData.deviceNumber.toString())
                    intent.putExtra("deviceVersion", pwdData.deviceVersion)
                    intent.putExtra("deviceTestVersion", pwdData.deviceTestVersion)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        mContext,
                        "Tidak mendukung tampilan jam kustom",
                        Toast.LENGTH_LONG
                    ).show()
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
                            Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT)
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