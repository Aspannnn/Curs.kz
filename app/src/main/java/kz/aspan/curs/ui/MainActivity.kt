package kz.aspan.curs.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import kz.aspan.curs.R
import kz.aspan.curs.adapter.ViewPagerAdapter
import kz.aspan.curs.databinding.ActivityMainBinding
import kz.aspan.curs.model.Syllabus
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setBackgroundDrawableResource(R.drawable.toolbar_background)

        val syllabus = getDataFromJson(applicationContext)
        binding.academicYearTv.text = syllabus.academicYear

        val viewPagerAdapter =
            ViewPagerAdapter(syllabus.semesters, supportFragmentManager, lifecycle)
        binding.pager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.pager, true, true) { tab, position ->
            tab.text = "Semester ${(syllabus.semesters[position].number)}"
        }.attach()

    }

    private fun getDataFromJson(context: Context): Syllabus {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("result.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
        return Gson().fromJson(
            jsonString,
            Syllabus::class.java
        )
    }

    //TODO 1 tilderdi kosu kerek, 2 download kosu kerek
}