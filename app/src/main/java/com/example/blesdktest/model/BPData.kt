package com.example.blesdktest.model

import android.os.Parcelable
import com.veepoo.protocol.model.enums.EBPDetectStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class BPData(
    var status: EBPDetectStatus? = null,
    var progress: Int = 0,
    var highPressure: Int = 0,
    var lowPressure: Int = 0,
    var isHaveProgress: Boolean = false

) : Parcelable
