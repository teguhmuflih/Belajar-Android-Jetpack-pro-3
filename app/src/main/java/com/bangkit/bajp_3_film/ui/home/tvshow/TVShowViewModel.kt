package com.bangkit.bajp_3_film.ui.home.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.bangkit.bajp_3_film.data.DataRepository
import com.bangkit.bajp_3_film.data.local.entity.TVShowEntity
import com.bangkit.bajp_3_film.vo.Resource

class TVShowViewModel(private val dataRepository: DataRepository) : ViewModel() {

    fun getTv() : LiveData<Resource<PagedList<TVShowEntity>>> = dataRepository.getTV()
}