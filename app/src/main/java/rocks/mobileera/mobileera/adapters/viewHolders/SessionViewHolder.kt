package rocks.mobileera.mobileera.adapters.viewHolders

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
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

    fun set(context: Context?, session: Session?, track: Int) {
        val context = context?.let { it } ?: return
        val session = session?.let { it } ?: return






        setFavoritesButton(context, session)
        setAvatar(session)

    }

    private fun setFavoritesButton(context: Context, session: Session) {
        addToFavorites.visibility = (if (session.isSystemAnnounce()) View.GONE else View.VISIBLE)
        addToFavorites.setImageResource( if (session.isFavorite(context)) R.drawable.star_filled else R.drawable.star_empty )
    }

    private fun setAvatar(session: Session) {
        session.speakersList?.firstOrNull()?.photoUrl.let {photoUrl ->
            Picasso.get().load(Uri.parse(photoUrl)).transform(CircleTransform()).into(avatarImageView)
        } ?: run {
            if (session.image.isNullOrEmpty()) {
                avatarImageView.setImageResource(android.R.color.transparent)
                return
            }

            session.image.let {sessionUrl ->
                Picasso.get().load(Uri.parse(domain + sessionUrl)).transform(CircleTransform()).into(avatarImageView)
            }
        }
    }
}