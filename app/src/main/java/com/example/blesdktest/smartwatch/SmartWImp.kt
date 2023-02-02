package com.example.blesdktest.smartwatch

import android.util.Log
import com.example.blesdktest.TestingFeature
import com.example.blesdktest.data.Oprate.Companion.SPORT_MODE_ORIGIN_READSTAUTS
import com.example.blesdktest.data.Oprate.Companion.SPORT_MODE_START_INDOOR
import com.orhanobut.logger.Logger
import com.veepoo.protocol.VPOperateManager
import com.veepoo.protocol.VPOperateManager.mContext
import com.veepoo.protocol.listener.data.*
import com.veepoo.protocol.model.datas.*
import com.veepoo.protocol.model.enums.*
import com.veepoo.protocol.model.enums.EFunctionStatus.SUPPORT
import com.veepoo.protocol.model.settings.*
import com.veepoo.protocol.shareprence.VpSpGetUtil
import java.util.*


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
        VPOperateManager.getMangerInstance(mContext).startDetectHeart(
            writeResponse
        ) {
            callback.onHeartDataChange(it)
        }
    }

    override fun checkTemp() {
        VPOperateManager.getMangerInstance(mContext).startDetectTempture(
            writeResponse
        ) { temptureDetectData ->
            callback.onTmpDataChange(temptureDetectData)
        }
    }

    override fun stopCheckTemp() {
        VPOperateManager.getMangerInstance(mContext).stopDetectTempture(
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
        VPOperateManager.getMangerInstance(mContext).readSportStep(
            writeResponse
        ) { sportData ->
            callback.onSportDataChange(sportData)
        }
    }

    override fun verification() {
        val is24Hourmodel = false
        VPOperateManager.getMangerInstance(mContext)
            .confirmDevicePwd(writeResponse,
                { pwdData ->
                    callback.onVerificationpwData(pwdData)
                }, { functionSupport ->
                    callback.onVerificationFuntionSupport(functionSupport)
                    watchDataDay = functionSupport.wathcDay
                    weatherStyle = functionSupport.weatherStyle
                    contactMsgLength = functionSupport.contactMsgLength;
                    allMsgLenght = functionSupport.allMsgLength;
                    isSleepPrecision = functionSupport.precisionSleep == SUPPORT;
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
        VPOperateManager.getMangerInstance(mContext).syncPersonInfo(
            writeResponse,
            { EOperateStatus ->
                val message = "Synchronize personal information:\n$EOperateStatus"
                Logger.t(TAG).i(message)
            }, PersonInfoData(ESex.MAN, 178, 60, 20, 8000)
        )
    }

    override fun setWatchTime() {
        val deviceTimeSetting = DeviceTimeSetting(2020, 11, 6, 15, 30, 14, ETimeMode.MODE_12)
        VPOperateManager.getMangerInstance(mContext).settingTime(
            writeResponse,
            { state ->
                val message = "settingTime response :\n$state"
                callback.onResponse(state)
                Log.i(TAG, message)
            }, deviceTimeSetting
        )
    }

    override fun readWeather() {
        VPOperateManager.getMangerInstance(mContext).readWeatherStatusInfo(
            writeResponse
        ) { weatherStatusData ->
            callback.onWeatherDataChange(weatherStatusData)
        }
    }

    override fun uiUpdateAGPS() {
        val bigTranType = VpSpGetUtil.getVpSpVariInstance(mContext).bigTranType
        val isSupportAgps = VpSpGetUtil.getVpSpVariInstance(mContext).isSupoortAGPS
        callback.onUpdateAGPS(bigTranType, isSupportAgps)
    }

    override fun settingWeatherStatusInfo() {
        val weatherStatusSetting = WeatherStatusSetting(0, true, EWeatherType.C)
        VPOperateManager.getMangerInstance(mContext).settingWeatherStatusInfo(
            writeResponse, weatherStatusSetting
        ) { weatherStatusData ->
            callback.onWeatherDataChange(weatherStatusData)
        }
    }

    override fun readLowPower() {
        VPOperateManager.getMangerInstance(mContext).readLowPower(
            writeResponse
        ) { lowPowerData ->
            callback.onLowerPowerDataDataChange(lowPowerData)
        }
    }

    override fun settingLowPower() {
        VPOperateManager.getMangerInstance(mContext).settingLowpower(
            writeResponse,
            { lowPowerData ->
                callback.onLowerPowerDataDataChange(lowPowerData)
            }, true
        )
    }

    override fun closeLowPower() {
        VPOperateManager.getMangerInstance(mContext).settingLowpower(
            writeResponse,
            { lowPowerData ->
                callback.onLowerPowerDataDataChange(lowPowerData)
            }, false
        )
    }

    override fun readBloodPFunction() {
        VPOperateManager.getMangerInstance(mContext).readBpFunctionState(
            writeResponse
        ) { bpFunctionData ->
            callback.onDataChange(bpFunctionData)
        }
    }

    override fun startDetectBP() {
        VPOperateManager.getMangerInstance(mContext).startDetectBP(
            writeResponse,
            { bpData ->
                callback.onDataChange(bpData)
            }, EBPDetectModel.DETECT_MODEL_PUBLIC
        )

    }

    override fun stopDetectBP() {
        VPOperateManager.getMangerInstance(mContext)
            .stopDetectBP(writeResponse, EBPDetectModel.DETECT_MODEL_PUBLIC);
    }

    override fun settingDetectBP() {
        val isOpenPrivateModel = true
        val isAngioAdjuste = false
        val bpSetting = BpSetting(isOpenPrivateModel, 111, 88)
        //Whether to enable the dynamic blood pressure adjustment mode, the function flag is returned in the password verification
        bpSetting.setAngioAdjuste(isAngioAdjuste)
        VPOperateManager.getMangerInstance(mContext).settingDetectBP(
            writeResponse,
            { bpSettingData ->
                callback.onDataChange(bpSettingData)
            }, bpSetting
        )
    }

    override fun stopDetectHR() {
        Logger.t(TAG).i("HEART_DETECT_STOP");
        VPOperateManager.getMangerInstance(mContext).stopDetectHeart(writeResponse);
    }

    override fun startCamera() {
        VPOperateManager.getMangerInstance(mContext).startCamera(
            writeResponse
        ) {
            callback.onCameraDataChange(it)
        }
    }

    override fun stopCamera() {
        VPOperateManager.getMangerInstance(mContext).stopCamera(
            writeResponse
        ) {
            callback.onCameraDataChange(it)
        }
    }

    override fun settingAlarm() {
        val alarmSettingList: MutableList<AlarmSetting> = ArrayList(3)

        val alarmSetting1 = AlarmSetting(13, 53, true)
        val alarmSetting2 = AlarmSetting(13, 54, true)
        val alarmSetting3 = AlarmSetting(13, 55, true)

        alarmSettingList.add(alarmSetting1)
        alarmSettingList.add(alarmSetting2)
        alarmSettingList.add(alarmSetting3)

        VPOperateManager.getMangerInstance(mContext).settingAlarm(
            writeResponse,
            { alarmData ->
                val message = "set an alarm:\n$alarmData"
                callback.onAlarmDataChangeListener(alarmData)
            }, alarmSettingList
        )
    }

    override fun getBatteryLevel() {
        VPOperateManager.getMangerInstance(mContext).readBattery(
            writeResponse
        ) { batteryData ->
            callback.onDataChange(batteryData)
        }
    }

    override fun disconnect() {
        VPOperateManager.getMangerInstance(mContext).disconnectWatch(writeResponse);
    }

    override fun findPhone() {
        VPOperateManager.getMangerInstance(mContext).settingFindPhoneListener {
        }
    }

    override fun settingCheckWear(status: Boolean) {
        val checkWearSetting = CheckWearSetting()
        checkWearSetting.isOpen = status
        VPOperateManager.getMangerInstance(mContext).setttingCheckWear(
            writeResponse,
            { checkWearData ->
                callback.onCheckWearDataChange(checkWearData)
            }, checkWearSetting
        )
    }

    override fun settingFindDevice(status: Boolean) {
        VPOperateManager.getMangerInstance(mContext).settingFindDevice(
            writeResponse,
            { findDeviceData ->
                callback.onFindDevice(findDeviceData)
            }, status
        )
    }

    override fun readFindDevice() {
        VPOperateManager.getMangerInstance(mContext).readFindDevice(
            writeResponse
        ) { findDeviceData ->
            callback.onFindDevice(findDeviceData)
        }
    }

    override fun clearDeviceData() {
        VPOperateManager.getMangerInstance(mContext).clearDeviceData(writeResponse);
    }

    override fun readHeartWarning() {
        VPOperateManager.getMangerInstance(mContext).readHeartWarning(
            writeResponse
        ) { heartWaringData ->
            callback.onHeartWaringDataChange(heartWaringData)
        }
    }

    override fun settingHeartWarning(status: Boolean) {
        VPOperateManager.getMangerInstance(mContext).settingHeartWarning(
            writeResponse,
            { heartWaringData ->
                callback.onHeartWaringDataChange(heartWaringData)
            }, HeartWaringSetting(120, 110, status)
        )
    }

    override fun startDetectSPO2H() {
        VPOperateManager.getMangerInstance(mContext).startDetectSPO2H(writeResponse,
            { spo2HData ->
                callback.onSpO2HADataChange(spo2HData)
            }) { data ->
            callback.onGreenLightDataChange(data)
        }
    }

    override fun stopDetectSPO2H() {

        VPOperateManager.getMangerInstance(mContext).stopDetectSPO2H(
            writeResponse
        ) { spo2HData ->
            callback.onSpO2HADataChange(spo2HData)
        }
    }

    override fun readSpo2hAutoDetect() {
        VPOperateManager.getMangerInstance(mContext).readSpo2hAutoDetect(
            writeResponse
        ) { allSetData ->
            callback.onAllSetDataChangeListener(allSetData)

        }
    }

    override fun settingSpo2hAutoDetect(open: Int) {
        val setting = 0
        val mAlarmSetting =
            AllSetSetting(EAllSetType.SPO2H_NIGHT_AUTO_DETECT, 22, 0, 8, 0, setting, open)
        VPOperateManager.getMangerInstance(mContext).settingSpo2hAutoDetect(
            writeResponse,
            { allSetData ->
                callback.onAllSetDataChangeListener(allSetData)
            }, mAlarmSetting
        )
    }

    override fun startMulSportMode() {
        VPOperateManager.getMangerInstance(mContext)
            .startMultSportModel(writeResponse, object : ISportModelStateListener {
                override fun onSportModelStateChange(sportModelStateData: SportModelStateData) {
                    callback.onSportModelStateChange(sportModelStateData)
                }

                override fun onSportStopped() {
                    callback.onSportStopped()
                    Logger.t(TAG)
                        .i(SPORT_MODE_START_INDOOR + "================================Sport ends")
                }
            }, ESportType.INDOOR_WALK)
    }

    override fun startSportModel() {
        VPOperateManager.getMangerInstance(mContext)
            .startSportModel(writeResponse, object : ISportModelStateListener {
                override fun onSportModelStateChange(sportModelStateData: SportModelStateData) {
                    callback.onSportModelStateChange(sportModelStateData)
                }

                override fun onSportStopped() {
                    callback.onSportStopped()
                }
            })
    }

    override fun readSportModelOrigin() {
        VPOperateManager.getMangerInstance(mContext)
            .readSportModelOrigin(writeResponse, object : ISportModelOriginListener {
                override fun onReadOriginProgress(progress: Float) {
                    val message = "Sports mode data [read progress]:$progress"
                    callback.onReadOriginProgress(progress)
                    Logger.t(TAG).i(message)
                }

                override fun onReadOriginProgressDetail(
                    day: Int,
                    date: String,
                    allPackage: Int,
                    currentPackage: Int
                ) {
                    val message = "Sports mode data [read details]:" + day +
                            ",allPackage=" + allPackage + ",currentPackage=" + currentPackage
                    Logger.t(TAG).i(message)
                    callback.onReadOriginProgressDetail(day, date, allPackage, currentPackage)
                }

                override fun onHeadChangeListListener(sportModelHeadData: SportModelOriginHeadData) {
                    val message = "Motion Mode Data [Header]:$sportModelHeadData"
                    Logger.t(TAG).i(message)
                    callback.onHeadChangeListListener(sportModelHeadData)
                }

                override fun onItemChangeListListener(sportModelItemData: List<SportModelOriginItemData>) {
                    val message = StringBuffer()
                    message.append("Sports mode data [detailed]:")
                    for (sportModelOriginItemData in sportModelItemData) {
                        message.append("\n")
                        message.append(sportModelOriginItemData.toString())
                    }
                    callback.onItemChangeListListener(sportModelItemData)

                    Logger.t(TAG).i(message.toString())
                }

                override fun onReadOriginComplete() {
                    val message = "Sports mode data [end of reading]"
                    Logger.t(TAG).i(message)
                    callback.onReadOriginComplete()
                }
            })
    }

    override fun readSportModelState() {
        VPOperateManager.getMangerInstance(mContext)
            .readSportModelState(writeResponse, object : ISportModelStateListener {
                override fun onSportModelStateChange(sportModelStateData: SportModelStateData) {
                    val message = "sport mode status$sportModelStateData"
                    Logger.t(TAG).i(message)
                    callback.onSportModelStateChange(sportModelStateData)
                }

                override fun onSportStopped() {
                    Logger.t(TAG)
                        .i("$SPORT_MODE_ORIGIN_READSTAUTS================================sports end @_@")
                }
            })
    }

    override fun readSpo2hOrigin() {
        VPOperateManager.getMangerInstance(mContext)
            .readSpo2hOrigin(writeResponse, object : ISpo2hOriginDataListener {
                override fun onReadOriginProgress(progress: Float) {
                    callback.onReadOriginProgress(progress)
                }

                override fun onReadOriginProgressDetail(
                    day: Int,
                    date: String,
                    allPackage: Int,
                    currentPackage: Int
                ) {
                    Logger.t(TAG)
                        .i("onReadOriginProgressDetail:allPackage=$allPackage,currentPackage=$currentPackage")
                    callback.onReadOriginProgressDetail(day, date, allPackage, currentPackage)
                }

                override fun onSpo2hOriginListener(sportOriginData: Spo2hOriginData) {
                    callback.onSpo2hOriginListener(sportOriginData)
                }

                override fun onReadOriginComplete() {
                    Logger.t(TAG).i("onReadOriginComplete")
                }
            }, watchDataDay)
    }

    override fun startDetectFatigue() {
        VPOperateManager.getMangerInstance(mContext).startDetectFatigue(
            writeResponse
        ) { fatigueData ->
            val message = "Fatigue - start:\n$fatigueData"
            Log.d(TAG, "startDetectFatigue: $message")
            callback.onFatigueDataListener(fatigueData)
        }
    }

    override fun stopDetectFatigue() {
        VPOperateManager.getMangerInstance(mContext).stopDetectFatigue(
            writeResponse
        ) { fatigueData ->
            val message = "Fatigue - End:\n$fatigueData"
            Logger.t(TAG).i(message)
            callback.onFatigueDataListener(fatigueData)
        }
    }

    override fun settingWomenState() {
        VPOperateManager.getMangerInstance(mContext).settingWomenState(
            writeResponse,
            { womenData ->
                val message = "Female Status - Settings:\n$womenData"
                Logger.t(TAG).i(message)
                callback.onWomenDataChange(womenData)
            }, WomenSetting(EWomenStatus.PREING, TimeData(2016, 3, 1), TimeData(2017, 1, 14))
        )
    }

    override fun readWomenState() {
        VPOperateManager.getMangerInstance(mContext).readWomenState(
            writeResponse
        ) { womenData ->
            val message = "Female Status - Read:\n$womenData"
            Logger.t(TAG).i(message)
            callback.onWomenDataChange(womenData)
        }
    }

    override fun readChantingData() {
        var timestamp: Long = Calendar.getInstance().getTimeInMillis()
        VPOperateManager.getMangerInstance(mContext).readChantingData(
            writeResponse, ChantingSetting(timestamp)
        ) { chantingData ->
            val message = "read chanting count:$chantingData"
            Logger.t(TAG).i(message)
            callback.onChantingDataChange(chantingData)
        }
    }

    override fun readTemptureDataBySetting() {
        val readOriginSetting = ReadOriginSetting(0, 1, false, watchDataDay)
        VPOperateManager.getMangerInstance(mContext)
            .readTemptureDataBySetting(writeResponse, object : ITemptureDataListener {
                override fun onTemptureDataListDataChange(temptureDataList: List<TemptureData>) {
                    val message = "onTemptureDataListDataChange:" + temptureDataList.size
                    Logger.t(TAG).i(message)
                    callback.onTemptureDataListDataChange(temptureDataList)
                }

                override fun onReadOriginProgressDetail(
                    day: Int,
                    date: String,
                    allPackage: Int,
                    currentPackage: Int
                ) {
                    val message =
                        "Temperature Data - Reading Progress:day=$day,currentPackage=$currentPackage,allPackage=$allPackage"
                    Logger.t(TAG).i(message)
                    callback.onReadOriginProgressDetail(day, date, allPackage, currentPackage)
                }

                override fun onReadOriginProgress(progress: Float) {
                    val message = "onReadOriginProgress:$progress"
                    Logger.t(TAG).i(message)
                    callback.onReadOriginProgress(progress)
                }

                override fun onReadOriginComplete() {
                    val message = "onReadOriginComplete"
                    Logger.t(TAG).i(message)
                    callback.onReadOriginComplete()
                }
            }, readOriginSetting)
    }

    override fun readSleepData() {
        VPOperateManager.getMangerInstance(mContext).readSleepData(
            writeResponse, object : ISleepDataListener {
                override fun onSleepDataChange(day: String, sleepData: SleepData) {
                    var message = ""
                    message = if (sleepData is SleepPrecisionData && isSleepPrecision) {
                        val sleepPrecisionData = sleepData
                        "Accurate Sleep Data-Back:$sleepPrecisionData"
                    } else {
                        "Normal sleep data - return:$sleepData"
                    }
                    Logger.t(TAG).i(message)
                    callback.onSleepDataChange(day, sleepData)
                }

                override fun onSleepProgress(progress: Float) {
                    val message = "sleep data - read progress:progress=$progress"
                    Logger.t(TAG).i(message)
                    callback.onSleepProgress(progress)
                }

                override fun onSleepProgressDetail(day: String, packagenumber: Int) {
                    val message = "sleep data - read progress:day=$day,packagenumber=$packagenumber"
                    Logger.t(TAG).i(message)
                    callback.onSleepProgressDetail(day, packagenumber)
                }

                override fun onReadSleepComplete() {
                    val message = "sleep data - end of read"
                    Logger.t(TAG).i(message)
                    callback.onReadSleepComplete()
                }
            }, watchDataDay
        )
    }

    override fun readSleepDataFromDay() {
        val beforeYesterday = 2
        VPOperateManager.getMangerInstance(mContext)
            .readSleepDataFromDay(writeResponse, object : ISleepDataListener {
                override fun onSleepDataChange(day: String, sleepData: SleepData) {
                    val message: String = getDay(day)
                        .toString() + "- sleep data - return:" + sleepData.toString()
                    callback.onSleepDataChange(day, sleepData)
                    Logger.t(TAG).i(message)
                }

                override fun onSleepProgress(progress: Float) {
                    val message = "sleep data - read progress:progress=$progress"
                    callback.onSleepProgress(progress)
                    Logger.t(TAG).i(message)
                }

                override fun onSleepProgressDetail(day: String, packagenumber: Int) {
                    val message = "sleep data - read progress:day=$day,packagenumber=$packagenumber"
                    callback.onSleepProgressDetail(day, packagenumber)
                    Logger.t(TAG).i(message)
                }

                override fun onReadSleepComplete() {
                    val message = "sleep data - end of read"
                    Logger.t(TAG).i(message)
                }
            }, beforeYesterday, watchDataDay)
    }

    override fun readSleepDataSingleDay() {
        val yesterday = 1
        VPOperateManager.getMangerInstance(mContext)
            .readSleepDataSingleDay(writeResponse, object : ISleepDataListener {
                override fun onSleepDataChange(day: String, sleepData: SleepData) {
                    val message: String = getDay(day)
                        .toString() + "- sleep data - returns:" + sleepData.toString()
                    callback.onSleepDataChange(day, sleepData)
                    Logger.t(TAG).i(message)
                }

                override fun onSleepProgress(progress: Float) {
                    val message = "sleep data - read progress:progress=$progress"
                    callback.onSleepProgress(progress)
                    Logger.t(TAG).i(message)
                }

                override fun onSleepProgressDetail(day: String, packagenumber: Int) {
                    val message = "sleep data - read progress:day=$day,packagenumber=$packagenumber"
                    callback.onSleepProgressDetail(day, packagenumber)
                    Logger.t(TAG).i(message)
                }

                override fun onReadSleepComplete() {
                    val message = "sleep data - end of read"
                    callback.onReadSleepComplete()
                    Logger.t(TAG).i(message)
                }
            }, yesterday, watchDataDay)
    }

    override fun readDrinkData() {
        VPOperateManager.getMangerInstance(mContext)
            .readDrinkData(writeResponse, object : IDrinkDataListener {
                override fun onDrinkDataChange(packagenumber: Int, drinkdata: DrinkData) {
                    val message = "Drinking Data - Back:$drinkdata"
                    Logger.t(TAG).i(message)
                    callback.onDrinkDataChange(packagenumber, drinkdata)
                }

                override fun onReadDrinkComplete() {
                    val message = "Drinking Data - End of Read"
                    callback.onReadDrinkComplete()
                    Logger.t(TAG).i(message)
                }
            })
    }

    override fun readHRVOrigin() {
        Log.d("HRV Origin Data", "dipanggil di SmartWImp")
        VPOperateManager.getMangerInstance(mContext)
            .readHRVOrigin(writeResponse, object : IHRVOriginDataListener {
                override fun onReadOriginProgress(progress: Float) {
                    Log.d("HRV Origin Data", "onReadOriginProgress =$progress")
                }

                override fun onReadOriginProgressDetail(
                    day: Int,
                    date: String,
                    allPackage: Int,
                    currentPackage: Int
                ) {
                    Log.d(
                        "HRV Origin Data",
                        "onReadOriginProgressDetail,day=$day,date=$date,allPackage=$allPackage,currentPackage=$currentPackage"
                    )
                }

                override fun onHRVOriginListener(hrvOriginData: HRVOriginData) {
                    Log.d("HRV Origin Data", "onHRVOriginListener=$hrvOriginData")
                }

                override fun onDayHrvScore(day: Int, date: String, hrvSocre: Int) {
                    Log.d("HRV Origin Data", "dipanggil di dalam VPOPERATOR MANAGERRRR")
                }
                override fun onReadOriginComplete() {
                    Log.d("HRV Origin Data", "onReadOriginComplete")
                }
            }, watchDataDay)
    }


    companion object {
        private val TAG = SmartWImp::class.java.simpleName
        var watchDataDay = 0
        var weatherStyle = 0
        var contactMsgLength = 0
        var allMsgLenght = 4
        var isSleepPrecision = false

        fun getDay(day: String): String? {
            return when (day) {
                "0" -> {
                    "Nowadays"
                }
                "1" -> {
                    "yesterday"
                }
                else -> {
                    "the day before yesterday"
                }
            }
        }
    }

}