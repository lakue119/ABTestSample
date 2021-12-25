package com.lakue.abtestsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val btnSnack = findViewById<TextView>(R.id.btnSnack)
                    val btnFloating = findViewById<FloatingActionButton>(R.id.btnFloating)

                    val data = remoteConfig.getString("button_style")
                    when(data){
                        "floating_button" -> {
                            btnSnack.visibility = View.GONE
                            btnFloating.visibility = View.VISIBLE
                        }
                        "snack_button" -> {
                            btnSnack.visibility = View.VISIBLE
                            btnFloating.visibility = View.GONE
                        }
                    }
                } else {
                    Log.d(TAG, "Fail")
                }
            }
    }
}