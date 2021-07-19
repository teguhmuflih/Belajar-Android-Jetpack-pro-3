package com.bangkit.bajp_3_film.ui.home.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.bangkit.bajp_3_film.data.DataRepository
import com.bangkit.bajp_3_film.data.local.entity.MovieEntity
import com.bangkit.bajp_3_film.vo.Resource

class MovieViewModel(private val dataRepository: DataRepository) : ViewModel() {

    fun getMovies() : LiveData<Resource<PagedList<MovieEntity>>> = dataRepository.getMovie()
}