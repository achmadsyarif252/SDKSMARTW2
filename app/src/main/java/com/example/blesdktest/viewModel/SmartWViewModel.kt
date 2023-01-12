package com.example.blesdktest.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blesdktest.model.ProgresSportModelData
import com.example.blesdktest.smartwatch.IBleSdkCallback
import com.example.blesdktest.smartwatch.SmartWImp
import com.veepoo.protocol.model.datas.*
import com.veepoo.protocol.model.enums.ECameraStatus

class SmartWViewModel : ViewModel() {
    private var _sportData = MutableLiveData<SportData>()
    val sportData: LiveData<SportData> = _sportData

    private var _heartRate = MutableLiveData<HeartData>()
    val heartRate: LiveData<HeartData> = _heartRate

    private var _tempData = MutableLiveData<Float>()
    val tmpData: LiveData<Float> = _tempData

    private var _pwData = MutableLiveData<PwdData>()
    val pwdData: LiveData<PwdData> = _pwData

    private var _functionSupportData = MutableLiveData<FunctionDeviceSupportData>()
    val functioinSupportData: LiveData<FunctionDeviceSupportData> = _functionSupportData

    private var _onResponseState = MutableLiveData<Int>()
    val onResponseState: LiveData<Int> = _onResponseState

    private var _msgWeatherData = MutableLiveData<String>()
    val msgWeatherData: LiveData<String> = _msgWeatherData

    //uiAGPS
    private var _bigTranType = MutableLiveData<Int>()
    val bigTranType: LiveData<Int> = _bigTranType

    private var _isSupportAgps = MutableLiveData<Boolean>()
    val isSupportAgps: LiveData<Boolean> = _isSupportAgps

    private var _msgWeatherDataSetting = MutableLiveData<String>()
    val msgWeatherDataSetting: LiveData<String> = _msgWeatherDataSetting

    private var _lowPowerData = MutableLiveData<LowPowerData>()
    val lowPowerData: LiveData<LowPowerData> = _lowPowerData

    private var _BPFunctionData = MutableLiveData<BpFunctionData>()
    val BpFunctionData: LiveData<BpFunctionData> = _BPFunctionData

    private var _isInPttModel = MutableLiveData<Boolean>()
    val isInPttModel: LiveData<Boolean> = _isInPttModel

    private var _bPData = MutableLiveData<BpData>()
    val bpData: LiveData<BpData> = _bPData

    private var _bpSettingData = MutableLiveData<BpSettingData>()
    val bpSettingData: LiveData<BpSettingData> = _bpSettingData

    private var _eCameraStatus = MutableLiveData<ECameraStatus>()
    val eCameraStatus: LiveData<ECameraStatus> = _eCameraStatus

    private var _alarmData = MutableLiveData<AlarmData>()
    val alarmData: LiveData<AlarmData> = _alarmData

    private var _batteryData = MutableLiveData<BatteryData>()
    val batteryData: LiveData<BatteryData> = _batteryData

    private var _checkWearData = MutableLiveData<CheckWearData>()
    val checkWearData: LiveData<CheckWearData> = _checkWearData

    private var _findDeviceData = MutableLiveData<FindDeviceData>()
    val findDeviceData: LiveData<FindDeviceData> = _findDeviceData

    private var _heartWaringData = MutableLiveData<HeartWaringData>()
    val heartWaringData: LiveData<HeartWaringData> = _heartWaringData

    //oksigen darah / spo2hData
    private var _spo2hData = MutableLiveData<Spo2hData>()
    val spo2hData: LiveData<Spo2hData> = _spo2hData

    private var _data = MutableLiveData<IntArray>()
    val data: LiveData<IntArray> = _data

    private var _allSetData = MutableLiveData<AllSetData>()
    val allSetData: LiveData<AllSetData> = _allSetData

    private var _sportModelStateData = MutableLiveData<SportModelStateData>()
    val sportModelStateData: LiveData<SportModelStateData> = _sportModelStateData

    private var _progressSportMode = MutableLiveData<Float>()
    val progressSportMode: MutableLiveData<Float> = _progressSportMode

