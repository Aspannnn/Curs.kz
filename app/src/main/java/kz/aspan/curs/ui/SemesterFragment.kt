package kz.aspan.curs.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.content.ContextCompat
import com.google.android.material.textview.MaterialTextView
import kz.aspan.curs.R
import kz.aspan.curs.common.Constants.ARG_OBJECT
import kz.aspan.curs.common.afterLayoutConfiguration
import kz.aspan.curs.common.dpToPx
import kz.aspan.curs.common.setMargins
import kz.aspan.curs.databinding.FragmentSemesterBinding
import kz.aspan.curs.model.Discipline
import kz.aspan.curs.model.Lesson
import kz.aspan.curs.model.Semester
import java.util.*

class SemesterFragment : Fragment(R.layout.fragment_semester) {
    private var _binding: FragmentSemesterBinding? = null
    private val binding: FragmentSemesterBinding
        get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSemesterBinding.bind(view)


        tableHeader(binding.CourseTitleLL, binding.tableTl)
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val semester = getSerializable(ARG_OBJECT) as Semester
            fillTable(semester.disciplines, binding.tableTl)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }


    private fun createTextView(text: String): MaterialTextView {
        val textView = MaterialTextView(requireContext())
        textView.text = text
        textView.textSize = 14f
        textView.gravity = Gravity.CENTER
        return textView
    }

    private fun createTextView(text: SpannableString): MaterialTextView {
        val textView = MaterialTextView(requireContext())
        textView.text = text
        textView.textSize = 14f
        textView.gravity = Gravity.CENTER
        return textView
    }

    private fun fillTable(list: List<Discipline>, table: TableLayout) {
        val appLanguage = Locale.getDefault().language
        for (discipline in list) {
            fillRow(binding.CourseTitleLL, discipline, appLanguage, table)
        }
    }

    private fun spanHour(lesson: Lesson): SpannableString {
        val hours = lesson.hours
        val realHours = lesson.realHours
        val fRealHours = if (hours.toInt() >= realHours.toInt()) {
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.zhasyl))
        } else {
            ForegroundColorSpan(Color.RED)
        }
        val fHours = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.zhasyl))

        val spannableHours = SpannableString("$hours/$realHours")

        spannableHours.setSpan(fHours, 0, hours.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableHours.setSpan(
            fRealHours,
            hours.length + 1,
            spannableHours.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableHours
    }

    private fun fillRow(
        linearLayout: LinearLayout,
        discipline: Discipline,
        appLanguage: String,
        table: TableLayout
    ) {
        //curseTitle
        val curseTv = when (appLanguage) {
            "kk" -> createTextView(discipline.disciplineName.nameKk)
            "en" -> createTextView(discipline.disciplineName.nameEn)
            "ru" -> createTextView(discipline.disciplineName.nameRu)
            else -> {
                createTextView(discipline.disciplineName.nameEn)
            }
        }
        curseTv.gravity = Gravity.START

        linearLayout.addView(curseTv)
        curseTv.setMargins(topMarginDp = 6, bottomMarginDp = 6)
        curseTv.afterLayoutConfiguration {
            curseHour(discipline, table, curseTv)
        }
    }

    private fun curseHour(discipline: Discipline, table: TableLayout, textView: MaterialTextView) {
        //curseHour
        val tableRow = TableRow(requireContext())
        var lecture = createTextView("")
        var seminar = createTextView("")
        var lab = createTextView("")

        for (lesson in discipline.lesson) {
            when (lesson.lessonTypeId) {
                "1" -> lecture = createTextView(spanHour(lesson))
                "2" -> seminar = createTextView(spanHour(lesson))
                "3" -> lab = createTextView(spanHour(lesson))
            }

        }

        val layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            textView.measuredHeight + 12.dpToPx(requireContext()),
            1f
        )

        tableRow.addView(lecture, layoutParams)
        tableRow.addView(seminar, layoutParams)
        tableRow.addView(lab, layoutParams)
        table.addView(tableRow)

    }

    private fun tableHeader(curses: LinearLayout, table: TableLayout) {
        val curse = createTextView(resources.getString(R.string.course_title))
        curse.gravity = Gravity.START
        curses.addView(curse)

        curse.afterLayoutConfiguration {
            title(table, curse)
        }


    }

    private fun title(table: TableLayout, textView: MaterialTextView) {
        val tableRow = TableRow(requireContext())

        val layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT, textView.measuredHeight, 1f
        )

        val lecture = createTextView(resources.getString(R.string.lecture))
        val seminar = createTextView(resources.getString(R.string.seminar))
        val lab = createTextView(resources.getString(R.string.lab))

        tableRow.addView(lecture, layoutParams)
        tableRow.addView(seminar, layoutParams)
        tableRow.addView(lab, layoutParams)
        table.addView(tableRow)
    }


}
