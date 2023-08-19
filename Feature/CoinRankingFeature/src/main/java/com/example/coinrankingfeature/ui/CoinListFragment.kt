package com.example.coinrankingfeature.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.coinrankingfeature.R
import com.example.coinrankingfeature.databinding.FragmentCoinRankingBinding
import com.example.coinrankingfeature.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinListFragment: Fragment() {

    private lateinit var binding: FragmentCoinRankingBinding
    private val viewModel by viewModel<MainViewModel>()

    private val coinListAdapter by lazy { CoinListAdapter(listOf()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoinRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGetAndUpdateCoinListFromApi()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopUpdateCoinData()
    }

    private fun initView() {
        binding.floatingBookmarkFilterButton.setOnClickListener {
            viewModel.showOnlyBookmarkedCoin()
        }
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(searchValue: String?): Boolean {
                viewModel.onSearchCoin(searchValue?:"")
                return false
            }
        })
        binding.rvCoinList.adapter = coinListAdapter
    }

    private fun observeViewModel() {
        viewModel.apply {
            coinList.observe(viewLifecycleOwner) {
                coinListAdapter.itemList = it
                coinListAdapter.notifyDataSetChanged()
            }
            isShowLoading.observe(viewLifecycleOwner) {
                if (it) {
                    //show loading
                    binding.loading.visibility = View.VISIBLE
                    binding.rvCoinList.visibility = View.GONE
                } else {
                    //hide loading
                    binding.loading.visibility = View.GONE
                    binding.rvCoinList.visibility = View.VISIBLE
                }
            }
            isShowToast.observe(viewLifecycleOwner) {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
            clickedItem.observe(viewLifecycleOwner) {
                goToDetailPage(it)
            }
            isShowOnlyBookmark.observe(viewLifecycleOwner) {
                if (it == true) {
                    binding.floatingBookmarkFilterButton.setImageResource(R.drawable.baseline_bookmark_24)
                    return@observe
                }
                binding.floatingBookmarkFilterButton.setImageResource(R.drawable.baseline_bookmark_border_24)
            }
        }
    }

    private fun goToDetailPage(coin: com.example.coinrankingfeature.uistatemodel.CoinUIStateModel) {
        val url = coin.coinrankingUrl
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}