    private var _progressSportmodelData = MutableLiveData<ProgresSportModelData>()
    val progresSportModelData: MutableLiveData<ProgresSportModelData> = _progressSportmodelData

    private var _sportModelHeadData = MutableLiveData<SportModelOriginHeadData>()
    val sportModelHeadData: LiveData<SportModelOriginHeadData> = _sportModelHeadData

    private var _sportModelItemData = MutableLiveData<List<SportModelOriginItemData>>()
    val sportModelItemData: LiveData<List<SportModelOriginItemData>> = _sportModelItemData

    private var _spo2hOriginData = MutableLiveData<Spo2hOriginData>()
    val spo2hOriginData: LiveData<Spo2hOriginData> = _spo2hOriginData

    private var _fatigueData = MutableLiveData<FatigueData>()
    val fatigueData: LiveData<FatigueData> = _fatigueData

    private var _womenData = MutableLiveData<WomenData>()
    val womenData: LiveData<WomenData> = _womenData

    private var _chantingData = MutableLiveData<ChantingData>()
    val chantingData: LiveData<ChantingData> = _chantingData

    private var _listTemptureData = MutableLiveData<List<TemptureData>>()
    val listTemptureData: LiveData<List<TemptureData>> = _listTemptureData

    //sleep health
    private var _sleepProgress = MutableLiveData<Float>()
    val sleepProgress: LiveData<Float> = _sleepProgress

    private var _day = MutableLiveData<String>()
    val day: LiveData<String> = _day

    private var _sleepData = MutableLiveData<SleepData>()
    val sleepData: LiveData<SleepData> = _sleepData

    private var _packageNumber = MutableLiveData<Int>()
    val packageNumber: LiveData<Int> = _packageNumber

    private var _packageNumberDrink = MutableLiveData<Int>()
    val packageNumberDrink: LiveData<Int> = _packageNumberDrink

    private var _drinkData = MutableLiveData<DrinkData>()
    val drinkData: LiveData<DrinkData> = _drinkData


    fun cekTmp() {
        swi.checkTemp()
    }

    fun stopCekTemp() {
        swi.stopCheckTemp()
    }

    fun checkHr() {
        swi.checkHeartRate()
    }

    fun cekStep() {
        swi.readStep()
    }

    fun init() {
        swi.verification()
        swi.syncProfile()
    }


    fun setWatchTime() {
        swi.setWatchTime()
    }

    fun readWeather() {
        swi.readWeather()
    }

    fun onUpdateUIAGPS() {
        swi.uiUpdateAGPS()
    }

    fun settingWeatherData() {
        swi.settingWeatherStatusInfo()
    }

    fun readLowPowerData() {
        swi.readLowPower()
    }

    fun settingLowPower() {
        swi.settingLowPower()
    }

    fun closeLowPower() {
        swi.closeLowPower()
    }

    fun bpFunctionData() {
        swi.readBloodPFunction()
    }

    fun startDetectBP() {
        swi.startDetectBP()
    }

    fun stopDetectBP() {
        swi.stopDetectBP()
    }

    fun settingDetectBP() {
        swi.settingDetectBP()
    }

    fun stopDetectHR() {
        swi.stopDetectHR()
    }

    fun startCamera() {
        swi.startCamera()
    }

    fun stopCamera() {
        swi.stopCamera()
    }

    fun settingAlarm() {
        swi.settingAlarm()
    }

    fun getBateryLevel() {
        swi.getBatteryLevel()
    }

    fun disconnect() {
        swi.disconnect()
    }

    fun clearDeviceData() {
        swi.clearDeviceData()
    }

    fun findPhone() {
        swi.findPhone()
    }

    fun checkWearData(status: Boolean) {
        swi.settingCheckWear(status)
    }

    fun settingFindDevice(status: Boolean) {
        swi.settingFindDevice(status)
    }

    fun readFindDevice() {
        swi.readFindDevice()
    }

    fun readHeartWarning() {
        swi.readHeartWarning()
    }

    fun settingHeartWarning(status: Boolean) {
        swi.settingHeartWarning(status)
    }

