package rocks.mobileera.mobileera.adapters.viewHolders

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_session.view.*
import rocks.mobileera.mobileera.model.Session

class SessionViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val titleTextView: TextView = view.titleTextView
    val nameTextView: TextView = view.nameTextView
    val avatarImageView: ImageView = view.avatarImageView

    fun set(context: Context?, session: Session?, track: Int) {
        //Picasso.get().load(Uri.parse(item.photoUrl)).into(holder.avatarImageView)

    }
}