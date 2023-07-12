package com.xdf.plugin.bitmap

data class LargeBitmapConfig(private val monitorImageViewClass: String = "com.xdf.monitor.bitmap.MonitorImageView") {

    val formatMonitorImageViewClass: String
        get() = monitorImageViewClass.replace(".", "/")
}
