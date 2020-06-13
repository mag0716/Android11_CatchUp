package com.github.mag0716.controlexternaldevicessample

import android.service.controls.Control
import android.service.controls.ControlsProviderService
import android.service.controls.actions.ControlAction
import java.util.concurrent.Flow
import java.util.function.Consumer

class SampleControlsProviderService : ControlsProviderService() {

    override fun createPublisherForAllAvailable(): Flow.Publisher<Control> {
        TODO("Not yet implemented")
    }

    override fun performControlAction(p0: String, p1: ControlAction, p2: Consumer<Int>) {
        TODO("Not yet implemented")
    }

    override fun createPublisherFor(p0: MutableList<String>): Flow.Publisher<Control> {
        TODO("Not yet implemented")
    }
}