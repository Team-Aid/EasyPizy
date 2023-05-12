package com.example.easypizy.presentation


import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.easypizy.SmokePlaceApplication
import com.example.easypizy.presentation.view_model.SmokeMemoViewModel
import com.example.easypizy.presentation.view_model.SmokeMemoViewModelFactory
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


class RecordActivity : AppCompatActivity() {
    private val memoViewModel: SmokeMemoViewModel by viewModels {
        SmokeMemoViewModelFactory((application as SmokePlaceApplication).smokeMemoRepository)
    }
    private var barChart: BarChart? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.easypizy.R.layout.activity_record)

        val chart: ArrayList<BarEntry> = ArrayList()


        barChart = findViewById<View>(com.example.easypizy.R.id.chart) as BarChart

        val barData = BarData()


        val cigaretteCounts = memoViewModel.getCigaretteCountPerDay(LocalDate.now())
        for (i in 0..6){
            chart.add(BarEntry(i.toFloat(), cigaretteCounts[i].toFloat()))
        }


        val barDataSet =
            BarDataSet(chart, "담배 핀 개수")


        barDataSet.color = Color.BLUE // 색깔 설정

        barDataSet.valueTextSize=16f  // 글씨 크기

        barData.addDataSet(barDataSet)


        barChart!!.data = barData

        barChart!!.invalidate() // 차트 업데이트

        barChart!!.setTouchEnabled(false) // 차트 터치 불가능

        val leftAxis: YAxis = barChart!!.axisLeft
        // 좌측 선 설정 (default = true)
        leftAxis.setDrawAxisLine(false)
        leftAxis.setDrawLabels(false)
        leftAxis.setDrawGridLines(false)
        leftAxis.axisMinimum = 0f
        leftAxis.axisMaximum = 25f

        val rightAxis: YAxis = barChart!!.axisRight
        // 우측 선 설정 (default = true)
        rightAxis.setDrawAxisLine(false)
        rightAxis.setDrawLabels(false)
        rightAxis.setDrawGridLines(false)

        val description = Description()
        description.isEnabled = false
        barChart!!.description = description

        // 바텀 좌표 값
        val xAxis: XAxis = barChart!!.xAxis
        // x축 선 설정 (default = true)
        xAxis.setDrawAxisLine(false)
        // 격자선 설정 (default = true)
        xAxis.setDrawGridLines(false)
        val labels = kotlin.collections.ArrayList<String>()
        val today = LocalDate.now()
        for(i in 6 downTo 0){
            val thatDay = today.minusDays(i.toLong())
            labels.add("${thatDay.month.value}/${thatDay.dayOfMonth}")
        }
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        memoViewModel.smokeMemos.observe(this){
            val todayCiga = memoViewModel.getCigaretteCount(LocalDate.now())
            val yesterdayCiga = memoViewModel.getCigaretteCount(LocalDate.now().minusDays(1L))
            findViewById<TextView>(com.example.easypizy.R.id.today_num).text = todayCiga.toString()
            findViewById<TextView>(com.example.easypizy.R.id.yesterday_num).text = yesterdayCiga.toString()
            val sign = when(todayCiga-yesterdayCiga){
                in 1..Int.MAX_VALUE -> "+"
                else -> ""
            }
            findViewById<TextView>(com.example.easypizy.R.id.number).text = "${sign}${todayCiga-yesterdayCiga}"

            val cigaretteCounts2 = memoViewModel.getCigaretteCountPerDay(LocalDate.now())
            chart.clear()
//            var biggest = 2
            for (i in 0..6){
                chart.add(BarEntry(i.toFloat(), cigaretteCounts2[i].toFloat()))
//                if(biggest < cigaretteCounts2[i]){
//                    biggest = cigaretteCounts2[i]
//                }
            }

//            Log.d("asdd", biggest.toString())
//            leftAxis.axisMaximum = biggest.toFloat()
            barChart!!.invalidate()
        }

        findViewById<Button>(com.example.easypizy.R.id.plusCigaButton).setOnClickListener {
            memoViewModel.plusCigarette(LocalDate.now(), 1)
        }
        findViewById<Button>(com.example.easypizy.R.id.plusCigaChooseDayButton).setOnClickListener {
            Locale.setDefault(Locale.KOREA)
            val plusCigaDialog = PlusCigaDialog()
            plusCigaDialog.show(supportFragmentManager, "")
        }
    }
}