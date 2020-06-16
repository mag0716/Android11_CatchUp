package com.github.mag0716.controlexternaldevicessample

import android.app.PendingIntent
import android.content.Context
import android.service.controls.Control
import android.service.controls.ControlsProviderService
import android.service.controls.DeviceTypes
import android.service.controls.actions.BooleanAction
import android.service.controls.actions.ControlAction
import android.service.controls.templates.ControlButton
import android.service.controls.templates.ToggleTemplate
import android.util.Log
import androidx.navigation.NavDeepLinkBuilder
import com.github.mag0716.controlexternaldevicessample.model.Device
import com.github.mag0716.controlexternaldevicessample.repository.DeviceRepository
import com.github.mag0716.controlexternaldevicessample.view.DeviceSettingFragmentArgs
import io.reactivex.Flowable
import io.reactivex.processors.ReplayProcessor
import kotlinx.coroutines.*
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
                launch {
                    val isConnected = async {
                        deviceRepository.isConnected(deviceId)
                    }
                    val isOn = async {
                        deviceRepository.getStatus(deviceId)
                    }
                    updatePublisher.onNext(
                        createControl(
                            context,
                            device,
                            isConnected.await(),
                            isOn.await()
                        )
                    )
                }
            } else {
                // 途中でデバイスを削除したケース
                updatePublisher.onNext(
                    createControl(
                        context,
                        Device.createNotFoundDevice(deviceId),
                        isEnabled = false,
                        isChecked = false
                    )
                )
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
                launch {
                    deviceRepository.updateStatus(deviceId, action.newState)
                    val isConnected = async {
                        deviceRepository.isConnected(deviceId)
                    }
                    updatePublisher.onNext(
                        createControl(
                            context,
                            device,
                            isConnected.await(),
                            action.newState
                        )
                    )
                }
            }
        }
    }

    private fun createStatelessControl(context: Context, device: Device): Control {
        return Control.StatelessBuilder(
            "$CONTROL_ID${device.id}",
            createPendingIntent(context, device.id)
        )
            .setTitle(device.name)
            .setSubtitle(device.placeLocation)
            .setDeviceType(DeviceTypes.TYPE_LIGHT)
            .build()
    }

    private fun createControl(
        context: Context,
        device: Device,
        isEnabled: Boolean,
        isChecked: Boolean
    ): Control {
        return Control.StatefulBuilder(
            "$CONTROL_ID${device.id}",
            createPendingIntent(context, device.id)
        )
            .setTitle(device.name)
            .setSubtitle(device.placeLocation)
            .setDeviceType(DeviceTypes.TYPE_LIGHT)
            .setControlTemplate(
                ToggleTemplate(
                    "$TEMPLATE_ID${device.id}",
                    ControlButton(isChecked, "turn ON/OFF")
                )
            )
            .setStatus(if (isEnabled) Control.STATUS_OK else Control.STATUS_ERROR)
            .build()
    }

    private fun createPendingIntent(context: Context, deviceId: Int): PendingIntent {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        return NavDeepLinkBuilder(context)
            .setGraph(R.navigation.navigation_graph)
            .setDestination(R.id.deviceSettingFragment)
            .setArguments(DeviceSettingFragmentArgs(deviceId).toBundle())
            .createPendingIntent()
    }
}