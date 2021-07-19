package com.bangkit.bajp_3_film.di

import android.content.Context
import com.bangkit.bajp_3_film.data.DataRepository
import com.bangkit.bajp_3_film.data.local.LocalDataSource
import com.bangkit.bajp_3_film.data.local.room.DatabaseDAO
import com.bangkit.bajp_3_film.data.remote.RemoteDataSource
import com.bangkit.bajp_3_film.data.remote.api.ApiService
import com.bangkit.bajp_3_film.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context) : DataRepository {

        val database = DatabaseDAO.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiService.getApiService())

        val localDataSource = LocalDataSource.getInstance(database.dataDao())

        val appExecutors = AppExecutors()

        return DataRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}