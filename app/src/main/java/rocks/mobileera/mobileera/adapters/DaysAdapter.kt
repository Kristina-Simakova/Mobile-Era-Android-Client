package rocks.mobileera.mobileera.adapters
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class DaysAdapter(fragmentManager: FragmentManager?, private val fragments: List<Fragment>) : FragmentPagerAdapter(fragmentManager) {

    override fun getPageTitle(position: Int): CharSequence? {
        return fragments[position].toString()
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

}