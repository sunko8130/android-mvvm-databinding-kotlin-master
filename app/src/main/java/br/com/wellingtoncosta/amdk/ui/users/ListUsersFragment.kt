package br.com.wellingtoncosta.amdk.ui.users

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.wellingtoncosta.amdk.BuildConfig
import br.com.wellingtoncosta.amdk.R
import br.com.wellingtoncosta.amdk.data.remote.response.Status
import br.com.wellingtoncosta.amdk.databinding.FragmentListUsersBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * @author Wellington Costa on 31/12/2017.
 */
class ListUsersFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ListUsersViewModel

    private lateinit var binding: FragmentListUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListUsersViewModel::class.java)

        observeLoadingStatus()
        observeResponse()

        viewModel.loadData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_users, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.swipeContainer.setOnRefreshListener(viewModel::loadData)
        return binding.root
    }

    private fun observeResponse() {
        viewModel.loadingStatus.observe(
                this,
                Observer { isLoading -> binding.swipeContainer.isRefreshing = isLoading ?: false })
    }

    private fun observeLoadingStatus() {
        viewModel.response.observe(
                this,
                Observer { response ->
                    if(response != null && response.status == Status.SUCCESS) {
                        binding.users = response.data
                        binding.executePendingBindings()
                        Snackbar.make(binding.root, "Hello", Snackbar.LENGTH_LONG).show()
                    } else {
                        if ((response != null && response.status == Status.ERROR) && BuildConfig.DEBUG) {
                            Log.e("get users error", response.error.toString())
                        }
                        Snackbar.make(binding.root, R.string.load_data_error, Snackbar.LENGTH_LONG).show()
                    }
                }
        )
    }

}