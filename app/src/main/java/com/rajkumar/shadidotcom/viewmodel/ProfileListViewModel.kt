package com.rajkumar.shadidotcom.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rajkumar.shadidotcom.repository.DataRepository
import com.rajkumar.shadidotcom.repository.database.Profile
import com.rajkumar.shadidotcom.R
import com.rajkumar.shadidotcom.repository.Resource
import com.rajkumar.shadidotcom.repository.Status


class ProfileListViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val profiles: MediatorLiveData<Resource<List<Profile?>?>?> =
        MediatorLiveData()


    fun getProfiles(): MediatorLiveData<Resource<List<Profile?>?>?> {
        return profiles
    }

    fun getProfileList() {
        getProfile()

    }


    private fun getProfile() {

        val repositorySource: LiveData<Resource<List<Profile?>?>?> =
            dataRepository.searchProfilesListApi()!!
        profiles.addSource(
            repositorySource,
            Observer {
                if (it != null) {
                    if (it.status == Status.SUCCESS) {

                        if (it.data != null) {
                            if (it.data.isEmpty()) {
                                profiles.value =
                                    Resource(
                                        it.data as List<Profile?>?,
                                        "No data found",
                                        Status.ERROR
                                    )
                                profiles.removeSource(repositorySource)
                            }

                        }


                    } else if (it.status == Status.ERROR) {
                        profiles.removeSource(repositorySource)

                    }
                    profiles.value = it


                } else {
                    profiles.removeSource(repositorySource)
                }


            })
    }

    fun updateProfile(profile: Profile) {
        dataRepository.updateProfile(profile)
    }


}

class ProfileViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileListViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@BindingAdapter(value = ["setImageUrl"])
fun ImageView.bindImageUrl(url: String?) {
    if (url != null && url.isNotBlank()) {

        val options: RequestOptions = RequestOptions()
            .placeholder(R.drawable.no_pic)
            .error(R.drawable.no_pic)
        Glide.with(this.context)
            .setDefaultRequestOptions(options).load(url).into(this)
    }
}