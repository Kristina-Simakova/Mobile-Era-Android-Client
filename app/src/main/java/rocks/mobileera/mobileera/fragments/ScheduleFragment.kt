package rocks.mobileera.mobileera.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
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

    lateinit var viewModel: ScheduleViewModel
    private lateinit var adapter: DaysAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DaysAdapter(childFragmentManager, activity?.applicationContext)
        daysViewPager.adapter = adapter
        daysViewPager.currentItem = 1
        daysViewPager.offscreenPageLimit = 2
        daysTabLayout.setupWithViewPager(daysViewPager)

        progressCircular.visibility = View.VISIBLE

        viewModel = ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
        viewModel.getDays()?.observe(this, Observer<List<Day>> {days ->
            progressCircular.visibility = View.GONE
        })
    }
}
