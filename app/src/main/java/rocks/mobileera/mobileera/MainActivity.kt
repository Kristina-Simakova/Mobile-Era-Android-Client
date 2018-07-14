package rocks.mobileera.mobileera

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var database: DatabaseReference? = null

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)


        database = FirebaseDatabase.getInstance().reference
        database?.keepSynced(true)
    }
}
