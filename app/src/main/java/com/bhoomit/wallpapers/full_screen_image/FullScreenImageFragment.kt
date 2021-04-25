package com.bhoomit.wallpapers.full_screen_image

import android.app.WallpaperManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bhoomit.wallpapers.R
import com.bhoomit.wallpapers.databinding.FragmentFullScreenImageBinding
import com.bhoomit.wallpapers.util.Extensions.showErrorToast

class FullScreenImageFragment : Fragment() {

    private lateinit var mBinding : FragmentFullScreenImageBinding
    private lateinit var mFullScreenImageViewModel : FullScreenImageViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentFullScreenImageBinding.inflate(inflater,container,false)
        initData()
        initObserver()
        return mBinding.root
    }

    private fun initData(){
        mFullScreenImageViewModel = ViewModelProvider(this).get(FullScreenImageViewModel::class.java)
        mFullScreenImageViewModel.setImage(arguments?.getString("URL"))
        mBinding.viewModel = mFullScreenImageViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.executePendingBindings()
    }

    private fun initObserver(){
        mFullScreenImageViewModel.setWallpaper.observe(viewLifecycleOwner, Observer {
            if (it!=null){
               val bitmap = mBinding.wallpaper.drawable.toBitmap()
                WallpaperManager.getInstance(requireContext()).setBitmap(bitmap)
                requireContext().showErrorToast(resources.getString(R.string.wallpaper_set_successfully_message))
            }
        })

        mFullScreenImageViewModel.error.observe(viewLifecycleOwner, Observer {
            requireContext().showErrorToast(it)
        })
    }
}