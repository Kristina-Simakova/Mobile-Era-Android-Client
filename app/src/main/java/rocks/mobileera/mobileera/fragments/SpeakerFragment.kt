package rocks.mobileera.mobileera.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson

import rocks.mobileera.mobileera.R
import rocks.mobileera.mobileera.model.Speaker

class SpeakerFragment : Fragment() {

    companion object {

        private val ARGS_SPEAKER_JSON = "ARGS_SPEAKER_JSON"

        fun createBundle(speaker: Speaker): Bundle {
            val bundle = Bundle()
            bundle.putString(ARGS_SPEAKER_JSON, Gson().toJson(speaker))
            return bundle
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_speaker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


}
