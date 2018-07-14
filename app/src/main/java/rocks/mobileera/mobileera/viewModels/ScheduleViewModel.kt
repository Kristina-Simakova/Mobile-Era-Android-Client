package rocks.mobileera.mobileera.viewModels

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.database.*
import rocks.mobileera.mobileera.model.Day


class ScheduleViewModel : ViewModel() {

    private var database: DatabaseReference? = null

    init {
        database = FirebaseDatabase.getInstance().reference
        database?.keepSynced(true)
    }

    private var days: MutableLiveData<List<Day>>? = null
    fun getDays(): MutableLiveData<List<Day>>? {
        if (days == null) {
            days = MutableLiveData()
            loadSchedule()
        }

        return days
    }

    private fun loadSchedule() {

        val scheduleListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val speakersSnapshot = snapshot.child("speakers").value
                val instructorsSnapshot = snapshot.child("instructors").value
                val sessionsSnapshot = snapshot.child("sessions").value
                val workshopsSnapshot = snapshot.child("workshops").value
                val scheduleSnapshot = snapshot.child("schedule").value

                if (speakersSnapshot == null || instructorsSnapshot == null || sessionsSnapshot == null ||
                        workshopsSnapshot == null || scheduleSnapshot == null) {
                    return
                }

                // TODO: complete loadSchedule method
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadSchedule:onCancelled ${databaseError.toException()}")
            }
        }

        database?.addValueEventListener(scheduleListener)
    }

}
