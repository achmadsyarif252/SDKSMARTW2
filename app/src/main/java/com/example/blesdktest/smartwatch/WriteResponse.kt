package com.example.blesdktest.smartwatch

import android.util.Log
import com.veepoo.protocol.listener.base.IBleWriteResponse

class WriteResponse() : IBleWriteResponse {
    override fun onResponse(code: Int) {
        Log.i(TAG, "write cmd status:$code")
    }

    companion object {
        private val TAG = WriteResponse::class.java.simpleName
    }
}
