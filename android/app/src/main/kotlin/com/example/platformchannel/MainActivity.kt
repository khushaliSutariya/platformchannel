package com.example.platformchannel

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity: FlutterActivity() {
    private val eventChannel1 = "com.example.two_eventchannels/events1"

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        EventChannel(flutterEngine.dartExecutor.binaryMessenger, eventChannel1).setStreamHandler(
            TimeHandler
        )
    }
}

object TimeHandler : EventChannel.StreamHandler {
    private var handler = Handler(Looper.getMainLooper())
    private var eventSink: EventChannel.EventSink? = null

    @SuppressLint("SimpleDateFormat")
    override fun onListen(p0: Any?, sink: EventChannel.EventSink) {
        eventSink = sink
        val r: Runnable = object : Runnable {
            override fun run() {
                handler.post {
                    val dateFormat = SimpleDateFormat("HH:mm:ss")
                    val time = dateFormat.format(Date())
                    eventSink?.success(time)
                }
                handler.postDelayed(this, 1000)
            }
        }
        handler.postDelayed(r, 1000)
    }

    override fun onCancel(p0: Any?) {
        eventSink = null
    }
}


