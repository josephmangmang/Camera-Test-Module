package com.mt.cameratest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.MethodChannel

class MainActivity : AppCompatActivity() {

    private lateinit var methodChannel: MethodChannel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        methodChannel = MyApplication.instance.methodChannel

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.openCameraWithNewEngine).setOnClickListener {
            methodChannel.invokeMethod("openCamera", null)
            startActivity(
                FlutterActivity
                    .withNewEngine()
                    .build(this)
            )
        }
        findViewById<Button>(R.id.openCameraWithCachedEngine).setOnClickListener {
            methodChannel.invokeMethod("openCamera", null)
            startActivity(
                FlutterActivity
                    .withCachedEngine(MyApplication.FLUTTER_ENGINE_ID)
                    .build(this)
            )
        }
        findViewById<Button>(R.id.openFlutterPage).setOnClickListener {
            startActivity(
                FlutterActivity
                    .withCachedEngine(MyApplication.FLUTTER_ENGINE_ID)
                    .build(this)
            )
        }
        setMethodChannelHandler()
    }

    private fun setMethodChannelHandler() {
        methodChannel.setMethodCallHandler { call, result ->
            Log.d(MainActivity::class.java.simpleName, "Method call: ${call.method}")
            result.success(true)
        }
    }
}