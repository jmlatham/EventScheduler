package com.jmlatham.eventscheduler.ui.groups

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jmlatham.eventscheduler.R
import com.jmlatham.eventscheduler.databinding.FragmentGroupsBinding

class GroupsFragment : Fragment() {

    private lateinit var groupsViewModel: GroupsViewModel
    private var _binding: FragmentGroupsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        groupsViewModel =
            ViewModelProvider(this).get(GroupsViewModel::class.java)

        _binding = FragmentGroupsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGroups
        groupsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}