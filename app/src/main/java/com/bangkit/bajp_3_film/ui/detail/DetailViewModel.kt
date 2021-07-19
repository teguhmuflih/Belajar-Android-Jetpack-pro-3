package com.bangkit.bajp_3_film.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.bajp_3_film.data.DataRepository
import com.bangkit.bajp_3_film.data.local.entity.MovieEntity
import com.bangkit.bajp_3_film.data.local.entity.TVShowEntity
import com.bangkit.bajp_3_film.vo.Resource

class DetailViewModel(private val dataRepository: DataRepository): ViewModel() {

    fun getMovieDetail(id : Int) : LiveData<Resource<MovieEntity>> = dataRepository.getMovieDetail(id)

    fun getTvDetail(id: Int) : LiveData<Resource<TVShowEntity>> = dataRepository.getTVDetail(id)

    fun setFavMovie(movie: MovieEntity, state: Boolean) = dataRepository.setFavMovie(movie, state)

    fun setFavTV(tv: TVShowEntity, state: Boolean) = dataRepository.setFavTV(tv, state)
}