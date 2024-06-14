package com.example.a6706220090_assesment2.ui.Screen

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6706220090_assesment2.model.Menu
import com.example.a6706220090_assesment2.network.ApiStatus
import com.example.a6706220090_assesment2.network.MenuApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class MainViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Menu>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set
    var errorMessage = mutableStateOf<String?>(null)
        private set



    fun retrieveData(userId: String){
        viewModelScope.launch (Dispatchers.IO){
            status.value =ApiStatus.LOADING
            try {
                data.value = MenuApi.service.getMenu(userId)
                status.value = ApiStatus.SUCCES
            } catch (e: Exception){
                Log.d("MainViewModel","Failure: ${e.message}")
                status.value =ApiStatus.FAILED
            }
        }
    }
    fun saveData(userId: String, hargaMakanan: String, namaMakanan: String, bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = MenuApi.service.postMenu(
                    userId,
                    hargaMakanan.toRequestBody("text/plain".toMediaTypeOrNull()),
                    namaMakanan.toRequestBody("text/plain".toMediaTypeOrNull()),
                    bitmap.toMultipartBody()
                )

                if (result.status == "success")
                    retrieveData(userId)
                else
                    throw Exception(result.message)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }
    fun deleteData(userId: String, id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = MenuApi.service.deleteMenu(userId, id)
                if (result.status == "success")
                    retrieveData(userId)
                else
                    throw Exception(result.message)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }
    private fun Bitmap.toMultipartBody(): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody(
            "image/jpg".toMediaTypeOrNull(), 0, byteArray.size)
        return MultipartBody.Part.createFormData(
            "image", "image.jpg", requestBody)
    }

    fun clearMessage() { errorMessage.value = null }
}