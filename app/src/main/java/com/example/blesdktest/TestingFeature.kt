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
import com.example.blesdktest.data.Oprate.Companion.SPORT_MODE_ORIGIN_READSTAUTS
import com.example.blesdktest.data.Oprate.Companion.oprateStr
import com.example.blesdktest.model.BPData
import com.example.blesdktest.smartwatch.SmartWImp
import com.example.blesdktest.ui.*
import com.example.blesdktest.viewModel.SmartWViewModel
import com.orhanobut.logger.Logger
import com.veepoo.protocol.VPOperateManager
import com.veepoo.protocol.listener.base.IBleWriteResponse
import com.veepoo.protocol.model.datas.*
import com.veepoo.protocol.model.enums.EBPDetectModel


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
        smartWViewModel.init()
        pwdData = PwdData()
        smartWViewModel.pwdData.observe(this) {
            pwdData.deviceNumber = it.deviceNumber
            pwdData.deviceTestVersion = it.deviceTestVersion
            pwdData.deviceVersion = it.deviceVersion
            pwdData.isHaveDrinkData = it.isHaveDrinkData
            pwdData.findPhoneFunction = it.findPhoneFunction
            pwdData.isOpenNightTurnWriste = it.isOpenNightTurnWriste
            pwdData.wearDetectFunction = it.wearDetectFunction
        }
        smartWViewModel.getBateryLevel()
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
            6 -> {
                Toast.makeText(this@TestingFeature, "Read Chanting Count", Toast.LENGTH_SHORT)
                    .show()
                smartWViewModel.readChantingData()
                smartWViewModel.chantingData.observe(this) {
                    val message = "read chanting count:$it"
                    Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()
                }
            }
            7 -> startActivity(Intent(this@TestingFeature, HeartRateActivity::class.java))
            8 -> {
                smartWViewModel.stopDetectHR()
                Toast.makeText(this@TestingFeature, "Stop Detect Heart Rate", Toast.LENGTH_SHORT)
                    .show()
            }
            9 -> startActivity(Intent(this@TestingFeature, SuhuActivity::class.java))
            10 -> {
                Toast.makeText(this@TestingFeature, "Stop Check Temperatur", Toast.LENGTH_SHORT)
                    .show()
                smartWViewModel.stopCekTemp()
                smartWViewModel.tmpData.observe(this) {
                    Toast.makeText(
                        this@TestingFeature,
                        "Temperature Check Stop",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            11 -> {
                smartWViewModel.readTemptureDataBySetting()
                smartWViewModel.listTemptureData.observe(this) {
                    val message = "onTemptureDataListDataChange:" + it.size
                    Toast.makeText(
                        this@TestingFeature,
                        message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                smartWViewModel.progresSportModelData.observe(this) {
                    val message =
                        "Temperature Data - Reading Progress:day=${it.day},currentPackage=${it.currentPackage},allPackage=${it.allPackage}"
                    Toast.makeText(
                        this@TestingFeature,
                        message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                smartWViewModel.progressSportMode.observe(this) {
                    val message = "onReadOriginProgress:$it"
                    Toast.makeText(
                        this@TestingFeature,
                        message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
            12 -> {
                val bPi = Intent(this@TestingFeature, BloodPressureActivity::class.java)
                var bpData = BPData()
                smartWViewModel.startDetectBP()
                smartWViewModel.bpData.observe(this) {
                    bpData.status = it.status
                    bpData.progress = it.progress
                    bpData.highPressure = it.highPressure
                    bpData.lowPressure = it.lowPressure
                    bpData.isHaveProgress = it.isHaveProgress
                }
                bPi.putExtra(BloodPressureActivity.EXTRA_BP, bpData)
                startActivity(bPi)
            }
            13 -> {
                smartWViewModel.stopDetectBP()
                Toast.makeText(this@TestingFeature, "Stop Detect BP", Toast.LENGTH_SHORT).show()
            }
            14 -> {
                smartWViewModel.settingDetectBP()
                var settingDetectBp = ""
                smartWViewModel.bpSettingData.observe(this) {
                    settingDetectBp = it.toString()
                }
                Toast.makeText(
                    this@TestingFeature,
                    "BPSettingData :${settingDetectBp}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            19 -> {
                smartWViewModel.startCamera()
                var eCameraStatus = ""
                smartWViewModel.eCameraStatus.observe(this) {
                    eCameraStatus = it.toString()
                }

                AlertDialog.Builder(this).apply {
                    setTitle("Start Camera")
                    setMessage(eCameraStatus)
                    create()
                    show()
                }
            }
            20 -> {
                smartWViewModel.stopCamera()
                var eCameraStatus = ""
                smartWViewModel.eCameraStatus.observe(this) {
                    eCameraStatus = it.toString()
                }

                AlertDialog.Builder(this).apply {
                    setTitle("Stop Camera")
                    setMessage(eCameraStatus)
                    create()
                    show()
                }
            }
            21 -> {
                smartWViewModel.settingAlarm()
                smartWViewModel.alarmData.observe(this) {
                    showAlarmDialog("Dalam Observe")
                }
                showAlarmDialog("Luar Observe Set Alarm")
            }
            33 -> {
                var batteryData = BatteryData()
                smartWViewModel.batteryData.observe(this) {
                    batteryData.batteryLevel = it.batteryLevel
                }
                Toast.makeText(
                    this@TestingFeature,
                    "Baterai Tersisa${batteryData.batteryLevel} \n Persentase :${batteryData.batteryLevel * 25}%",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            43 -> {
                smartWViewModel.findPhone()
                Toast.makeText(
                    this@TestingFeature,
                    "(Listen to the bracelet to find the phone)-where is the phone,make some noise!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            44 -> {
                smartWViewModel.checkWearData(true)
                val wearCheckWearData = CheckWearData()
                smartWViewModel.checkWearData.observe(this) {
                    wearCheckWearData.checkWearState = it.checkWearState
                }
                Toast.makeText(
                    this@TestingFeature,
                    "Wear Detection On : $wearCheckWearData",
                    Toast.LENGTH_SHORT
                ).show()
            }
            45 -> {
                smartWViewModel.checkWearData(false)
                val wearCheckWearData = CheckWearData()
                smartWViewModel.checkWearData.observe(this) {
                    wearCheckWearData.checkWearState = it.checkWearState
                }
                Toast.makeText(
                    this@TestingFeature,
                    "Wear Detection off : $wearCheckWearData",
                    Toast.LENGTH_SHORT
                ).show()
            }
            46 -> {
                smartWViewModel.settingFindDevice(true)
                var findDeviceData = FindDeviceData()
                smartWViewModel.findDeviceData.observe(this) {
                    findDeviceData = it
                }
                val message = "Anti lost Open :\n$findDeviceData"
                Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()
            }
            47 -> {
                smartWViewModel.settingFindDevice(false)
                var findDeviceData = FindDeviceData()
                smartWViewModel.findDeviceData.observe(this) {
                    findDeviceData = it
                }
                val message = "Anti lost off :\n$findDeviceData"
                Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()
            }
            48 -> {
                smartWViewModel.readFindDevice()
                var findDeviceData = FindDeviceData()
                smartWViewModel.findDeviceData.observe(this) {
                    findDeviceData = it
                }
                val message = "Loss prevention Reading :\n$findDeviceData"
                Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()
            }
            55 -> {
                smartWViewModel.readHeartWarning()
                var heartWarningData = HeartWaringData()
                smartWViewModel.heartWaringData.observe(this) {
                    heartWarningData = it
                }
                val message = "Heart Rate Alarm - Reading:\n$heartWarningData"
                Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()
            }

            56 -> {
                smartWViewModel.settingHeartWarning(true)
                var heartWarningData = HeartWaringData()
                smartWViewModel.heartWaringData.observe(this) {
                    heartWarningData = it
                }
                val message = "Heart Rate Alarm-On :\n$heartWarningData"
                Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()
            }
            57 -> {
                smartWViewModel.settingHeartWarning(false)
                var heartWarningData = HeartWaringData()
                smartWViewModel.heartWaringData.observe(this) {
                    heartWarningData = it
                }
                val message = "Heart Rate Alarm-off :\n$heartWarningData"
                Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()
            }
            58 -> {
                var spo2hData = Spo2hData()
                var data = intArrayOf()
                smartWViewModel.startDetectSPO2H()
                smartWViewModel.data.observe(this) {
                    data = it
                }
                smartWViewModel.spo2hData.observe(this) {
                    spo2hData = it
                }

                val message = "Blood Oxygen - Start:\n$spo2hData"
                val message2 = """
                 Blood oxygen-optical signal:
                 ${data.contentToString()}
                 """.trimIndent()
                Toast.makeText(this@TestingFeature, "$message \n $message2", Toast.LENGTH_SHORT)
                    .show()

            }
            59 -> {
                smartWViewModel.stopDetectSPO2H()
                var spo2hData = Spo2hData()
                smartWViewModel.spo2hData.observe(this) {
                    spo2hData = it
                    val message = "Blood Oxygen - End:\n$spo2hData"
                    Toast.makeText(this@TestingFeature, "$message \n $message", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            60 -> {
                smartWViewModel.readSpo2hAutoDetect()
                smartWViewModel.allSetData.observe(this) {
                    val allSetData = it
                    val message = "Blood oxygen automatic detection - reading\n$allSetData"
                    Toast.makeText(this@TestingFeature, "$message \n $message", Toast.LENGTH_SHORT)
                        .show()
                }

            }
            61 -> {
                smartWViewModel.settingSpo2hAutoDetect(1)
                smartWViewModel.allSetData.observe(this) {
                    val allSetData = it
                    val message = "Blood oxygen auto-detection-open\n$allSetData"
                    Toast.makeText(this@TestingFeature, "$message \n $message", Toast.LENGTH_SHORT)
                        .show()
                }

            }
            62 -> {
                smartWViewModel.settingSpo2hAutoDetect(0)
                smartWViewModel.allSetData.observe(this) {
                    val allSetData = it
                    val message = "Blood oxygen auto-detection-close\n$allSetData"
                    Toast.makeText(this@TestingFeature, "$message \n $message", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            63 -> {
                Toast.makeText(this@TestingFeature, "Detect Fatiigue Start", Toast.LENGTH_SHORT)
                    .show()
                smartWViewModel.startDetectFatigue()
                smartWViewModel.fatigueData.observe(this) {
                    val message = "Fatigue - start:\n$it"
                    Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()
                }
            }
            64 -> {
                Toast.makeText(this@TestingFeature, "Detect Fatigue Stop", Toast.LENGTH_SHORT)
                    .show()
                smartWViewModel.stopDetectFatigue()
                smartWViewModel.fatigueData.observe(this) {
                    val message = "Fatigue - Stop:\n$it"
                    Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()
                }
            }
            65 -> {
                smartWViewModel.settingWomenState()
                smartWViewModel.womenData.observe(this) {
                    val message = """
                        Female Status - Settings:
                        $it
                        """.trimIndent()
                    Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()
                }
            }
            66 -> {
                smartWViewModel.readWomenState()
                smartWViewModel.womenData.observe(this) {
                    val message = """
                    Female Status - Read:
                    $it
                    """.trimIndent()
                    Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()

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
            92 -> {
                smartWViewModel.readSpo2hOrigin()
                smartWViewModel.progresSportModelData.observe(this) {
                    val message =
                        "onReadOriginProgressDetail:allPackage=${it.allPackage},currentPackage=${it.currentPackage}"
                    Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()
                    val msg2 = "onReadOriginComplete"
                    Toast.makeText(this@TestingFeature, msg2, Toast.LENGTH_SHORT).show()
                }

            }
            94 -> {
                AlertDialog.Builder(this).apply {
                    setMessage("Hapus Data")
                    setMessage("Semua data pada smartwatch akan dihapus,lanjutkan?")
                    setPositiveButton("Ya") { _, _ ->
                        smartWViewModel.clearDeviceData()
                    }
                    setNegativeButton("Batal") { dialog, _ ->
                        dialog.dismiss()
                    }
                    create()
                    show()
                }
            }
            95 -> {
                smartWViewModel.disconnect()
                Toast.makeText(this@TestingFeature, "Sambungan terputus", Toast.LENGTH_SHORT).show()
                finish();
            }
            96 -> {
                startActivity(Intent(this@TestingFeature, EcgDetectActivity::class.java))
            }
            97 -> {
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

            106 -> {
                var isInPttModel = false
                smartWViewModel.isInPttModel.observe(this) {
                    isInPttModel = it
                }
                val intent = Intent(this@TestingFeature, PttActivity::class.java)
                intent.putExtra("inPttModel", isInPttModel)
                startActivity(intent)
            }

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
            76 -> {
                var day = "0"
                var sleepData = SleepData()
                var progress = 0f
                var packageNumber = 0
                smartWViewModel.readSleepData()
                smartWViewModel.day.observe(this) {
                    day = it
                }
                smartWViewModel.sleepData.observe(this) {
                    sleepData = it
                }
                smartWViewModel.sleepProgress.observe(this) {
                    progress = it
                }
                smartWViewModel.packageNumber.observe(this) {
                    packageNumber = it
                }

                var message = ""
                message = if (sleepData is SleepPrecisionData && SmartWImp.isSleepPrecision) {
                    val sleepPrecisionData = sleepData as SleepPrecisionData
                    "Accurate Sleep Data-Back:$sleepPrecisionData"
                } else {
                    "Normal sleep data - return:$sleepData"
                }
                Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()

                val message2 =
                    "sleep data - read progress :progress=$progress"
                Toast.makeText(this@TestingFeature, message2, Toast.LENGTH_SHORT).show()

                val message3 =
                    "sleep data - read progress:day=$day,packagenumber=$packageNumber"
                Toast.makeText(this@TestingFeature, message3, Toast.LENGTH_SHORT).show()
                val message4 = "sleep data - end of read"
                Toast.makeText(this@TestingFeature, message4, Toast.LENGTH_SHORT).show()

            }
            77 -> {
                var day = "0"
                var sleepData = SleepData()
                var progress = 0f
                var packageNumber = 0
                smartWViewModel.readSleepDataFromDay()
                smartWViewModel.day.observe(this) {
                    day = it
                }
                smartWViewModel.sleepData.observe(this) {
                    sleepData = it
                }
                smartWViewModel.sleepProgress.observe(this) {
                    progress = it
                }
                smartWViewModel.packageNumber.observe(this) {
                    packageNumber = it
                }

                val message: String = SmartWImp.getDay(day)
                    .toString() + "- sleep data - return:" + sleepData.toString()

                Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()

                val message2 =
                    "sleep data - read progress :progress=$progress"
                Toast.makeText(this@TestingFeature, message2, Toast.LENGTH_SHORT).show()

                val message3 =
                    "sleep data - read progress:day=$day,packagenumber=$packageNumber"
                Toast.makeText(this@TestingFeature, message3, Toast.LENGTH_SHORT).show()
                val message4 = "sleep data - end of read"
                Toast.makeText(this@TestingFeature, message4, Toast.LENGTH_SHORT).show()
            }
            78 -> {
                var day = "0"
                var sleepData = SleepData()
                var progress = 0f
                var packageNumber = 0
                smartWViewModel.readSleepDataSingleDay()
                smartWViewModel.day.observe(this) {
                    day = it
                }
                smartWViewModel.sleepData.observe(this) {
                    sleepData = it
                }
                smartWViewModel.sleepProgress.observe(this) {
                    progress = it
                }
                smartWViewModel.packageNumber.observe(this) {
                    packageNumber = it
                }

                val message: String = SmartWImp.getDay(day)
                    .toString() + "- sleep data - return:" + sleepData.toString()

                Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()

                val message2 =
                    "sleep data - read progress :progress=$progress"
                Toast.makeText(this@TestingFeature, message2, Toast.LENGTH_SHORT).show()

                val message3 =
                    "sleep data - read progress:day=$day,packagenumber=$packageNumber"
                Toast.makeText(this@TestingFeature, message3, Toast.LENGTH_SHORT).show()
                val message4 = "sleep data - end of read"
                Toast.makeText(this@TestingFeature, message4, Toast.LENGTH_SHORT).show()
            }
            79 -> {
                smartWViewModel.readDrinkData()
                smartWViewModel.drinkData.observe(this) {
                    val message = "Drinking Data - Back:$it"
                    Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()
                }
                val message = "Drinking Data - End of Read"
                Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()
            }
            87 -> {
                smartWViewModel.readSportModelOrigin()
                smartWViewModel.progressSportMode.observe(this) {
                    val message = "Sports mode data [read progress]:$it"
                    Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()
                }
                smartWViewModel.progresSportModelData.observe(this) {
                    val message = "Sports mode data [read details]:" + it.day +
                            ",allPackage=" + it.allPackage + ",currentPackage=" + it.currentPackage
                    Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()
                }
                smartWViewModel.sportModelHeadData.observe(this) {
                    val message = "Motion Mode Data [Header]:$it"
                    Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()
                }
                smartWViewModel.sportModelItemData.observe(this) {
                    val message = StringBuffer()
                    message.append("Sports mode data [detailed]:")
                    for (sportModelOriginItemData in it) {
                        message.append("\n")
                        message.append(sportModelOriginItemData.toString())
                    }
                    Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()
                }
            }
            88 -> {
                smartWViewModel.readSportModelState()
                smartWViewModel.sportModelStateData.observe(this) {
                    val message = "sport mode status$it"
                    Toast.makeText(this@TestingFeature, message, Toast.LENGTH_SHORT).show()
                    val msg2 =
                        "$SPORT_MODE_ORIGIN_READSTAUTS================================sports end @_@";
                    Toast.makeText(this@TestingFeature, msg2, Toast.LENGTH_SHORT).show()
                }


            }
            89 -> {
                smartWViewModel.startMulSportModel()
                smartWViewModel.sportModelStateData.observe(this) {
                    Toast.makeText(
                        this@TestingFeature,
                        "Data Sport model :${it.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            90 -> {
                smartWViewModel.startSportModel()
                smartWViewModel.sportModelStateData.observe(this) {
                    Toast.makeText(
                        this@TestingFeature,
                        "Sport mode status :${it}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            91 -> {}
        }
    }

    private fun showAlarmDialog(m: String) {
        Toast.makeText(this@TestingFeature, m, Toast.LENGTH_SHORT).show()
    }


    //mulai


    companion object {
        private val TAG = TestingFeature::class.java.simpleName

    }
}