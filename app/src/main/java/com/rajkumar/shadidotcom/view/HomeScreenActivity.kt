package com.rajkumar.shadidotcom.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rajkumar.shadidotcom.*
import com.rajkumar.shadidotcom.adapter.IRecyclerClickListener
import com.rajkumar.shadidotcom.adapter.ProfileListAdapter
import com.rajkumar.shadidotcom.databinding.ActivityHomeScreenBinding
import com.rajkumar.shadidotcom.repository.database.Profile
import com.rajkumar.shadidotcom.repository.Status
import com.rajkumar.shadidotcom.viewmodel.ProfileListViewModel
import com.rajkumar.shadidotcom.viewmodel.ProfileViewModelFactory

class HomeScreenActivity : AppCompatActivity() ,
    IRecyclerClickListener<Profile> {
private lateinit var binding : ActivityHomeScreenBinding
    private val mProfileListViewModel by lazy {
        ViewModelProvider(this, getViewModelFactory()).get(ProfileListViewModel::class.java)
    }

    private fun getViewModelFactory(): ViewModelProvider.Factory {
        return ProfileViewModelFactory((application as ShadiDotComApplication).repository)
    }

    private var mAdapter: ProfileListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen)
        initRecyclerView();
        subscribeObservers();
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getProfilesApi()



    }




    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun subscribeObservers() {
        mProfileListViewModel.getProfiles()
            .observe(this, Observer() {

                if (it != null) {
                    if (it.data != null) {
                        when (it.status) {
                            Status.PROGRESS -> {

                                    binding.progress.visibility = View.VISIBLE

                            }
                            Status.ERROR -> {
                                binding.progress.visibility = View.GONE
                                mAdapter?.setProfiles(it.data as ArrayList<Profile>?)
                                Toast.makeText(
                                    this,
                                    it.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                //adaptor no data
                            }
                            Status.SUCCESS -> {
                                binding.progress.visibility = View.GONE
                                mAdapter?.setProfiles(it.data as ArrayList<Profile>?)
                            }
                        }
                    }

                }
            })


    }


    private fun getProfilesApi() {
        binding.rvProfiles.smoothScrollToPosition(0)
        mProfileListViewModel.getProfileList()

    }

    private fun initRecyclerView() {
        mAdapter = ProfileListAdapter(this)
        binding.rvProfiles.adapter = mAdapter
    }


    override fun onClick(t: Profile, view: View) {
        Thread(Runnable {
            mProfileListViewModel.updateProfile(t)
        }).start()

    }


}