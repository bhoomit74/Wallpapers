package com.bhoomit.wallpapers.dashboard.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bhoomit.wallpapers.dashboard.data.model.ImageDetail
import com.bhoomit.wallpapers.databinding.ItemImageBinding

class DashboardAdapter(private val imageList : ArrayList<ImageDetail>,
                       private val onClickListener: OnClickListener) : RecyclerView.Adapter<DashboardAdapter.MyHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setData(imageList[position],onClickListener)
    }

    fun updateList(newMyImageList : ArrayList<ImageDetail>){
        val start = imageList.size
        imageList.addAll(newMyImageList)
        notifyItemRangeInserted(start,30)
    }

    class MyHolder(private val binding : ItemImageBinding) : RecyclerView.ViewHolder(binding.root){

        fun setData(imageDetail : ImageDetail,onClickListener: OnClickListener){
            binding.onClickListener = onClickListener
            binding.imageDetail = imageDetail
            binding.executePendingBindings()
        }
    }

}