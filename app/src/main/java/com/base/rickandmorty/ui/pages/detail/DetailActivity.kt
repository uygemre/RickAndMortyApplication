package com.base.rickandmorty.ui.pages.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Observer
import com.base.core.extensions.gone
import com.base.core.extensions.loadImage
import com.base.core.helpers.LocalPrefManager
import com.base.core.networking.DataFetchResult
import com.base.data.database.model.FavoriteRickAndMortyDTO
import com.base.data.response.Results
import com.base.rickandmorty.R
import com.base.rickandmorty.ui.base.activity.BaseActivity
import com.base.rickandmorty.ui.pages.detail.viewmodel.DetailActivityViewModel
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.btn_back
import kotlinx.android.synthetic.main.activity_detail.tv_name
import kotlinx.android.synthetic.main.activity_detail.tv_species
import javax.inject.Inject

class DetailActivity : BaseActivity<DetailActivityViewModel>() {

    override val viewModelClass = DetailActivityViewModel::class.java
    override val layoutViewRes = R.layout.activity_detail

    @Inject
    lateinit var localPrefManager: LocalPrefManager

    private var results: Results? = null

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        results = intent.getParcelableExtra("results")
        setupView()

        ib_favorite.clicks().subscribe {
            if (localPrefManager.pull(results?.name ?: "", "") == "") {
                viewModel.insertFav(
                        FavoriteRickAndMortyDTO(
                                id = 0,
                                personId = results?.id
                        )
                )
                localPrefManager.push(results?.name ?: "", results?.name)
                ib_favorite.setBackgroundResource(R.drawable.ic_favorite_selected)
            } else {
                viewModel.deleteFavoritesById(results?.id ?: 0)
                localPrefManager.push(results?.name ?: "", "")
                ib_favorite.setBackgroundResource(R.drawable.ic_favorite_unselected)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        bindObserver()
    }

    private fun setupView() {
        btn_back.setOnClickListener {
            onBackPressed()
        }
        img_detail.loadImage(results?.image ?: "")
        tv_name.text = results?.name
        tv_species.text = results?.species
        tv_gender.text = results?.gender
        tv_last_known_location.text = results?.location?.name
        tv_origin_location_name.text = results?.origin?.name
        tv_number_of_episodes.text = results?.episode?.size.toString()
        tv_status.text = results?.status

        when (results?.status) {
            "Alive" -> {
                iv_status.loadImage(R.drawable.ic_alive)
            }
            "Dead" -> {
                iv_status.loadImage(R.drawable.ic_dead)
            }
            "unknown" -> {
                iv_status.loadImage(R.drawable.ic_unknown)
            }
            else -> {
                iv_status.gone()
            }
        }

        if (localPrefManager.pull(results?.name ?: "", "") == "") {
            ib_favorite.setBackgroundResource(com.base.component.R.drawable.ic_favorite_unselected)
        } else {
            ib_favorite.setBackgroundResource(com.base.component.R.drawable.ic_favorite_selected)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindObserver() {
        viewModel.getEpisodeAndAirDate(episode_number = results?.episode?.size)
        viewModel.getEpisodeNameAndAirDatePublishSubject.observe(this, Observer {
            when (it) {
                is DataFetchResult.Progress -> {
                }
                is DataFetchResult.Failure -> {
                }
                is DataFetchResult.Success -> {
                    tv_last_seen_episode.text = "${it.data.name} on ${it.data.air_date}"
                }
            }
        })
    }
}