    fun startDetectSPO2H() {
        swi.startDetectSPO2H()
    }

    fun stopDetectSPO2H() {
        swi.stopDetectSPO2H()
    }

    fun readSpo2hAutoDetect() {
        swi.readSpo2hAutoDetect()
    }

    fun settingSpo2hAutoDetect(open: Int) {
        swi.settingSpo2hAutoDetect(open)
    }

    fun startMulSportModel() {
        swi.startMulSportMode()
    }

    fun startSportModel() {
        swi.startSportModel()
    }

    fun readSportModelOrigin() {
        swi.readSportModelOrigin()
    }

    fun readSportModelState() {
        swi.readSportModelState()
    }

    fun readSpo2hOrigin() {
        swi.readSpo2hOrigin()
    }

    fun startDetectFatigue() {
        swi.startDetectFatigue()
    }

    fun stopDetectFatigue() {
        swi.stopDetectFatigue()
    }

    fun settingWomenState() {
        swi.settingWomenState()
    }

    fun readWomenState() {
        swi.readWomenState()
    }

    fun readChantingData() {
        swi.readChantingData()
    }

    fun readTemptureDataBySetting() {
        swi.readTemptureDataBySetting()
    }

    fun readSleepData() {
        swi.readSleepData()
    }

    fun readSleepDataFromDay() {
        swi.readSleepDataFromDay()
    }

    fun readSleepDataSingleDay() {
        swi.readSleepDataSingleDay()
    }

    fun readDrinkData() {
        swi.readDrinkData()
    }

