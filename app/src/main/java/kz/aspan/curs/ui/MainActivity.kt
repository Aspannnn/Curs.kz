package kz.aspan.curs.ui

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import kz.aspan.curs.R
import kz.aspan.curs.adapter.ViewPagerAdapter
import kz.aspan.curs.databinding.ActivityMainBinding
import kz.aspan.curs.model.Syllabus
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var downloadId: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setBackgroundDrawableResource(R.drawable.toolbar_background)

        val syllabus = getDataFromJson(applicationContext)
        binding.academicYearTv.text = syllabus.academicYear
        binding.downloadFile.setOnClickListener {
            downloadFile(syllabus.documentURL)
        }

        val viewPagerAdapter =
            ViewPagerAdapter(syllabus.semesters, supportFragmentManager, lifecycle)
        binding.pager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.pager, true, true) { tab, position ->
            tab.text =
                "${resources.getString(R.string.semester)} ${(syllabus.semesters[position].number)}"
        }.attach()


        registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

    }

    private val br = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val id = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id == downloadId) {
                Toast.makeText(applicationContext, "Download Completed", Toast.LENGTH_LONG).show()
            }
        }
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

    private fun downloadFile(url: String) {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Document")
            .setDescription("Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI or
                        DownloadManager.Request.NETWORK_MOBILE
            )
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Document")
            .setMimeType("*/*")

        val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadId = dm.enqueue(request)
    }


}