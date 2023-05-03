package com.example.easypizy.presentation


import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


class RecordActivity : AppCompatActivity() {

    private var barChart: BarChart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.easypizy.R.layout.activity_record)

        val chart: ArrayList<BarEntry> = ArrayList()


        barChart = findViewById<View>(com.example.easypizy.R.id.chart) as BarChart

        val barData = BarData()


        chart.add(BarEntry(1f, 1f))
        chart.add(BarEntry(2f, 4f))
        chart.add(BarEntry(3f, 3f))
        chart.add(BarEntry(4f, 5f))
        chart.add(BarEntry(5f, 2f))
        chart.add(BarEntry(6f, 7f))
        chart.add(BarEntry(7f, 8f))


        val barDataSet =
            BarDataSet(chart, "담배 핀 개수")


        barDataSet.color = Color.BLUE // 색깔 설정

        barDataSet.valueTextSize=16f;  // 글씨 크기

        barData.addDataSet(barDataSet)


        barChart!!.setData(barData)

        barChart!!.invalidate() // 차트 업데이트

        barChart!!.setTouchEnabled(false) // 차트 터치 불가능


    }
}