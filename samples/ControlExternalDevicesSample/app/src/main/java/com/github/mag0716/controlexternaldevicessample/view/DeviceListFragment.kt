package com.github.mag0716.controlexternaldevicessample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mag0716.controlexternaldevicessample.R
import com.github.mag0716.controlexternaldevicessample.model.Device
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DeviceListFragment : Fragment(), DeviceAdapter.ClickListener {

    val viewModel by viewModels<DeviceListViewModel>()

    private lateinit var deviceList: RecyclerView
    private lateinit var addButton: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_device_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deviceList = view.findViewById(R.id.list)
        addButton = view.findViewById(R.id.addButton)

        deviceList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        addButton.setOnClickListener {
            findNavController().navigate(R.id.actionMoveToDeviceSetting)
        }

        viewModel.deviceList.observe(viewLifecycleOwner, Observer {
            deviceList.adapter =
                DeviceAdapter(
                    it
                ).apply {
                    clickListener = this@DeviceListFragment
                }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadDeviceList()
    }

    override fun onClick(device: Device) {
        findNavController().navigate(DeviceListFragmentDirections.actionMoveToDeviceSetting(device.id))
    }
}

class DeviceAdapter(private val deviceList: List<Device>) :
    RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    interface ClickListener {
        fun onClick(device: Device)
    }

    var clickListener: ClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_device, parent, false)
        return DeviceViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = deviceList[position]
        holder.itemView.setOnClickListener {
            clickListener?.onClick(device)
        }
        holder.deviceNameText.text = device.name
        holder.deviceLocationText.text = device.placeLocation
    }

    class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceNameText: TextView = itemView.findViewById<TextView>(R.id.deviceNameText)
        val deviceLocationText: TextView = itemView.findViewById<TextView>(R.id.deviceLocationText)
    }
}