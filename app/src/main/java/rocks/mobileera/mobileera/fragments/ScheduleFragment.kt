package rocks.mobileera.mobileera.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_schedule.*

import rocks.mobileera.mobileera.R
import rocks.mobileera.mobileera.adapters.DaysAdapter
import rocks.mobileera.mobileera.model.Day
import rocks.mobileera.mobileera.viewModels.ScheduleViewModel

class ScheduleFragment : Fragment() {

    companion object {
        fun newInstance() = ScheduleFragment()
    }

    private val daysFragments = ArrayList<DayFragment>(3)
    private lateinit var viewModel: ScheduleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daysFragments.add(DayFragment.newInstance(activity?.applicationContext?.getString(R.string.workshops), null, true))
        daysFragments.add(DayFragment.newInstance(activity?.applicationContext?.getString(R.string.day1), null, false))
        daysFragments.add(DayFragment.newInstance(activity?.applicationContext?.getString(R.string.day2), null, false))
        daysViewPager.adapter = DaysAdapter(childFragmentManager, daysFragments)
        daysViewPager.currentItem = 1
        daysViewPager.offscreenPageLimit = 2
        daysTabLayout.setupWithViewPager(daysViewPager)

        viewModel = ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
        viewModel.getDays()?.observe(this, Observer<List<Day>> {days ->
            daysFragments[0].day = days?.getOrNull(0)
            daysFragments[1].day = days?.getOrNull(1)
            daysFragments[2].day = days?.getOrNull(2)
        })
    }
}
