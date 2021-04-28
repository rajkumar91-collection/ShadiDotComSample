package com.rajkumar.shadidotcom.repository

import androidx.lifecycle.LiveData
import com.rajkumar.shadidotcom.repository.database.ProfileDao
import com.rajkumar.altbalajitestapp.utils.Constant
import com.rajkumar.shadidotcom.repository.database.Profile


class DataRepository(private val profileDao: ProfileDao) {


    fun searchProfilesListApi(

    ): LiveData<Resource<List<Profile?>?>?>? {
        return object :
            NetworkBoundResource<List<Profile?>?, ProfileResponse?>(AppExecutors) {
            override fun saveCallResult(response: ProfileResponse?) {
                if (response?.results != null) {

                    profileDao.insertProfile(parseResultToProfileList(response.results))


                }
            }

            override fun loadFromDb(): LiveData<List<Profile?>?> {
                return profileDao.getProfiles()
            }

            override fun createCall(): LiveData<ApiCallBack<ProfileResponse?>> {
                return WebServiceController.getApiService()
                    ?.getProfile(
                    Constant.RESULTS
                )!!
            }


            override fun shouldFetch(data: List<Profile?>?): Boolean {
                return true
            }
        }.asLiveData
    }

    private fun parseResultToProfileList(results: List<ResultsItem>): List<Profile?>? {
      val list = ArrayList<Profile?>()
       for(result in results)
       {
           val profile = Profile(
               result.login.uuid,
               result.gender,
               result.name.first + result.name.last,
               result.location.city,
               result.location.state,
               result.email,
               result.dob.age.toString(),
               result.picture.large,
               "NA"
           )
           list.add(profile)
       }
        return list
    }

    fun updateProfile(profile: Profile) {
        profileDao.updateProfile(profile.uuid,profile.interest)
    }


}