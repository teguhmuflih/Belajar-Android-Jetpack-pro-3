package com.bangkit.bajp_3_film.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.bajp_3_film.data.remote.api.ApiService
import com.bangkit.bajp_3_film.data.remote.response.ResultsItem
import com.bangkit.bajp_3_film.data.remote.response.ResultsItemTV
import com.bangkit.bajp_3_film.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await
import java.io.IOException

class RemoteDataSource private constructor(private val apiService: ApiService){

    companion object{
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this){
                instance ?: RemoteDataSource(service).apply { instance = this}
            }
    }

    fun getMovie(): LiveData<ApiResponse<List<ResultsItem>>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<ResultsItem>>>()
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = apiService.getMovie().await()
                resultMovie.postValue(ApiResponse.success(response.results))
            } catch (e: IOException){
                e.printStackTrace()
                resultMovie.postValue(
                    ApiResponse.error(
                        e.message.toString(),
                        mutableListOf()
                    )
                )
            }
        }
        EspressoIdlingResource.decrement()
        return resultMovie
    }

    fun getMovieDetail(Id: Int): LiveData<ApiResponse<ResultsItem>> {
        EspressoIdlingResource.increment()
        val resultDetailMovie = MutableLiveData<ApiResponse<ResultsItem>>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getMovieDetail(Id).await()
            resultDetailMovie.postValue(ApiResponse.success(response))
        }
        EspressoIdlingResource.decrement()
        return  resultDetailMovie
    }

    fun getTV(): LiveData<ApiResponse<List<ResultsItemTV>>> {
        EspressoIdlingResource.increment()
        val resultTV = MutableLiveData<ApiResponse<List<ResultsItemTV>>>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getTV().await()
            resultTV.postValue(ApiResponse.success(response.resultsTV))
        }
        EspressoIdlingResource.decrement()
        return resultTV
    }

    fun getTVDetail(Id: Int): LiveData<ApiResponse<ResultsItemTV>> {
        EspressoIdlingResource.increment()
        val resultDetailTV = MutableLiveData<ApiResponse<ResultsItemTV>>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getTVDetail(Id).await()
            resultDetailTV.postValue(ApiResponse.success(response))
        }
        EspressoIdlingResource.decrement()
        return  resultDetailTV
    }
}