    private val swi = SmartWImp(object : IBleSdkCallback {
        override fun onHeartDataChange(heartData: HeartData) {
            _heartRate.postValue(heartData)
        }

        override fun onSportDataChange(sportData: SportData) {
            _sportData.postValue(sportData)
        }

        override fun onResponse(state: Int) {
            _onResponseState.postValue(state)
        }

        override fun onWeatherDataChange(weatherStatusData: WeatherStatusData) {
            _msgWeatherData.postValue(
                "readWeatherStatusInfo onWeatherDataChange read:\n$weatherStatusData"
            )

            _msgWeatherDataSetting.postValue(
                "settingWeatherStatusInfo onWeatherDataChange read:\n$weatherStatusData"
            )
        }

        override fun onUpdateAGPS(bigTranType: Int, isSupportAgps: Boolean) {
            _bigTranType.postValue(bigTranType)
            _isSupportAgps.postValue(isSupportAgps)
        }

        override fun onLowerPowerDataDataChange(lowPowerData: LowPowerData) {
            _lowPowerData.postValue(lowPowerData)
        }

        override fun onDataChange(bpFunctionData: BpFunctionData) {
            _BPFunctionData.postValue(bpFunctionData)
        }

        override fun onDataChange(bpData: BpData) {
            _bPData.postValue(bpData)
        }

        override fun onDataChange(bpDataSettingData: BpSettingData) {
            _bpSettingData.postValue(bpDataSettingData)
        }

        override fun onDataChange(batteryData: BatteryData) {
            _batteryData.postValue(batteryData)
        }

        override fun inPttModel() {
            _isInPttModel.postValue(true)
        }

        override fun outPttModel() {
            _isInPttModel.postValue(false)
        }

        override fun rejectPhone() {
            TODO("Not yet implemented")
        }

        override fun cliencePhone() {
            TODO("Not yet implemented")
        }

        override fun knocktify() {
            TODO("Not yet implemented")
        }

        override fun sos() {
            TODO("Not yet implemented")
        }

        override fun nextMusic() {
            TODO("Not yet implemented")
        }

        override fun prevMusic() {
            TODO("Not yet implemented")
        }

        override fun pauseAndPlayMusic() {
            TODO("Not yet implemented")
        }

        override fun pauseMusic() {
            TODO("Not yet implemented")
        }

        override fun playMusic() {
            TODO("Not yet implemented")
        }

        override fun voiceUp() {
            TODO("Not yet implemented")
        }

        override fun voiceDown() {
            TODO("Not yet implemented")
        }

        override fun operateMusicSuccess() {
            TODO("Not yet implemented")
        }

        override fun oprateMusicFail() {
            TODO("Not yet implemented")
        }


        override fun onCameraDataChange(oprateStatus: ECameraStatus) {
            _eCameraStatus.postValue(oprateStatus)
        }

        override fun onAlarmDataChangeListener(alarmData: AlarmData) {
            _alarmData.postValue(alarmData)
        }

        override fun onCheckWearDataChange(checkWearData: CheckWearData) {
            _checkWearData.postValue(checkWearData)
        }

        override fun onFindDevice(findDeviceData: FindDeviceData) {
            _findDeviceData.postValue(findDeviceData)
        }

        override fun onHeartWaringDataChange(heartWaringData: HeartWaringData) {
            _heartWaringData.postValue(heartWaringData)
        }

        override fun onSpO2HADataChange(spo2hData: Spo2hData) {
            _spo2hData.postValue(spo2hData)
        }

        override fun onGreenLightDataChange(data: IntArray) {
            _data.postValue(data)
        }

        override fun onAllSetDataChangeListener(allSetData: AllSetData) {
            _allSetData.postValue(allSetData)
        }

        override fun onSportModelStateChange(sportModelStateData: SportModelStateData) {
            _sportModelStateData.postValue(sportModelStateData)
        }

        override fun onSportStopped() {
            TODO("Not yet implemented")

        }

        override fun onReadOriginProgress(progress: Float) {
            _progressSportMode.postValue(progress)
        }

        override fun onReadOriginProgressDetail(
            day: Int,
            date: String,
            allPackage: Int,
            currentPackage: Int
        ) {
            _progressSportmodelData.postValue(
                ProgresSportModelData(
                    day,
                    date,
                    allPackage,
                    currentPackage
                )
            )


        }

        override fun onHeadChangeListListener(sportModelHeadData: SportModelOriginHeadData) {
            _sportModelHeadData.postValue(sportModelHeadData)

        }

        override fun onItemChangeListListener(sportModelItemData: List<SportModelOriginItemData>) {
            _sportModelItemData.postValue(sportModelItemData)
        }

        override fun onReadOriginComplete() {
            TODO("Not yet implemented")
        }

        override fun onSpo2hOriginListener(sportOriginData: Spo2hOriginData) {
            _spo2hOriginData.postValue(sportOriginData)
        }

        override fun onFatigueDataListener(fatigueData: FatigueData) {
            _fatigueData.postValue(fatigueData)
        }

        override fun onWomenDataChange(womenData: WomenData) {
            _womenData.postValue(womenData)
        }

        override fun onChantingDataChange(chantingData: ChantingData) {
            _chantingData.postValue(chantingData)
        }

        override fun onTemptureDataListDataChange(temptureDataList: List<TemptureData>) {
            _listTemptureData.postValue(temptureDataList)
        }

        override fun onSleepDataChange(day: String, sleepData: SleepData) {
            _day.postValue(day)
            _sleepData.postValue(sleepData)
        }

        override fun onSleepProgress(progress: Float) {
            _sleepProgress.postValue(progress)
        }

        override fun onSleepProgressDetail(day: String, packagenumber: Int) {
            _day.postValue(day)
            _packageNumber.postValue(packagenumber)
        }

        override fun onReadSleepComplete() {
            TODO("Not yet implemented")
        }

        override fun onDrinkDataChange(packagenumber: Int, drinkdata: DrinkData) {
            _packageNumberDrink.postValue(packagenumber)
            _drinkData.postValue(drinkdata)
        }

        override fun onReadDrinkComplete() {
            TODO("Not yet implemented")
        }


        override fun onTmpDataChange(tmpDetectData: TemptureDetectData) {
            _tempData.postValue(tmpDetectData.tempture)
        }

        override fun onVerificationpwData(pwdData: PwdData) {
            _pwData.postValue(pwdData)
        }

        override fun onVerificationFuntionSupport(functionDeviceSupportData: FunctionDeviceSupportData) {
            _functionSupportData.postValue(functionDeviceSupportData)
        }

        override fun onSyncProfile() {

        }

    })


}