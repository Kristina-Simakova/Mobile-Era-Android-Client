package rocks.mobileera.mobileera.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.row_speaker.view.*
import rocks.mobileera.mobileera.R
import rocks.mobileera.mobileera.adapters.interfaces.TagCallback

class TagsAdapter (
        private val tags: List<String>,
        private val listener: TagCallback?)
    : RecyclerView.Adapter<TagsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.row_tag_clickable, parent, false))
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: SpeakersAdapter.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val avatarImageView: ImageView = view.avatarImageView
        val nameTextView: TextView = view.nameTextView
    }
}