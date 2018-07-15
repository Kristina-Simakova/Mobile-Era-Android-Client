package rocks.mobileera.mobileera

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import androidx.navigation.ui.NavigationUI
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_main.*
import rocks.mobileera.mobileera.adapters.interfaces.AddToFavoritesCallback
import rocks.mobileera.mobileera.adapters.interfaces.SessionCallback
import rocks.mobileera.mobileera.adapters.interfaces.SpeakerCallback
import rocks.mobileera.mobileera.fragments.SessionFragment
import rocks.mobileera.mobileera.fragments.SpeakerFragment
import rocks.mobileera.mobileera.model.Session
import rocks.mobileera.mobileera.model.Speaker

class MainActivity : AppCompatActivity(), SpeakerCallback, SessionCallback, AddToFavoritesCallback {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.let { bottomNavView ->
            NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)
        }
    }

    override fun onSpeakerClick(speaker: Speaker?) {
        speaker?.let { value ->
            val bundle = SpeakerFragment.createBundle(value)
            NavHostFragment.findNavController(hostFragment).navigate(R.id.action_navigation_speakers_to_speakerFragment, bundle)
        }
    }

    override fun onSessionClick(session: Session?) {
        session?.let { value ->
            val bundle = SessionFragment.createBundle(value)
            NavHostFragment.findNavController(hostFragment).navigate(R.id.action_navigation_schedule_to_sessionFragment, bundle)
        }
    }

    override fun onAddToFavoritesClick(session: Session?) {
        applicationContext?.let {context ->
            session?.toggleFavorites(context)
        }
    }
}
