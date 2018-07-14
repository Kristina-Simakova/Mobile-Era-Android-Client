package rocks.mobileera.mobileera.viewModels

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.database.*
import rocks.mobileera.mobileera.model.Speaker


class SpeakersViewModel : ViewModel() {

    private var database: DatabaseReference? = null

    init {
        database = FirebaseDatabase.getInstance().reference
        database?.keepSynced(true)
    }

    private var days: MutableLiveData<List<Speaker>>? = null
    fun getSpeakers(): MutableLiveData<List<Speaker>>? {
        if (days == null) {
            days = MutableLiveData()
            loadSpeakers()
        }

        return days
    }

    private fun loadSpeakers() {

        val speakersListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val speakersSnapshot = snapshot.child("speakers").value
                val instructorsSnapshot = snapshot.child("instructors").value

                if (speakersSnapshot == null || instructorsSnapshot == null) {
                    return
                }

                // TODO: complete loadSpeakers method
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadSpeakers:onCancelled ${databaseError.toException()}")
            }
        }

        database?.addValueEventListener(speakersListener)
    }

}