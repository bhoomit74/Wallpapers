package com.bhoomit.wallpapers.dashboard.view

import android.app.WallpaperManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.bhoomit.wallpapers.R
import com.bhoomit.wallpapers.dashboard.data.model.ImageDetail
import com.bhoomit.wallpapers.dashboard.viewmodel.DashboardViewModel
import com.bhoomit.wallpapers.databinding.FragmentDashboardBinding
import com.bhoomit.wallpapers.util.CheckConnection
import com.bhoomit.wallpapers.util.CheckInternetBySocketConnection
import com.bhoomit.wallpapers.util.Extensions.setImage
import com.bumptech.glide.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception
import java.net.URL

class DashboardFragment : Fragment() {


    private lateinit var mBinding: FragmentDashboardBinding
    private lateinit var mDashboardViewModel: DashboardViewModel
    private lateinit var mDashboardAdapter: DashboardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        initData()
        initObserver()
        return mBinding.root
    }


    private fun initData() {
        mDashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        mBinding.viewModel = mDashboardViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        val temp = arrayListOf<ImageDetail>()
        mDashboardViewModel.imageList.value?.forEach {
            temp.add(it)
        }
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
                        Log.d("RECYCLERVIEW","VIC : $visibleItemCount")
                        Log.d("RECYCLERVIEW","TIC : $totalItemCount")
                        Log.d("RECYCLERVIEW","FV : $firstVisibleItemPosition")
                        mDashboardViewModel.increasePageAndGetData()
                    }
                }
            })
        }

    }

    private fun initObserver() {
        mDashboardViewModel.updateList.observe(viewLifecycleOwner, Observer {
            if (it!=null) {
                Log.d("RECYCLERVIEW", "InitList : " + it.size.toString())
                mDashboardAdapter.updateList(it)
            }
        })

        mDashboardViewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
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

@BindingAdapter("imageUrl")
fun load(imageView: ImageView, imageUrl: String) {
    imageView.context.setImage(imageView, imageUrl)
}