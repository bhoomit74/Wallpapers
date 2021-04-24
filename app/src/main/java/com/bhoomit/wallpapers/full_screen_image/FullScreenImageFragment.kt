package com.bhoomit.wallpapers.full_screen_image

import android.app.WallpaperManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bhoomit.wallpapers.R
import com.bhoomit.wallpapers.databinding.FragmentFullScreenImageBinding
import com.bhoomit.wallpapers.util.Extensions.setImage

class FullScreenImageFragment : Fragment() {

    private lateinit var mBinding : FragmentFullScreenImageBinding
    private lateinit var mViewModel : FullScreenImageViewModel


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
        val url = arguments?.getString("URL")
        mViewModel = ViewModelProvider(this).get(FullScreenImageViewModel::class.java)
        if (url!=null){
            mViewModel.setImage(url)
        }
        else{
            Toast.makeText(requireContext(),"URL not found",Toast.LENGTH_SHORT).show()
        }
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.executePendingBindings()
    }

    private fun initObserver(){
        mViewModel.setWallpaper.observe(viewLifecycleOwner, Observer {
            if (it!=null){
               val bitmap = mBinding.wallpaper.drawable.toBitmap()
                WallpaperManager.getInstance(requireContext())
                    .setBitmap(bitmap)
            }
        })
    }



}

@BindingAdapter("imageUrl")
fun load(imageView: ImageView, imageUrl: String) {
    imageView.context.setImage(imageView, imageUrl)
}