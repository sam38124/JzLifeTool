package com.example.jzlifetoolsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.jzlifttool.LifeTimer
import com.example.jzlifttool.callback
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    lateinit var timer: LifeTimer
    var i = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timer = LifeTimer(lifecycle)
        timer.schedule(0, 1000 * 1, callback {
            i += 1
            if (i % 10 == 0) {
                timer.stop()
            }
            Log.e("state", "$i")
        })
        findViewById<TextView>(R.id.s).setOnClickListener {
            timer.start()
        }
    }
}
