package rocks.mobileera.mobileera.viewModels

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.gson.Gson
import rocks.mobileera.mobileera.model.Speaker


class SpeakersViewModel : ViewModel() {

    private var database: DatabaseReference? = null

    init {
        database = FirebaseDatabase.getInstance().reference
        database?.keepSynced(true)
    }

    private var speakers: MutableLiveData<List<Speaker>>? = null
    fun getSpeakers(): MutableLiveData<List<Speaker>>? {
        if (speakers == null) {
            speakers = MutableLiveData()
            loadSpeakers()
        }

        return speakers
    }

    private fun loadSpeakers() {

        val speakersListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val gson = Gson()

                val speakersSnapshot = snapshot.child("speakers").value
                val instructorsSnapshot = snapshot.child("instructors").value

                if (speakersSnapshot == null || instructorsSnapshot == null) {
                    return
                }

                val speakersJson = gson.toJson(speakersSnapshot)
                val instructorsJson = gson.toJson(instructorsSnapshot)


                if (speakersJson == null || instructorsJson == null) {
                    return
                }

                val speakersData = ArrayList(gson.fromJson(speakersJson, Array<Speaker>::class.java).toList())
                val instructorsData = gson.fromJson(instructorsJson, Array<Speaker>::class.java).toList()

                for (instructor in instructorsData) {
                    // Minor hack to avoid data-duplication: in ME-db, instructors have different IDs (i.e. we have 2 "copies" of Wei Meng or Svetlana. With 2XX and 3XX ids each)
                    if (!speakersData.any { speaker -> speaker.name == instructor.name }) {
                        speakersData.add(instructor)
                    }
                }

                speakers?.value = speakersData.sortedBy { speaker -> speaker.name }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadSpeakers:onCancelled ${databaseError.toException()}")
            }
        }

        database?.addValueEventListener(speakersListener)
    }

}