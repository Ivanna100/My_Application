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
import com.google.android.material.snackbar.Snackbar
import com.ispolska.myApplication.task_2.domain.model.User
import com.ispolska.myApplication.task_2.repository.UserItemClickListener
import com.ispolska.myApplication.task_2.ui.contactAdapter.UserListAdapter
import com.ispolska.myApplication.task_2.ui.fragments.DialogFragment
import com.ispolska.myApplication.task_2.utils.Constants
import com.ispolska.myApplication.task_2.utils.ext.animateVisibility
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), UserItemClickListener {

    private val binding: ActivityContactsBinding by lazy {
        ActivityContactsBinding.inflate(layoutInflater)
    }

    private val adapter: UserListAdapter by lazy {
        UserListAdapter(listener = object : UserItemClickListener {
            override fun onUserDelete(user: User, position: Int) {
                deleteUserWithRestore(user, position)
            }
        })
    }

    private val viewModel : UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setObserver()
        initialRecyclerview()
        showAddContactsDialog()
        setNavigationUpListeners()
    }

    private fun setObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.users.collect {
                    Log.d("log", it.toString())
                    adapter.submitList(it)
                }
            }
        }
    }

    private fun initialRecyclerview() {
        setTouchRecycleItemListener()
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewContacts.layoutManager = layoutManager
        binding.recyclerViewContacts.adapter = adapter
    }

    private fun showAddContactsDialog() {
        binding.textViewAddContacts.setOnClickListener {
            val dialogFragment = DialogFragment()
            dialogFragment.setViewModel(viewModel)
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
        return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteUserWithRestore(
                    viewModel.getUserList()[viewHolder.bindingAdapterPosition],
                    viewHolder.bindingAdapterPosition
                )
            }
        }
    }

    override fun onUserDelete(user: User, position: Int) {
        deleteUserWithRestore(user, position)
    }

    fun deleteUserWithRestore(user: User, position: Int) {
        if (viewModel.deleteUser(user)) {
            Snackbar.make(
                binding.recyclerViewContacts,
                getString(R.string.s_has_been_removed).format(user.name),
                Snackbar.LENGTH_LONG
            )
                .setAction(getString(R.string.restore)) {
                    viewModel.addUser(user, position)
                }
                .show()
        }
    }
}