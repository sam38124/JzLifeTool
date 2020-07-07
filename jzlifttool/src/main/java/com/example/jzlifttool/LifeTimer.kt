package com.example.jzlifttool

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.ON_ANY
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import java.lang.Exception
import java.util.*
import kotlin.concurrent.schedule

class LifeTimer(var life: Lifecycle) {
     var timer: Timer = Timer()
     var delay=0L
     var period=0L
     var callback:callback? =null
     var start=true
    init {
        life.addObserver(TestObserver())
    }
   fun schedule(delay:Long,period:Long, callback: callback){
       this.callback=callback
       this.delay=delay
       this.period=period
       start()
   }
    fun stop(){
        start=false
    }
    fun start(){
        start=true
        excute()
    }
    fun excute(){
        try {
            timer.cancel()
        }catch (e:Exception){
        }
        if(callback==null){return}
        timer=Timer()
        timer.schedule(delay,period){
            if(callback==null||!start){
                timer.cancel()
            }else{callback!!.callback()}
        }
    }

   open inner class TestObserver : LifecycleObserver {

        @OnLifecycleEvent(ON_ANY)
        fun onAny(source: LifecycleOwner?, event: Lifecycle.Event?) {
            Log.e("state",event.toString())
            if(event.toString()=="ON_RESUME"){
                excute()
            }else if(event.toString()=="ON_STOP"){
                try {
                    timer.cancel()
                }catch (e:Exception){
                }
            }
        }
    }

}