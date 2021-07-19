package com.bangkit.bajp_3_film.ui.favorite.moviefav

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bajp_3_film.R
import com.bangkit.bajp_3_film.data.local.entity.MovieEntity
import com.bangkit.bajp_3_film.databinding.FragmentMovieFavBinding
import com.bangkit.bajp_3_film.ui.detail.DetailActivity
import com.bangkit.bajp_3_film.ui.favorite.FavoriteViewModel
import com.bangkit.bajp_3_film.ui.home.movie.MovieAdapter
import com.bangkit.bajp_3_film.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

@Suppress("DEPRECATION")
class MovieFavFragment : Fragment() {

    private lateinit var binding: FragmentMovieFavBinding
    private lateinit var adapterMovie: MovieAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieFavBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding.rvFavMovies)

        adapterMovie = MovieAdapter()

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        viewModel.getFavMovie().observe(viewLifecycleOwner, { favMovie ->
            adapterMovie.submitList(favMovie)
            adapterMovie.notifyDataSetChanged()
        })

        binding.rvFavMovies.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = adapterMovie
        }

        adapterMovie.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MovieEntity) {
                val moveActivity = Intent(activity, DetailActivity::class.java)
                moveActivity.putExtra(DetailActivity.EXTRA_DETAIL, "MOVIE")
                moveActivity.putExtra(DetailActivity.EXTRA_ID, data.id)
                startActivity(moveActivity)
            }
        })
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val movieEntity = adapterMovie.getSwipedData(swipedPosition)
                movieEntity?.let { viewModel.setFavMovie(movieEntity, it.isFavorite) }
                val snackbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) { _ ->
                    movieEntity?.let { viewModel.setFavMovie(movieEntity, it.isFavorite) }
                }
                snackbar.show()
            }
        }
    })
}