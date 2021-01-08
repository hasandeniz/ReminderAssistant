package com.hasandeniz.reminderassistant.fragments

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasandeniz.reminderassistant.*
import com.hasandeniz.reminderassistant.activities.AddEventActivity
import com.hasandeniz.reminderassistant.adapters.RecyclerViewAdapter
import com.hasandeniz.reminderassistant.data.Item
import com.hasandeniz.reminderassistant.data.ItemViewModel
import com.hasandeniz.reminderassistant.databinding.FragmentMondayBinding
import com.hasandeniz.reminderassistant.notify.AlarmReceiver
import kotlinx.coroutines.InternalCoroutinesApi


class MondayFragment : Fragment(),RecyclerViewAdapter.DeleteItemListener, RecyclerViewAdapter.OnItemClickListener {
    @InternalCoroutinesApi
    private lateinit var mItemViewModel: ItemViewModel
    private var _binding: FragmentMondayBinding? = null
    private val binding get() = _binding!!
    @InternalCoroutinesApi
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMondayBinding.inflate(inflater, container, false)
        val view = binding.root

        val adapter = RecyclerViewAdapter()
        val recyclerView = binding.recyclerViewMonday
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.setListener(this)
        adapter.setEditItemClickListener(this)
        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        mItemViewModel.readMondayData.observe(viewLifecycleOwner, { item ->
            adapter.setData(item as ArrayList<Item>)
            if(item.isNotEmpty())
                binding.animationView.visibility = View.INVISIBLE
            else
                binding.animationView.visibility = View.VISIBLE

        })

        return view
    }
    @InternalCoroutinesApi
    override fun onItemClicked(item: Item, position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        val alarmManager =  requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), item.id , alarmIntent, 0)

        builder.setPositiveButton(getString(R.string.yes)){ _, _ ->
            mItemViewModel.deleteItem(item)
            alarmManager.cancel(pendingIntent)
        }
        builder.setNegativeButton(getString(R.string.no)){ _, _ ->}
        builder.setTitle(getString(R.string.delete)+ " " +item.courseName + "?")
        builder.setMessage(getString(R.string.are_you_sure))
        builder.create().apply {
            setOnShowListener {
                getButton(Dialog.BUTTON_NEGATIVE)?.setTextColor(ContextCompat.getColor(context,R.color.colorAlertText))
                getButton(Dialog.BUTTON_POSITIVE)?.setTextColor(ContextCompat.getColor(context,R.color.colorAlertText))
            }
        }.show()
    }
    override fun onEditItemClicked(item: Item, position: Int) {
        val intent = Intent(requireContext(), AddEventActivity::class.java)
        val id = item.id
        intent.putExtra("isEdit",true)
        intent.putExtra("courseName",item.courseName)
        intent.putExtra("className",item.className)
        intent.putExtra("startTime",item.startTime)
        intent.putExtra("finishTime",item.finishTime)
        intent.putExtra("editPosition",0)
        intent.putExtra("id",id)
        intent.putExtra("date",item.day)
        startActivity(intent)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}