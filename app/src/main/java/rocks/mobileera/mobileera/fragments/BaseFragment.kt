package rocks.mobileera.mobileera.fragments

import android.support.v4.app.Fragment
import android.view.MenuItem
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import rocks.mobileera.mobileera.R

abstract class BaseFragment : Fragment() {

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.navigation_info -> {
                activity?.let {context ->
                    MaterialDialog.Builder(context)
                            .title(R.string.information)
                            .iconRes(R.drawable.ic_info_blue)
                            .itemsCallback { _, _, index, _ ->

                            }
                            .onPositive { dialog, _ ->
                                dialog.dismiss()
                            }
                            .positiveText(R.string.filter_ok)
                            .autoDismiss(false)
                            .positiveColorRes(R.color.colorPrimary)
                            .customView(R.layout.dialogue_info, true)
                            .theme(Theme.LIGHT)
                            .show()
                }
            }
        }

        return super.onOptionsItemSelected(item)

    }


}