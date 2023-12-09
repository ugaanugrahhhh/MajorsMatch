package capstone.product.tim.majorsmatch.view.story

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import capstone.product.tim.majorsmatch.R
import capstone.product.tim.majorsmatch.data.paging.LoadingStateAdapter
import capstone.product.tim.majorsmatch.databinding.ActivityListStoryBinding
import capstone.product.tim.majorsmatch.di.Injection
import capstone.product.tim.majorsmatch.view.ViewModelFactory
import capstone.product.tim.majorsmatch.view.camera.UploadFotoActivity
import capstone.product.tim.majorsmatch.view.main.MainActivity
import capstone.product.tim.majorsmatch.view.main.MainViewModel
import capstone.product.tim.majorsmatch.view.maps.MapsActivity
import capstone.product.tim.majorsmatch.view.welcome.WelcomeActivity
import com.google.android.material.bottomappbar.BottomAppBar

class ListStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListStoryBinding
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBarList.visibility = View.GONE

        binding.fabAddStory.setOnClickListener {
            val intent = Intent(this, UploadFotoActivity::class.java)
            startActivity(intent)
        }

        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar2)
        bottomAppBar.setNavigationIcon(R.drawable.baseline_map_24)

        val userRepository =
            Injection.provideRepository(this)
        viewModel =
            ViewModelProvider(this, ViewModelFactory(userRepository))[MainViewModel::class.java]

        viewModel.getSession().observe(this) { user ->
            if (!user.statusLoginUser) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                getData(user.token)
                setupStoryList(user.token)
            }
        }

        binding.bottomAppBar2.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        binding.logout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun setupStoryList(token: String) {
        storyAdapter = StoryAdapter()

        viewModel.getAllStories(token)

        binding.listItem.apply {
            layoutManager = LinearLayoutManager(this@ListStoryActivity)
            adapter = storyAdapter
        }
    }

    private fun getData(token: String) {
        val adapter = StoryAdapter()

        binding.listItem.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        viewModel.getAllStories(token)

        viewModel.quoteData(token).observe(this) { response ->
            Log.d("Stories", "onCreate: $response")
            response?.let {
                storyAdapter.submitData(lifecycle, it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSession().observe(this) { user ->
            if (user.statusLoginUser) {
                viewModel.getStories(user.token)
            }
        }
    }
}

