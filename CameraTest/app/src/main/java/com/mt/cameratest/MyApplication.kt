package com.mt.cameratest

import android.app.Application
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

class MyApplication : Application() {
    private lateinit var flutterEngine: FlutterEngine
    lateinit var methodChannel: MethodChannel

    companion object {
        const val FLUTTER_ENGINE_ID = "my_engine_id"
        const val METHOD_CHANNEL_NAME = "com.mt.isl/sdk"

        // get application instance
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Instantiate a FlutterEngine.
        flutterEngine = FlutterEngine(this)

        // Start executing Dart code to pre-warm the FlutterEngine.
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())

        // Cache the FlutterEngine to be used by FlutterActivity.
        FlutterEngineCache
            .getInstance()
            .put(FLUTTER_ENGINE_ID, flutterEngine)


        methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, METHOD_CHANNEL_NAME)
    }

}