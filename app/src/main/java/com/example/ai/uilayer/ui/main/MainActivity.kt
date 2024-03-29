package com.example.ai.uilayer.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ai.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
//    private val viewModel: com.example.coinrankingfeature.viewmodel.MainViewModel by viewModel()

//    private val coinListAdapter by lazy { com.example.coinrankingfeature.ui.CoinListAdapter(listOf()) }

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
//        supportFragmentManager.commit {
//            replace<CoinListFragment>(R.id.fragment_container)
//            setReorderingAllowed(true)
//            addToBackStack(null)
//        }
        setContentView(view)
//        checkPermission()
//        observeViewModel()
//        initView()
//        onViewModelObserved()
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
}