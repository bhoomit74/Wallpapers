package com.bhoomit.wallpapers.dashboard.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.bhoomit.wallpapers.R
import com.bhoomit.wallpapers.dashboard.data.model.ImageDetail
import com.bhoomit.wallpapers.dashboard.viewmodel.DashboardViewModel
import com.bhoomit.wallpapers.databinding.FragmentDashboardBinding
import com.bhoomit.wallpapers.util.Extensions.showErrorToast

class DashboardFragment : Fragment() {

    private lateinit var mBinding: FragmentDashboardBinding
    private lateinit var mDashboardViewModel: DashboardViewModel
    private lateinit var mDashboardAdapter: DashboardAdapter
    private val temp = arrayListOf<ImageDetail>() // remove this variable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        initData()
        initObserver()
        return mBinding.root
    }


    private fun initData() {
        mDashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        mBinding.viewModel = mDashboardViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        temp.clear()
        mDashboardViewModel.allImages.value?.forEach {
            temp.add(it)
        }  // remove this temp variable
        mDashboardAdapter = DashboardAdapter(temp, listener())

        mBinding.apply {
            dashboardRecyclerView.adapter = mDashboardAdapter
            dashboardRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            dashboardRecyclerView.addOnScrollListener(object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition > 0
                    ) {
                        mDashboardViewModel.incrementPageAndGetImages()
                    }
                }
            })
        }
    }

    private fun initObserver() {
        mDashboardViewModel.updateList.observe(viewLifecycleOwner, Observer {
            if (it!=null) {
                mDashboardAdapter.updateList(it)
            }
        })

        mDashboardViewModel.error.observe(viewLifecycleOwner, Observer {
            requireContext().showErrorToast(it)
        })

    }


    private fun listener(): OnClickListener {
        return object : OnClickListener {
            override fun onClick(url: String) {
                val bundle = Bundle()
                bundle.putString("URL",url)
                findNavController().navigate(R.id.action_dashboardFragment_to_fullScreenImageFragment,bundle)
                mDashboardViewModel.removeUpdateList()
            }

        }
    }
}