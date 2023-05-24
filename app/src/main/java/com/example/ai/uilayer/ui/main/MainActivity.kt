package com.example.ai.uilayer.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ai.R
import com.example.ai.databinding.ActivityMainBinding
import com.example.ai.uilayer.ui.detail.CoinDetailActivity
import com.example.ai.uilayer.uistatemodel.CoinUIStateModel
import com.example.ai.uilayer.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    private val coinListAdapter by lazy { CoinListAdapter(listOf()) }

//    private val requestPermissionLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (isGranted) {
//                // Permission is granted. Continue the action or workflow in your
//                // app.
//                Intent(baseContext, CoinDetailActivity::class.java).run {
//                    startActivity(this)
//                }
//            } else {
//                // Explain to the user that the feature is unavailable because the
//                // feature requires a permission that the user has denied. At the
//                // same time, respect the user's decision. Don't link to system
//                // settings in an effort to convince the user to change their
//                // decision.
//            }
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        checkPermission()
        observeViewModel()
        initView()
        onViewModelObserved()
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance())
//                .commitNow()
//        }
    }

//    private fun checkPermission() {
//        when {
//            ContextCompat.checkSelfPermission(
//                baseContext,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED -> {
//                // You can use the API that requires the permission.
//            }
//            else -> {
//                // You can directly ask for the permission.
//                // The registered ActivityResultCallback gets the result of this request.
//                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//            }
//        }
//    }

    override fun onStart() {
        super.onStart()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
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
//            }
            isShowToast.observe(this@MainActivity) {
                Toast.makeText(baseContext, it, Toast.LENGTH_LONG).show()
            }
            clickedItem.observe(this@MainActivity) {
                goToDetailPage(it)
            }
            isShowOnlyBookmark.observe(this@MainActivity) {
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

    private fun goToDetailPage(coin: CoinUIStateModel) {
        val url = coin.coinrankingUrl
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
//        Intent(baseContext, CoinDetailActivity::class.java).run {
//            putExtra("uuid", uuid)
//            startActivity(this@run)
//        }
    }
}