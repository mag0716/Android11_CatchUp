package com.github.mag0716.controlexternaldevicessample

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.service.controls.Control
import android.service.controls.ControlsProviderService
import android.service.controls.DeviceTypes
import android.service.controls.actions.BooleanAction
import android.service.controls.actions.ControlAction
import android.service.controls.templates.ControlButton
import android.service.controls.templates.ToggleTemplate
import android.util.Log
import com.github.mag0716.controlexternaldevicessample.model.Device
import com.github.mag0716.controlexternaldevicessample.repository.DeviceRepository
import io.reactivex.Flowable
import io.reactivex.processors.ReplayProcessor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import org.reactivestreams.FlowAdapters
import java.util.concurrent.Flow
import java.util.function.Consumer
import kotlin.coroutines.CoroutineContext

class SampleControlsProviderService : ControlsProviderService(), CoroutineScope {

    companion object {
        const val TAG = "SampleControlsProvideService"

        const val CONTROL_REQUEST_CODE = 0
        const val CONTROL_ID = "Control"
        const val TEMPLATE_ID = "template"
    }

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    private lateinit var deviceRepository: DeviceRepository
    private lateinit var updatePublisher: ReplayProcessor<Control>

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "SampleControlsProviderService#onCreate")
        job = Job()
        deviceRepository = (application as App).deviceRepository
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun createPublisherForAllAvailable(): Flow.Publisher<Control> {
        Log.d(TAG, "SampleControlsProviderService#createPublisherForAllAvailable")
        val context = baseContext

        val controls = mutableListOf<Control>()
        val deviceList = runBlocking {
            deviceRepository.loadDevices()
        }
        for (device in deviceList) {
            controls.add(createStatelessControl(context, device))
        }

        return FlowAdapters.toFlowPublisher(Flowable.fromIterable(controls))
    }

    override fun createPublisherFor(controlIds: MutableList<String>): Flow.Publisher<Control> {
        Log.d(TAG, "SampleControlsProviderService#createPublisherFor : $controlIds")
        val context = baseContext
        updatePublisher = ReplayProcessor.create()

        for (controlId in controlIds) {
            val deviceId = controlId.replace(CONTROL_ID, "").toInt()
            val device = runBlocking {
                deviceRepository.loadDevice(deviceId)
            }
            if (device != null) {
                // TODO: 外部デバイスの状態を取得
                updatePublisher.onNext(createControl(context, device, true))
            }
        }

        return FlowAdapters.toFlowPublisher(Flowable.fromPublisher(updatePublisher))
    }

    override fun performControlAction(
        controlId: String,
        action: ControlAction,
        consumer: Consumer<Int>
    ) {
        Log.d(TAG, "SampleControlsProviderService#performControlAction : $controlId, $action")
        val context = baseContext

        if (action is BooleanAction) {
            val deviceId = controlId.replace(CONTROL_ID, "").toInt()
            val device = runBlocking {
                deviceRepository.loadDevice(deviceId)
            }
            if (device != null) {
                consumer.accept(ControlAction.RESPONSE_OK)
                if (updateDeviceState(action.newState)) {
                    updatePublisher.onNext(createControl(context, device, action.newState))
                }
            }
        }
    }

    private fun createStatelessControl(context: Context, device: Device): Control {
        return Control.StatelessBuilder("$CONTROL_ID${device.id}", createPendingIntent(context))
            .setTitle(device.name)
            .setSubtitle(device.placeLocation)
            .setDeviceType(DeviceTypes.TYPE_LIGHT)
            .build()
    }

    private fun createControl(context: Context, device: Device, isChecked: Boolean): Control {
        return Control.StatefulBuilder("$CONTROL_ID${device.id}", createPendingIntent(context))
            .setTitle(device.name)
            .setSubtitle(device.placeLocation)
            .setDeviceType(DeviceTypes.TYPE_LIGHT)
            .setControlTemplate(
                ToggleTemplate(
                    "$TEMPLATE_ID${device.id}",
                    ControlButton(isChecked, "turn ON/OFF")
                )
            )
            // TODO: 外部デバイスの状態を取得する
            .setStatus(Control.STATUS_OK)
            .build()
    }

    private fun createPendingIntent(context: Context): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        return PendingIntent.getActivity(
            context,
            CONTROL_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun updateDeviceState(isOn: Boolean): Boolean {
        // TODO: 外部デバイスに対しての更新処理
        return true
    }
}