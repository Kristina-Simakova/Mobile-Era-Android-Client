package rocks.mobileera.mobileera.adapters.viewHolders

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_session.view.*
import rocks.mobileera.mobileera.R
import rocks.mobileera.mobileera.model.Session
import rocks.mobileera.mobileera.utils.CircleTransform
import rocks.mobileera.mobileera.utils.Preferences.Companion.domain

class SessionViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val titleTextView: TextView = view.titleTextView
    val nameTextView: TextView = view.nameTextView
    val avatarImageView: ImageView = view.avatarImageView
    val addToFavorites: ImageButton = view.addToFavoritesButton
    val separatorNameRoomView: View = view.separatorNameRoomView
    val colorBarView: View = view.colorBarView
    val extraSpeakersTextView: TextView = view.extraSpeakersTextView
    val roomTextView: TextView = view.roomTextView
    val tagsFlexboxLayout: FlexboxLayout = view.tagsFlexboxLayout


    fun set(context: Context?, session: Session?, track: Int) {
        val context = context?.let { it } ?: return
        val session = session?.let { it } ?: return

        titleTextView.text = (session.title + (if (session.lightning == true) " ⚡" else ""))
        nameTextView.text = session.speakersList?.joinToString(separator = ", ") { speaker -> speaker.name }

        session.speakersList?.count()?.let {speakersCount ->
            extraSpeakersTextView.visibility = (if (speakersCount < 2) View.GONE else View.VISIBLE)
            extraSpeakersTextView.text = ("+" + (speakersCount - 1))
        }

        setFavoritesButton(context, session)
        setAvatar(session)
        setTrack(session, track)
        setTags(session)
    }

    private fun setTags(session: Session) {
        if (session.isSystemAnnounce()) {
            tagsFlexboxLayout.visibility = View.GONE
            return
        }

        session.tags?.let {tags ->
            if (tags.isEmpty()) {
                tagsFlexboxLayout.visibility = View.GONE
                return
            }

            tagsFlexboxLayout.visibility = View.VISIBLE

            // Adding right tags into the flexbox
        }
    }

    private fun setTrack(session: Session, track: Int) {
        if (session.isWorkshop() || session.isSystemAnnounce()) {
            colorBarView.visibility = View.GONE
            separatorNameRoomView.visibility = View.GONE
            roomTextView.visibility = View.GONE
            return
        }

        when (track) {
            0 -> {
                colorBarView.setBackgroundResource(R.color.colorOdin)
                roomTextView.setText(R.string.odin)
            }

            1 -> {
                colorBarView.setBackgroundResource(R.color.colorFreyja)
                roomTextView.setText(R.string.freyja)
            }

            2 -> {
                colorBarView.setBackgroundResource(R.color.colorThor)
                roomTextView.setText(R.string.thor)
            }
        }

        colorBarView.visibility = View.VISIBLE
        separatorNameRoomView.visibility = View.VISIBLE
        roomTextView.visibility = View.VISIBLE
    }

    private fun setFavoritesButton(context: Context, session: Session) {
        addToFavorites.visibility = (if (session.isSystemAnnounce()) View.GONE else View.VISIBLE)
        addToFavorites.setImageResource( if (session.isFavorite(context)) R.drawable.star_filled else R.drawable.star_empty )

        addToFavorites.setOnClickListener {  }
    }

    private fun setAvatar(session: Session) {
        session.speakersList?.firstOrNull()?.photoUrl?.let {photoUrl ->
            Picasso.get().load(Uri.parse(domain + photoUrl)).transform(CircleTransform()).into(avatarImageView)
        } ?: run {
            session.image?.let {sessionUrl ->
                if (sessionUrl.isEmpty()) {
                    avatarImageView.setImageResource(android.R.color.transparent)
                    return
                }

                Picasso.get().load(Uri.parse(domain + sessionUrl)).transform(CircleTransform()).into(avatarImageView)
            } ?: run {
                avatarImageView.setImageResource(android.R.color.transparent)
            }
        }
    }
}