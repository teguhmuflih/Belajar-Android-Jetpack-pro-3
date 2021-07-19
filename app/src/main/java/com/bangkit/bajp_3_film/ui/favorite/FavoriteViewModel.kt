package com.bangkit.bajp_3_film.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.bangkit.bajp_3_film.data.DataRepository
import com.bangkit.bajp_3_film.data.local.entity.MovieEntity
import com.bangkit.bajp_3_film.data.local.entity.TVShowEntity

class FavoriteViewModel(private val dataRepository: DataRepository) : ViewModel()  {

    fun getFavMovie(): LiveData<PagedList<MovieEntity>> = dataRepository.getListFavMovie()

    fun getFavTV(): LiveData<PagedList<TVShowEntity>> = dataRepository.getListFavTV()

    fun setFavMovie(movieEntity: MovieEntity, newState: Boolean) {
        dataRepository.setFavMovie(movieEntity, newState)
    }

    fun setFavTV(tvEntity: TVShowEntity, newState: Boolean) {
        dataRepository.setFavTV(tvEntity, newState)
    }
}