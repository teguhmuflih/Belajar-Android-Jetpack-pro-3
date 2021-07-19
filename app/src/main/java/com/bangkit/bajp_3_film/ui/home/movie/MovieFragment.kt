package com.bangkit.bajp_3_film.ui.home.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.bajp_3_film.R
import com.bangkit.bajp_3_film.data.local.entity.MovieEntity
import com.bangkit.bajp_3_film.databinding.FragmentMovieBinding
import com.bangkit.bajp_3_film.ui.detail.DetailActivity
import com.bangkit.bajp_3_film.viewmodel.ViewModelFactory
import com.bangkit.bajp_3_film.vo.Status

class MovieFragment : Fragment() {

    private lateinit var fragmentMovieBinding: FragmentMovieBinding
    private lateinit var adapter2: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter2 = MovieAdapter()
        adapter2.notifyDataSetChanged()
        handleData()

        fragmentMovieBinding.progressBar.visibility = View.VISIBLE

        val factory = ViewModelFactory.getInstance(requireActivity())
        val movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        movieViewModel.getMovies().observe(viewLifecycleOwner, { movie ->
            if (movie != null) {
                when (movie.status) {
                    Status.LOADING -> fragmentMovieBinding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        fragmentMovieBinding.progressBar.visibility = View.GONE
                        adapter2.submitList(movie.data)
                        adapter2.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        fragmentMovieBinding.progressBar.visibility = View.GONE
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun handleData(){
        fragmentMovieBinding.progressBar.visibility = View.INVISIBLE

        fragmentMovieBinding.rvMovies.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = adapter2
        }

        adapter2.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MovieEntity) {
                val moveActivity = Intent(activity, DetailActivity::class.java)
                moveActivity.putExtra(DetailActivity.EXTRA_DETAIL, "MOVIE")
                moveActivity.putExtra(DetailActivity.EXTRA_ID, data.id)
                startActivity(moveActivity)
            }
        })
    }
}