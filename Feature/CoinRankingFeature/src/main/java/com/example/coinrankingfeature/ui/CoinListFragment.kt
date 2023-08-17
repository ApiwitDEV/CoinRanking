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
        observeViewModel()
        initView()
        onViewModelObserved()
    }

    private fun initView() {
        binding.floatingBookmarkFilterButton.setOnClickListener {
            viewModel.showOnlyBookmarkedCoin()
        }
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                viewModel.getCoinList(p0?:"")
                return false
            }
        })
        binding.rvCoinList.adapter = coinListAdapter
    }

    private fun observeViewModel() {
        viewModel.apply {
//            coinList.observe(this@MainActivity) {
//                coinListAdapter.itemList = it
//                coinListAdapter.notifyDataSetChanged()
//            } X
            isShowToast.observe(viewLifecycleOwner) {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
            clickedItem.observe(viewLifecycleOwner) {
                goToDetailPage(it)
            }
            isShowOnlyBookmark.observe(viewLifecycleOwner) {
                if (it == true) {
                    binding.floatingBookmarkFilterButton.setImageResource(R.drawable.baseline_bookmark_24)
                    coinListAdapter.itemList = bookmarkedCoinList.value?: listOf()
                    coinListAdapter.notifyDataSetChanged()
                    return@observe
                }
                coinListAdapter.itemList = coinList.value?: listOf()
                coinListAdapter.notifyDataSetChanged()
                binding.floatingBookmarkFilterButton.setImageResource(R.drawable.baseline_bookmark_border_24)
            }
        }
    }

    private fun onViewModelObserved() {
        viewModel.getCoinList()
    }

    private fun goToDetailPage(coin: com.example.coinrankingfeature.uistatemodel.CoinUIStateModel) {
        val url = coin.coinrankingUrl
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
//        Intent(baseContext, CoinDetailActivity::class.java).run {
//            putExtra("uuid", uuid)
//            startActivity(this@run)
//        }
    }
}