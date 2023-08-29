package com.ispolska.myApplication.task_2.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task_2.R
import com.example.task_2.databinding.ActivityContactsBinding
import com.ispolska.myApplication.task_2.data.model.User
import com.ispolska.myApplication.task_2.ui.activities.interfaces.UserItemClickListener
import com.ispolska.myApplication.task_2.ui.contactAdapter.RecyclerViewAdapter
import com.ispolska.myApplication.task_2.ui.fragments.DialogFragment
import com.ispolska.myApplication.task_2.utils.Constants
import com.ispolska.myApplication.task_2.utils.ext.animateVisibility
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val binding: ActivityContactsBinding by lazy {
        ActivityContactsBinding.inflate(layoutInflater)
    }

    private val adapter: RecyclerViewAdapter by lazy {
        RecyclerViewAdapter(listener = object : UserItemClickListener {
            override fun onUserDelete(user: User, position: Int) {
                deleteUserWithRestore(user, position)
            }
        })
    }

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // adapter = RecyclerViewAdapter()
        setObserver()
        initialRecyclerview()
        showAddContactsDialog()
        setNavigationUpListeners()
    }

    private fun setObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.users.collect {
                    Log.d("log observer", it.toString())
                    adapter.submitList(it)
                }
            }
        }
    }

    private fun initialRecyclerview() {
        setTouchRecycleItemListener()
        // viewModel = ViewModelProvider(this)[UserViewModel::class.java] // TODO: why not by viewModels?
        val layoutManager = LinearLayoutManager(this)
        // adapter.setUserItemClickListener(this)
        binding.recyclerViewContacts.layoutManager = layoutManager
        binding.recyclerViewContacts.adapter = adapter
        // adapter.updateUsers(viewModel.getUserList()) // TODO: this can do diff util (with list adapter)
    }

    private fun showAddContactsDialog() {
        binding.textViewAddContacts.setOnClickListener {
            val dialogFragment = DialogFragment()
            dialogFragment.setViewModel(viewModel)
            dialogFragment.setAdapter(adapter)
            dialogFragment.show(supportFragmentManager, Constants.DIALOG_TAG)
        }
    }

    private fun setNavigationUpListeners() {
        binding.imageViewNavigationUp.viewTreeObserver.addOnScrollChangedListener {
            checkForDisplayUpNavigationButton()
        }
        binding.imageViewNavigationUp.setOnClickListener {
            binding.recyclerViewContacts.smoothScrollToPosition(0)
        }
    }

    private fun checkForDisplayUpNavigationButton() {
        val visibleItemCount = binding.recyclerViewContacts.childCount
        val layoutManager = binding.recyclerViewContacts.layoutManager as LinearLayoutManager
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        binding.imageViewNavigationUp.animateVisibility(
            if (lastVisibleItemPosition >= visibleItemCount) View.VISIBLE else View.GONE
        )
    }

    private fun setTouchRecycleItemListener() {
        val itemTouchCallback = setTouchCallBackListener()
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.recyclerViewContacts)
    }

    private fun setTouchCallBackListener(): ItemTouchHelper.Callback {
        return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteUserWithRestore(
                    viewModel.getUserList()[viewHolder.bindingAdapterPosition], // TODO: deprecated
                    viewHolder.adapterPosition // TODO: deprecated
                )
            }
        }
    }

//    override fun onUserDelete(user: User, position: Int) { // TODO: can do via anonymous object
//        deleteUserWithRestore(user, position)
//    }

    fun deleteUserWithRestore(user: User, position: Int) {
        if (viewModel.deleteUser(user)) {
//            adapter.notifyItemRemoved(position)
//            adapter.updateUsers(viewModel.getUserList())
            Snackbar.make(
                binding.recyclerViewContacts,
                getString(R.string.s_has_been_removed).format(user.name),
                Snackbar.LENGTH_LONG
            )
                .setAction(getString(R.string.restore)) {
                    viewModel.addUser(user, position)
//                    {
//                        adapter.notifyItemInserted(position)
//                        adapter.updateUsers(viewModel.getUserList())
//                    }
                }.show()
        }
    }
}