package rocks.mobileera.mobileera.viewModels

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.google.firebase.database.*
import rocks.mobileera.mobileera.model.Day
import rocks.mobileera.mobileera.model.Speaker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import rocks.mobileera.mobileera.model.Session
import rocks.mobileera.mobileera.model.Timeslot
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


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

    var showOnlyFavorite: MutableLiveData<Boolean> = MutableLiveData()
    var selectedTags: MutableLiveData<List<String>> = MutableLiveData()
    var allTags: ArrayList<String> = ArrayList()

    private fun loadSchedule() {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.US)

        val scheduleListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val gson = Gson()

                val speakersSnapshot = snapshot.child("speakers").value
                val instructorsSnapshot = snapshot.child("instructors").value
                val sessionsSnapshot = snapshot.child("sessions").value
                val workshopsSnapshot = snapshot.child("workshops").value
                val scheduleSnapshot = snapshot.child("schedule").value

                if (speakersSnapshot == null || instructorsSnapshot == null || sessionsSnapshot == null ||
                        workshopsSnapshot == null || scheduleSnapshot == null) {
                    return
                }

                val speakersJson = gson.toJson(speakersSnapshot)
                val instructorsJson = gson.toJson(instructorsSnapshot)
                val sessionsJson = gson.toJson(sessionsSnapshot)
                val workshopsJson = gson.toJson(workshopsSnapshot)
                val scheduleJson = gson.toJson(scheduleSnapshot)

                if (speakersJson == null || instructorsJson == null || sessionsJson == null ||
                        workshopsJson == null || scheduleJson == null) {
                    return
                }

                val speakers = gson.fromJson(speakersJson, Array<Speaker>::class.java).toList()
                val instructors = gson.fromJson(instructorsJson, Array<Speaker>::class.java).toList()
                val sessionsDictionary: Map<String, Session> = gson.fromJson(sessionsJson, object : TypeToken<Map<String, Session>>() {}.type)
                val workshopsDictionary: Map<String, Session> = gson.fromJson(workshopsJson, object : TypeToken<Map<String, Session>>() {}.type)
                val schedule = ArrayList(gson.fromJson(scheduleJson, Array<Day>::class.java).toList())


                val sessions = sessionsDictionary.values + workshopsDictionary.values
                val allTagsList: HashSet<String> = HashSet()
                for (session in sessions) {
                    val joinedSpeakerList: ArrayList<Speaker> = ArrayList()

                    session.speakers?.forEach { id ->
                        val speakersPool = if (session.isWorkshop()) instructors else speakers
                        speakersPool.firstOrNull {speaker ->
                                speaker.id == id
                        }?.let { speaker ->
                            joinedSpeakerList.add(speaker)
                        }

                    }

                    session.speakersList = joinedSpeakerList
                    session.tags?.forEach {tag ->
                        allTagsList.add(tag)
                    }
                }

                for (day in schedule) {
                    day.timeslots?.forEach { timeslot ->
                        val joinedSessionsList: ArrayList<Session> = ArrayList()

                        timeslot.sessions?.map { session -> session.firstOrNull() }?.forEach {id ->
                            sessions.firstOrNull { talk ->
                                talk.id == id
                            }?.let { joinedSession->
                                joinedSession.startDate = dateFormatter.parse(day.date + "T" + timeslot.startTime)
                                joinedSession.endDate = dateFormatter.parse(day.date + "T" + timeslot.endTime)
                                joinedSessionsList.add(joinedSession)
                            }
                        }

                        timeslot.sessionsList = joinedSessionsList
                    }
                }


                // Populating a schedule for the workshops
                val workshopDay = Day()
                val timeslots = ArrayList<Timeslot>()
                timeslots.add(Timeslot("09:00", "16:00", ArrayList(workshopsDictionary.values)))
                workshopDay.timeslots = timeslots
                schedule.add(0, workshopDay)

                allTags = ArrayList(allTagsList)
                days?.value = schedule
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadSchedule:onCancelled ${databaseError.toException()}")
            }
        }

        database?.addValueEventListener(scheduleListener)
    }

}
