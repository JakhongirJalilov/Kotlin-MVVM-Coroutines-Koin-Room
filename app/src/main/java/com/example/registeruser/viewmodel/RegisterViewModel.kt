package com.example.registeruser.viewmodel

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
class RegisterViewModel(val api: ApiInterface) : ViewModel() {
    var isLoading = MutableLiveData<Boolean>()
    var isSuccess = MutableLiveData<Boolean>()

    fun registerUser(userData: UserData): LiveData<Boolean> {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.registerUser(userData)
                if (response.code() == 200) {
                    withContext(Dispatchers.Main) {
                        isLoading.value = false
                        isSuccess.value = true
                    }
                } else {
                    isLoading.value = false
                    isSuccess.value = false
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    isLoading.value = false
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    isLoading.value = false
                }
            }
        }
        return isSuccess
    }
}