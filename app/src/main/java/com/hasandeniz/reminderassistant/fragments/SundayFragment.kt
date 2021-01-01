package com.hasandeniz.reminderassistant.fragments

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasandeniz.reminderassistant.*
import com.hasandeniz.reminderassistant.adapters.RecyclerViewAdapter
import com.hasandeniz.reminderassistant.data.Item
import com.hasandeniz.reminderassistant.data.ItemViewModel
import kotlinx.android.synthetic.main.fragment_friday.view.*
import kotlinx.android.synthetic.main.fragment_sunday.view.*
import kotlinx.coroutines.InternalCoroutinesApi


class SundayFragment : Fragment(),RecyclerViewAdapter.ItemListener, RecyclerViewAdapter.OnItemClickListener {
    @InternalCoroutinesApi
    private lateinit var mItemViewModel: ItemViewModel
    @InternalCoroutinesApi
    override fun onCreateView(

            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sunday, container, false)

        val adapter = RecyclerViewAdapter()
        val recyclerView = view.recyclerViewSunday
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.setListener(this)
        adapter.setEditItemClickListener(this)
        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        mItemViewModel.readSundayData.observe(viewLifecycleOwner, Observer { item ->
            adapter.setData(item as ArrayList<Item>)
        })
        mItemViewModel.getIdData.observe(viewLifecycleOwner,{item->
            globalId = if(item.isEmpty()) 1
            else item[0]
        })
        mItemViewModel.readAllData.observe(viewLifecycleOwner,{item->
            globalSize = item.size
        })
        return view
    }
    @InternalCoroutinesApi
    override fun onItemClicked(item: Item, position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        val alarmManager =  requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), item.id , alarmIntent, 0)

        builder.setPositiveButton("Yes"){ _, _ ->
            mItemViewModel.deleteItem(item)
            MyCounterPreferences(requireContext()).globalCounter++
            alarmManager.cancel(pendingIntent)
            Toast.makeText(requireContext(), "Successfully removed",Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){ _, _ ->}
        builder.setTitle("Delete ${item.courseName}?")
        builder.setMessage("Are you sure want to delete ${item.courseName}?")
        builder.create().show()
    }
    override fun onEditItemClicked(item: Item, position: Int) {
        val intent = Intent(requireContext(), AddEventActivity::class.java)
        val id = item.id
        intent.putExtra("isEdit",true)
        intent.putExtra("courseName",item.courseName)
        intent.putExtra("className",item.className)
        intent.putExtra("startTime",item.startTime)
        intent.putExtra("finishTime",item.finishTime)
        intent.putExtra("editPosition",4)
        intent.putExtra("id",id)
        startActivity(intent)
    }


}