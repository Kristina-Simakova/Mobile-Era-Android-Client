package rocks.mobileera.mobileera.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rocks.mobileera.mobileera.R
import rocks.mobileera.mobileera.model.Day


class DayFragment: Fragment() {

    private var title: Int = R.string.day1
    private var isWorkshopsDay: Boolean = false

    var day: Day? = null
        set(value) {
            field = value
            print("Data has been updated")
            // TODO: reload recyclerView
        }

    companion object {
        internal fun newInstance(title: Int, day: Day?, isWorkshopsDay: Boolean): DayFragment {
            val fragment = DayFragment()
            fragment.title = title
            fragment.day = day
            fragment.isWorkshopsDay = isWorkshopsDay
            return fragment
        }
    }

    override fun toString(): String {
        return getString(title)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false)
    }
}