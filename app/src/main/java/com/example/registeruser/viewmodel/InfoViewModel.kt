package com.example.registeruser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registeruser.models.UserData
import com.example.registeruser.network.ApiInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * @author jakhongirjalilov
 * @data 4/30/21
 * @project RegisterUser
 */
class InfoViewModel(private val api: ApiInterface) : ViewModel() {

    var isLoading = MutableLiveData<Boolean>()
    var isFound = MutableLiveData<Boolean>()

    var userData = MutableLiveData<UserData>()
    fun getUser(username: String): LiveData<UserData> {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.getUser(username)
                if (response.code() == 200) {
                    withContext(Dispatchers.Main) {
                        Log.i("ZZZZ", "${response.body()}")
                        isLoading.value = false
                        userData.value =
                            response.body()
                    }
                } else if (response.code() == 404) {
                    isFound.value = false
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    isLoading.value = false
                }
                Log.d("TTTT", "HttpException: " + e.message())
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    isLoading.value = false
                }
                Log.d("TTTT", "Ooops: Something else went wrong: $e")
            }
        }
        return userData
    }
}