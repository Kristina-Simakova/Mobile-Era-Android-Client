package rocks.mobileera.mobileera.adapters

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_speaker.view.*
import rocks.mobileera.mobileera.R
import rocks.mobileera.mobileera.model.Speaker


import rocks.mobileera.mobileera.fragments.SpeakersFragment.OnSpeakersListListener

class SpeakersAdapter(
        speakers: List<Speaker>?,
        private val listener: OnSpeakersListListener?)
    : RecyclerView.Adapter<SpeakersAdapter.ViewHolder>() {

    var speakers = speakers
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val onClickListener: View.OnClickListener

    init {
        // TODO: make sure that it's been called
        onClickListener = View.OnClickListener { v ->
            val position = v.tag as? Int
            position?.let {
                val speaker = speakers?.getOrNull(position) //TODO: test it
                listener?.onSpeakerClicked(speaker)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_speaker, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = speakers?.getOrNull(position)?.let { it } ?: return

        Picasso.get().load(Uri.parse(item.photoUrl)).into(holder.avatarImageView)
        holder.nameTextView.text = item.name

        with(holder.view) {
            tag = position
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int {
        val speakers = speakers?.let { it } ?: return 0
        return speakers.size
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val avatarImageView: ImageView = view.avatarImageView
        val nameTextView: TextView = view.nameTextView
    }
}
