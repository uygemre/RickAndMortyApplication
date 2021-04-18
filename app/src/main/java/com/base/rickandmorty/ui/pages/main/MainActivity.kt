package com.base.rickandmorty.ui.pages.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.base.component.RickAndMortyRecyclerviewAdapter
import com.base.component.ui.characteritem.CharacterItemDTO
import com.base.component.ui.characteritemforgrid.CharacterItemForGridDTO
import com.base.core.extensions.setup
import com.base.core.helpers.LocalPrefManager
import com.base.core.networking.DataFetchResult
import com.base.data.database.model.FavoriteRickAndMortyDTO
import com.base.data.response.Results
import com.base.rickandmorty.R
import com.base.rickandmorty.ui.base.activity.BaseActivity
import com.base.rickandmorty.ui.pages.detail.DetailActivity
import com.base.rickandmorty.ui.pages.main.dialog.DialogFilter
import com.base.rickandmorty.ui.pages.main.viewmodel.MainActivityViewModel
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity<MainActivityViewModel>() {

    override val viewModelClass = MainActivityViewModel::class.java
    override val layoutViewRes = R.layout.activity_main

    @Inject
    lateinit var adapter: RickAndMortyRecyclerviewAdapter

    @Inject
    lateinit var localPrefManager: LocalPrefManager

    var grid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        home_page_recyclerview.setup(adapter = adapter.getAdapter())

        adapter.getAdapter().itemClickListener = { item, position ->
            when (item) {
                is CharacterItemDTO -> {
                    openDetailActivity(item.results)
                }
                is CharacterItemForGridDTO -> {
                    openDetailActivity(item.results)
                }
            }
        }

        adapter.getAdapter().itemViewClickListener = { view, item, position ->
            when (item) {
                is CharacterItemDTO -> {
                    if (view.id == R.id.ib_favorite) {
                        val imgFavorites = view.findViewById<ImageButton>(R.id.ib_favorite)
                        if (localPrefManager.pull(item.results.name ?: "", "") == "") {
                            viewModel.insertFav(
                                    FavoriteRickAndMortyDTO(
                                            id = 0,
                                            personId = item.results.id
                                    )
                            )
                            localPrefManager.push(item.results.name ?: "", item.results.name)
                            imgFavorites.setBackgroundResource(R.drawable.ic_favorite_selected_16)
                        } else {
                            viewModel.deleteFavoritesById(item.results.id ?: 0)
                            localPrefManager.push(item.results.name ?: "", "")
                            imgFavorites.setBackgroundResource(R.drawable.ic_favorite_unselected_16)
                        }
                    }
                }
                is CharacterItemForGridDTO -> {
                    if (view.id == R.id.ib_favorites) {
                        val imgFavorites = view.findViewById<ImageButton>(R.id.ib_favorites)
                        if (localPrefManager.pull(item.results.name ?: "", "") == "") {
                            viewModel.insertFav(
                                    FavoriteRickAndMortyDTO(
                                            id = 0,
                                            personId = item.results.id
                                    )
                            )
                            localPrefManager.push(item.results.name ?: "", item.results.name)
                            imgFavorites.setBackgroundResource(R.drawable.ic_favorite_selected_16)
                        } else {
                            viewModel.deleteFavoritesById(item.results.id ?: 0)
                            localPrefManager.push(item.results.name ?: "", "")
                            imgFavorites.setBackgroundResource(R.drawable.ic_favorite_unselected_16)
                        }
                    }
                }
            }
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_list -> {
                    grid = false
                    home_page_recyclerview.setup(adapter = adapter.getAdapter())
                }
                R.id.rb_grid -> {
                    grid = true
                    val layoutManager = GridLayoutManager(applicationContext, 2)
                    home_page_recyclerview.setup(adapter = adapter.getAdapter(), layoutManager = layoutManager)
                }
            }
        }

        float_action_button_filter.setOnClickListener {
            val dialog = DialogFilter(viewModel)
            dialog.show(supportFragmentManager, "dialog")
        }
    }

    override fun onResume() {
        super.onResume()
        bindObserver()
    }

    private fun bindObserver() {
        viewModel.getCharacter()
        viewModel.getCharacterPublishSubject.observe(this, Observer { _data ->
            when (_data) {
                is DataFetchResult.Success -> {
                    viewModel.consumeCharacter(_data.data, grid)

                    rb_list.clicks().subscribe {
                        viewModel.consumeCharacter(_data.data, grid)
                    }
                    rb_grid.clicks().subscribe {
                        viewModel.consumeCharacter(_data.data, grid)
                    }
                }
                is DataFetchResult.Progress -> {
                }
                is DataFetchResult.Failure -> {
                }
            }
        })

        viewModel.getCharactersWithFiltersPublishSubject.observe(this, Observer { data ->
            when (data) {
                is DataFetchResult.Success -> {
                    viewModel.consumeCharacter(data.data, grid)

                    rb_list.clicks().subscribe {
                        viewModel.consumeCharacter(data.data, grid)
                    }
                    rb_grid.clicks().subscribe {
                        viewModel.consumeCharacter(data.data, grid)
                    }
                }
                is DataFetchResult.Progress -> {
                }
                is DataFetchResult.Failure -> {
                    adapter.getAdapter().updateAllItems(emptyList())
                }
            }
        })

        viewModel.getCharacterPublish.subscribe {
            adapter.getAdapter().updateAllItems(it)
        }.isDisposed
    }

    private fun openDetailActivity(item: Results) {
        val intent: Intent? = Intent(this, DetailActivity::class.java)
        intent?.putExtra("results", item)
        startActivity(intent)
    }
}
