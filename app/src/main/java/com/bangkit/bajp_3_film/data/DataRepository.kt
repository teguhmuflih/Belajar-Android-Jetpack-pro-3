package com.bangkit.bajp_3_film.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bangkit.bajp_3_film.data.local.LocalDataSource
import com.bangkit.bajp_3_film.data.local.entity.MovieEntity
import com.bangkit.bajp_3_film.data.local.entity.TVShowEntity
import com.bangkit.bajp_3_film.data.remote.ApiResponse
import com.bangkit.bajp_3_film.data.remote.RemoteDataSource
import com.bangkit.bajp_3_film.data.remote.response.ResultsItem
import com.bangkit.bajp_3_film.data.remote.response.ResultsItemTV
import com.bangkit.bajp_3_film.utils.AppExecutors
import com.bangkit.bajp_3_film.vo.Resource

class DataRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
)
    : DataSource {

    companion object{
        @Volatile
        private var instance: DataRepository? = null

        fun getInstance(remoteData: RemoteDataSource, localData: LocalDataSource, appExecutors: AppExecutors): DataRepository =
            instance ?: synchronized(this){
                instance ?: DataRepository(remoteData, localData, appExecutors).apply { instance = this }
            }
    }

    override fun getMovie(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<ResultsItem>>(appExecutors){
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getMovie()

            public override fun saveCallResult(data: List<ResultsItem>) {
                val movieList = ArrayList<MovieEntity>()
                for(item in data){
                    val movies = MovieEntity(
                        null,
                        item.posterPath,
                        item.id,
                        item.title,
                        item.releaseDate,
                        item.voteAverage.toString(),
                        item.overview,
                        false
                    )
                    movieList.add(movies)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getMovieDetail(id: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, ResultsItem>(appExecutors){
            override fun loadFromDB(): LiveData<MovieEntity> =
                localDataSource.getDetailMovies(id)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data == null

            public override fun createCall(): LiveData<ApiResponse<ResultsItem>> =
                remoteDataSource.getMovieDetail(id)

            override fun saveCallResult(data: ResultsItem) {
                val movie = MovieEntity(
                    null,
                    data.posterPath,
                    data.id,
                    data.title,
                    data.releaseDate,
                    data.voteAverage.toString(),
                    data.overview,
                    false
                )
                localDataSource.insertMovieDetail(movie)
            }
        }.asLiveData()
    }

    override fun getTV(): LiveData<Resource<PagedList<TVShowEntity>>> {
        return object : NetworkBoundResource<PagedList<TVShowEntity>, List<ResultsItemTV>>(appExecutors){
            public override fun loadFromDB(): LiveData<PagedList<TVShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTV(), config).build()
            }

            override fun shouldFetch(data: PagedList<TVShowEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<ResultsItemTV>>> =
                remoteDataSource.getTV()

            public override fun saveCallResult(data: List<ResultsItemTV>) {
                val tvList = ArrayList<TVShowEntity>()
                for(item in data){
                    val tvs = TVShowEntity(
                        null,
                        item.posterPath,
                        item.id,
                        item.originalName,
                        item.firstAirDate,
                        item.voteAverage.toString(),
                        item.overview,
                        false
                    )
                    tvList.add(tvs)
                }
                localDataSource.insertTV(tvList)
            }
        }.asLiveData()
    }

    override fun getTVDetail(id: Int): LiveData<Resource<TVShowEntity>> {
        return object : NetworkBoundResource<TVShowEntity, ResultsItemTV>(appExecutors){
            override fun loadFromDB(): LiveData<TVShowEntity> =
                localDataSource.getDetailTVs(id)

            override fun shouldFetch(data: TVShowEntity?): Boolean =
                data == null

            public override fun createCall(): LiveData<ApiResponse<ResultsItemTV>> =
                remoteDataSource.getTVDetail(id)

            override fun saveCallResult(data: ResultsItemTV) {
                val tv = TVShowEntity(
                    null,
                    data.posterPath,
                    data.id,
                    data.originalName,
                    data.firstAirDate,
                    data.voteAverage.toString(),
                    data.overview,
                    false
                )
                localDataSource.insertTVDetail(tv)
            }
        }.asLiveData()
    }

    override fun getListFavMovie(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getListFavMovies(), config).build()
    }

    override fun setFavMovie(movie: MovieEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setFavMovie(movie, state) }

    override fun getListFavTV(): LiveData<PagedList<TVShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getListFavTVs(), config).build()
    }

    override fun setFavTV(tv: TVShowEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setFavTV(tv, state) }
}