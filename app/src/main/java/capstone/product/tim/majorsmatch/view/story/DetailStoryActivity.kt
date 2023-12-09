package capstone.product.tim.majorsmatch.view.story

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import capstone.product.tim.majorsmatch.databinding.ActivityDetailStoryBinding
import com.bumptech.glide.Glide

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressDetail.visibility
        binding.progressDetail.visibility = View.GONE

        id = intent.getStringExtra(ID)?: ""
        name = intent.getStringExtra(NAME)?: ""
        description = intent.getStringExtra(DESCRIPTION)?: ""
        picture = intent.getStringExtra(PICTURE)?: ""

        binding.tvDetailName.text = name
        binding.tvDetailDescription.text = description

        Glide.with(this).load(picture).into(binding.ivDetailPhoto)
    }

    companion object {
        const val ID = "ID"
        const val NAME = "NAME"
        const val DESCRIPTION = "DESCRIPTION"
        const val PICTURE = "PICTURE"

        var id: String = ""
        var name: String = ""
        var description: String? = null
        var picture: String? = null
    }
}