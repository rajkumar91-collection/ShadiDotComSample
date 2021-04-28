package com.rajkumar.shadidotcom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rajkumar.shadidotcom.R
import com.rajkumar.shadidotcom.databinding.RowItemProfileListBinding
import com.rajkumar.shadidotcom.repository.database.Profile

class ProfileListAdapter(private val listener: IRecyclerClickListener<Profile>) :
    RecyclerView.Adapter<ProfileListAdapter.MoveListViewHolder>() {

    private var profiles: ArrayList<Profile>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoveListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowItemProfileListBinding.inflate(layoutInflater)
        return MoveListViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MoveListViewHolder, i: Int) {
        val profile = profiles?.get(i)
        profile?.let { holder.bind(it) }
        holder.binding.executePendingBindings()
    }



    override fun getItemCount(): Int {
        return if (profiles != null) {
            profiles!!.size
        } else 0
    }


    private fun clearProfilesList() {
        if (profiles == null) {
            profiles = ArrayList()
        } else {
            profiles!!.clear()
        }
        notifyDataSetChanged()
    }




    fun setProfiles(profiles: ArrayList<Profile>?) {
        this.profiles = profiles
        notifyDataSetChanged()
    }




    inner class MoveListViewHolder(val binding: RowItemProfileListBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        fun bind(profile: Profile) {
            binding.model = profile
            binding.btnAccept.setOnClickListener(this)
            binding.btnDecline.setOnClickListener(this)
        }

        override fun onClick(p0: View) {
            when(p0.id)
            {
                R.id.btn_accept ->{
                    profiles!![bindingAdapterPosition].interest = "Y"
                }
                R.id.btn_decline ->{
                    profiles!![bindingAdapterPosition].interest = "N"
                }


            }
            listener.onClick(profiles!![bindingAdapterPosition],p0)
            notifyDataSetChanged()

        }
    }



}
