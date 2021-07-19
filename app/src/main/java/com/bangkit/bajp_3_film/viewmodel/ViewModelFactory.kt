package com.bangkit.bajp_3_film.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.bajp_3_film.data.DataRepository
import com.bangkit.bajp_3_film.di.Injection
import com.bangkit.bajp_3_film.ui.detail.DetailViewModel
import com.bangkit.bajp_3_film.ui.favorite.FavoriteViewModel
import com.bangkit.bajp_3_film.ui.home.movie.MovieViewModel
import com.bangkit.bajp_3_film.ui.home.tvshow.TVShowViewModel

class ViewModelFactory private constructor(private val mDataRepository: DataRepository) : ViewModelProvider.NewInstanceFactory() {

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(mDataRepository) as T
            }

            modelClass.isAssignableFrom(TVShowViewModel::class.java) -> {
                TVShowViewModel(mDataRepository) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(mDataRepository) as T
            }

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(mDataRepository) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}