package capstone.product.tim.majorsmatch.view.story

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import capstone.product.tim.majorsmatch.databinding.ItemStoryBinding
import capstone.product.tim.majorsmatch.response.ListStoryItem
import com.bumptech.glide.Glide

class StoryAdapter : PagingDataAdapter<ListStoryItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listStory: ListStoryItem, onStoryClick: () -> Unit) {
            with(binding) {
                Glide.with(itemView)
                    .load(listStory.photoUrl)
                    .into(ivItemPhoto)
                tvItemName.text = listStory.name
                tvItemDesc.text = listStory.description
            }
            itemView.setOnClickListener { onStoryClick() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = getItem(position)
        val onStoryClick: () -> Unit = {
            val intent = Intent(holder.itemView.context, DetailStoryActivity::class.java)
            intent.putExtra(DetailStoryActivity.ID, story?.id)
            intent.putExtra(DetailStoryActivity.NAME, story?.name)
            intent.putExtra(DetailStoryActivity.DESCRIPTION, story?.description)
            intent.putExtra(DetailStoryActivity.PICTURE, story?.photoUrl)
            holder.itemView.context.startActivity(intent)
        }
        holder.bind(story!!, onStoryClick)
    }


    companion object {
      val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
