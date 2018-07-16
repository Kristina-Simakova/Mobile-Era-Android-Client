package rocks.mobileera.mobileera.fragments

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import rocks.mobileera.mobileera.R
import kotlinx.android.synthetic.main.fragment_speaker.*

import rocks.mobileera.mobileera.model.Speaker
import rocks.mobileera.mobileera.utils.Preferences
import android.content.Intent



class SpeakerFragment : Fragment() {

    companion object {

        private val ARGS_SPEAKER_JSON = "ARGS_SPEAKER_JSON"

        fun createBundle(speaker: Speaker): Bundle {
            val bundle = Bundle()
            bundle.putString(ARGS_SPEAKER_JSON, Gson().toJson(speaker))
            return bundle
        }
    }

    private var speaker: Speaker? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_speaker, container, false)
    }

    private fun updateState(arguments: Bundle?) {
        arguments?.let { value ->
            speaker = Gson().fromJson(value.getString(ARGS_SPEAKER_JSON), Speaker::class.java)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateState(arguments)

        speaker?.let {
            Picasso.get().load(Uri.parse(Preferences.domain + it.photoUrl)).into(avatarImageView)
        } ?: run {
            avatarImageView.setImageResource(android.R.color.transparent)
        }

        nameTextView.text = speaker?.name
        companyTextView.text = speaker?.company
        bioTextView.text = speaker?.shortBio

        speaker?.socials?.let { socials ->
            twitterImageView.visibility = if (socials.any { social -> social.icon == "twitter"}) View.VISIBLE else View.GONE
            githubImageView.visibility = if (socials.any { social -> social.icon == "github"}) View.VISIBLE else View.GONE
            webImageView.visibility = if (socials.any { social -> social.icon == "website"}) View.VISIBLE else View.GONE
        } ?: run {
            twitterImageView.visibility = View.GONE
            githubImageView.visibility = View.GONE
            webImageView.visibility = View.GONE
        }

        twitterImageView?.setOnClickListener { v ->
            speaker?.socials?.first { it.icon == "twitter" }?.link?.let {
                openUrl(it)
            }
        }

        githubImageView?.setOnClickListener { v ->
            speaker?.socials?.first { it.icon == "github" }?.link?.let {
                openUrl(it)
            }
        }

        webImageView?.setOnClickListener { v ->
            speaker?.socials?.first { it.icon == "website" }?.link?.let {
                openUrl(it)
            }
        }
    }

    private fun openUrl(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }
}
