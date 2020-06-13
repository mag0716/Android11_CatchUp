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
import io.reactivex.Flowable
import io.reactivex.processors.ReplayProcessor
import org.reactivestreams.FlowAdapters
import java.util.concurrent.Flow
import java.util.function.Consumer

class SampleControlsProviderService : ControlsProviderService() {

    companion object {
        const val TAG = "SampleControlsProvideService"

        const val CONTROL_REQUEST_CODE = 0
        const val CONTROL_ID = "Control ID"
        const val TEMPLATE_ID = "template ID"
    }

    private lateinit var updatePublisher: ReplayProcessor<Control>

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "SampleControlsProviderService#onCreate")
    }

    override fun createPublisherForAllAvailable(): Flow.Publisher<Control> {
        Log.d(TAG, "SampleControlsProviderService#createPublisherForAllAvailable")
        val context = baseContext
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

    override fun createPublisherFor(controlIds: MutableList<String>): Flow.Publisher<Control> {
        Log.d(TAG, "SampleControlsProviderService#createPublisherFor : $controlIds")
        val context = baseContext
        updatePublisher = ReplayProcessor.create()

        if (controlIds.contains(CONTROL_ID)) {
            val control = Control.StatefulBuilder(CONTROL_ID, createPendingIntent(context))
                .setTitle("title")
                .setSubtitle("subtitle")
                .setStructure("structure")
                .setDeviceType(DeviceTypes.TYPE_LIGHT)
                .setControlTemplate(
                    ToggleTemplate(
                        TEMPLATE_ID,
                        ControlButton(true, "turn ON/OFF")
                    )
                )
                .setStatus(Control.STATUS_OK) // TODO: 外部デバイスの状態を取得
                .build()

            updatePublisher.onNext(control)
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
            consumer.accept(ControlAction.RESPONSE_OK)

            if (updateDeviceState(action.newState)) {
                val control = Control.StatefulBuilder(CONTROL_ID, createPendingIntent(context))
                    .setTitle("title")
                    .setSubtitle("subtitle")
                    .setStructure("structure")
                    .setDeviceType(DeviceTypes.TYPE_LIGHT)
                    .setControlTemplate(
                        ToggleTemplate(
                            TEMPLATE_ID,
                            ControlButton(action.newState, "turn ON/OFF")
                        )
                    )
                    .setStatus(Control.STATUS_OK)
                    .build()

                updatePublisher.onNext(control)
            }
        }
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