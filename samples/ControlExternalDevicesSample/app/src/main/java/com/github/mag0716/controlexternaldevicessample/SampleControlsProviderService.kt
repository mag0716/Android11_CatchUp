package com.github.mag0716.controlexternaldevicessample

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.service.controls.Control
import android.service.controls.ControlsProviderService
import android.service.controls.DeviceTypes
import android.service.controls.actions.ControlAction
import android.util.Log
import io.reactivex.Flowable
import org.reactivestreams.FlowAdapters
import java.util.concurrent.Flow
import java.util.function.Consumer

class SampleControlsProviderService : ControlsProviderService() {

    companion object {
        const val TAG = "SampleControlsProvideService"
        const val CONTROL_REQUEST_CODE = 0
        const val CONTROL_ID = "Control ID"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "SampleControlsProviderService#onCreate")
    }

    override fun createPublisherForAllAvailable(): Flow.Publisher<Control> {
        Log.d(TAG, "SampleControlsProviderService#createPublisherForAllAvailable")
        val context: Context = baseContext
        val intent = Intent()
        val pendingIntent = PendingIntent.getActivity(
            context,
            CONTROL_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val controls = mutableListOf<Control>()
        val control = Control.StatelessBuilder(CONTROL_ID, pendingIntent)
            .setTitle("title")
            .setSubtitle("subtitle")
            .setStructure("structure")
            .setDeviceType(DeviceTypes.TYPE_LIGHT)
            .build()
        controls.add(control)

        return FlowAdapters.toFlowPublisher(Flowable.fromIterable(controls))
    }

    override fun createPublisherFor(p0: MutableList<String>): Flow.Publisher<Control> {
        Log.d(TAG, "SampleControlsProviderService#createPublisherFor")
        TODO("Not yet implemented")
    }

    override fun performControlAction(p0: String, p1: ControlAction, p2: Consumer<Int>) {
        Log.d(TAG, "SampleControlsProviderService#performControlAction")
        TODO("Not yet implemented")
    }
}