package rocks.mobileera.mobileera.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_session.*

import rocks.mobileera.mobileera.R
import rocks.mobileera.mobileera.adapters.viewHolders.SpeakerViewHolder
import rocks.mobileera.mobileera.model.Session

class SessionFragment : Fragment() {

    companion object {

        private val ARGS_SESSION_JSON = "ARGS_SESSION_JSON"

        fun createBundle(session: Session): Bundle {
            val bundle = Bundle()
            bundle.putString(ARGS_SESSION_JSON, Gson().toJson(session))
            return bundle
        }
    }

    private var session: Session? = null
    private fun updateState(arguments: Bundle?) {
        arguments?.let { value ->
            session = Gson().fromJson(value.getString(ARGS_SESSION_JSON), Session::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_session, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateState(arguments)
        titleTextView.text = session?.title

        session?.speakersList?.getOrNull(0)?.let {
            SpeakerViewHolder(speaker1Layout).set(it)
            speaker1Layout.visibility = View.VISIBLE
        } ?: run {
            speaker1Layout.visibility = View.GONE
        }

        session?.speakersList?.getOrNull(1)?.let {
            SpeakerViewHolder(speaker2Layout).set(it)
            speaker2Layout.visibility = View.VISIBLE
        } ?: run {
            speaker2Layout.visibility = View.GONE
        }
    }


}
