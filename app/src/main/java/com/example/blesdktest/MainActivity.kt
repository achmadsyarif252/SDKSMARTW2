package com.example.blesdktest

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.blesdktest.adapter.ListDeviceAdapter
import com.example.blesdktest.databinding.ActivityMainBinding
import com.inuker.bluetooth.library.Code
import com.inuker.bluetooth.library.Constants
import com.inuker.bluetooth.library.search.SearchResult
import com.inuker.bluetooth.library.utils.BluetoothUtils
import com.veepoo.protocol.VPOperateManager
import com.veepoo.protocol.listener.base.IABleConnectStatusListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //deklarasi dan inisialisasi variabel
    private val CODE_REQUEST_ACCESS_COARSE_LOCATION = 101
    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var bluetoothManager: BluetoothManager
    val listResult = ArrayList<ScanResult>()
    private var REQUEST_CODE = 1
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout


    val deviceList = ArrayList<SearchResult>()
    private var mIsOadModel = false
    private var isStartConnecting = false

    //sdk dari ble
    var mVpoperateManager: VPOperateManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Demo Smartwatch SDK"
        mSwipeRefreshLayout = binding.refreshRv

        val context: Context = this
        mVpoperateManager = VPOperateManager.getMangerInstance(context)

        bluetoothManager = context.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        mSwipeRefreshLayout.setOnRefreshListener(OnRefreshListener {
            if (checkBLE()) scanDevice()
        })

        val layoutManager = LinearLayoutManager(this)
        binding.rvBluetooth.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvBluetooth.addItemDecoration(itemDecoration)

        checkPermission()
        scanDevice()
    }

    private fun checkPermission() {
        //Check whether you have fuzzy positioning permission
        checkBLE()
        val permissionList = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT
        )

        val permissionRequest = arrayListOf<String>()

        permissionList.forEach {
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    it
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionRequest.add(it)
            }
        }

        if (permissionRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                permissionRequest.toTypedArray(),
                CODE_REQUEST_ACCESS_COARSE_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun scanDevice() {
        val scanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                super.onScanResult(callbackType, result)
                val result = SearchResult(result.device, result.rssi, result.scanRecord?.bytes)
                if (result !in deviceList) {
                    deviceList.add(result)
                    Log.d(TAG, "onScanResult: ${result.device.address}")

                    if (result.device.name != null) {
                        bluetoothAdapter.bluetoothLeScanner.stopScan(this)
                        if (mSwipeRefreshLayout.isRefreshing) mSwipeRefreshLayout.isRefreshing =
                            false
                    }
                }
            }
        }
        initRv()
        bluetoothAdapter.bluetoothLeScanner.startScan(scanCallback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            if (BluetoothUtils.isBluetoothEnabled()) {
                scanDevice()
            } else {
                stopRefresh()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun stopRefresh() {
        Log.d("MainAct", "RefreshComplete ")
        if (mSwipeRefreshLayout.isRefreshing) mSwipeRefreshLayout.isRefreshing = false
    }

    @SuppressLint("MissingPermission")
    private fun checkBLE(): Boolean {
        if (!BluetoothUtils.isBluetoothEnabled()) {
            val i = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(i, REQUEST_CODE)
            return false
        }
        return true
    }

    private fun initRv() {
        val adapter = ListDeviceAdapter(deviceList)
        adapter.setOnItemClickCallback(object : ListDeviceAdapter.OnItemClickCallback {
            override fun onItemClicked(searchResult: SearchResult) {
                Toast.makeText(this@MainActivity, "Connecting,please wait..", Toast.LENGTH_SHORT)
                    .show()
//                connectDevice(searchResult.address, searchResult.name);
            connectDevice("C3:30:04:34:05:F5","E400")
            }


        })
        binding.rvBluetooth.adapter = adapter
    }

    private val mBleConnectStatusListener: IABleConnectStatusListener =
        object : IABleConnectStatusListener() {
            override fun onConnectStatusChanged(mac: String, status: Int) {
                if (status == Constants.STATUS_CONNECTED) {
                    Log.i(TAG, "STATUS_CONNECTED")
                } else if (status == Constants.STATUS_DISCONNECTED) {
                    Log.i(TAG, "STATUS_DISCONNECTED")
                }
            }
        }

    //connect to selected device
    private fun connectDevice(mac: String, deviceName: String) {
        mVpoperateManager?.registerConnectStatusListener(mac, mBleConnectStatusListener)
        mVpoperateManager?.connectDevice(mac, deviceName,
            { code, _, isoadModel ->
                if (code == Code.REQUEST_SUCCESS) {
                    //Bluetooth connection status with the device
                    Log.i(TAG, "connection succeeded")
                    Toast.makeText(this@MainActivity, "connection succeeded", Toast.LENGTH_SHORT)
                        .show()
                    Log.i(TAG, "Whether it is firmware upgrade mode=$isoadModel")
                    mIsOadModel = isoadModel
                    isStartConnecting = true
                } else {
                    Toast.makeText(this@MainActivity, "Connection failed", Toast.LENGTH_SHORT)
                        .show()
                    Log.i(TAG, "")
                    isStartConnecting = false
                }
            }) { state ->
            if (state == Code.REQUEST_SUCCESS) {
                //Bluetooth connection status with the device
                Log.i(TAG, "The monitoring is successful - other operations can be performed")
                isStartConnecting = true

                val intent = Intent(VPOperateManager.mContext, TestingFeature::class.java)
                intent.putExtra("isoadmodel", mIsOadModel)
                intent.putExtra("deviceaddress", mac)
                startActivity(intent)
                //                    }
            } else {
                Log.i(TAG, "Listening failed, reconnect")
                Toast.makeText(
                    this@MainActivity,
                    "Listening failed, reconnect",
                    Toast.LENGTH_SHORT
                )
                    .show()
                isStartConnecting = false
            }
        }
    }


    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

}