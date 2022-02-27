package kz.aspan.curs.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import kz.aspan.curs.common.Constants.ARG_OBJECT
import kz.aspan.curs.model.Semester
import kz.aspan.curs.ui.SemesterFragment

class ViewPagerAdapter(
    private val list: List<Semester>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        val fragment = SemesterFragment()
        fragment.arguments = Bundle().apply {
            putSerializable(ARG_OBJECT, list[position])
        }
        return fragment
    }
}