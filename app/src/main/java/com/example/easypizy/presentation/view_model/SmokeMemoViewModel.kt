package com.example.easypizy.presentation.view_model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.easypizy.data.data_source.local.entity.SmokeMemoEntity
import com.example.easypizy.data.repository.SmokeMemoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate

class SmokeMemoViewModel(private val repository: SmokeMemoRepository) : ViewModel() {
    var smokeMemos: LiveData<List<SmokeMemoEntity>> = repository.smokeMemos.asLiveData()
        private set

    /**
     * 날짜 [date]에 [count]만큼의 담배 개비 수를 추가
     * @param date 담배 개비 수를 추가할 날짜
     * @param count 추가할 담배의 개비 수*/
    fun plusCigarette(date: LocalDate, count: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val memo = repository.getSmokeMemoEntity(date)
            Log.d(
                "SmokeMemoViewModel",
                memo.toString()
            )
            if (memo == null) {
                repository.insertSmokeMemoEntity(
                    SmokeMemoEntity(
                        id = date.toString(),
                        date = date,
                        cigaretteCount = count
                    )
                )
            } else {
                val newMemo = memo.copy(cigaretteCount = memo.cigaretteCount + count)
                repository.updateSmokeMemoEntity(newMemo)
            }
        }
    }

    /**
     * 날짜 [date]의 담배 개비 수를 [count]로 수정
     * @param date 담배 개비 수를 수정할 날짜
     * @param count 수정할 담배의 개비 수*/
    fun updateCigarette(date: LocalDate, count: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val memo = repository.getSmokeMemoEntity(date)
            Log.d(
                "SmokeMemoViewModel",
                memo.toString()
            )
            if (memo == null) {
                repository.insertSmokeMemoEntity(
                    SmokeMemoEntity(
                        id = date.toString(),
                        date = date,
                        cigaretteCount = count
                    )
                )
            } else {
                val newMemo = memo.copy(cigaretteCount = count)
                repository.updateSmokeMemoEntity(newMemo)
            }
        }
    }

    /**
     * 날짜 [date]에 핀 담배 개비 수를 반환
     * @param date 몇 개비 폈는지 알고 싶은 날짜*/
    fun getCigaretteCount(date: LocalDate): Int {
        var memo = smokeMemos.value?.find { it.date == date }
        if (memo == null) {
            CoroutineScope(Dispatchers.IO).launch {
                val newMemo = SmokeMemoEntity(
                    id = date.toString(),
                    date = date,
                    cigaretteCount = 0
                )
                repository.insertSmokeMemoEntity(
                    newMemo
                )
                memo = newMemo
            }
        }
        return memo?.cigaretteCount ?: 0
    }

    /**
     * 날짜 [date]-6일부터 [date]까지 하루에 핀 개비 수를 ArrayList<Int>의 형태로 반환
     * @param date 기준 날짜(보통은 금일)*/
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCigaretteCountPerDay(date: LocalDate): ArrayList<Int> {
        val counts: ArrayList<Int> = ArrayList()
        for (i in 6 downTo 0) {
            val memo = smokeMemos.value?.find { it.date == date.minusDays(i.toLong()) }
            counts.add(memo?.cigaretteCount ?: 0)
        }
        return counts
    }

    /**
     * 날짜 [date]-3주부터 [date]까지 1주에 핀 개비 수를 ArrayList<Int>의 형태로 반환
     * @param date 기준 날짜(보통은 금일)*/
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCigaretteCountPerWeek(date: LocalDate): ArrayList<Int> {
        val counts: ArrayList<Int> = ArrayList()
        for (i in 3 downTo 0) {
            // 해당 주의 마지막 날짜
            val lastDateOfThatWeek = date.minusWeeks(i.toLong())
            // 해당 주의 day count 리스트
            val dayCountListOfThatWeek = getCigaretteCountPerDay(lastDateOfThatWeek)
            var thatWeekCount: Int = 0
            for (count in dayCountListOfThatWeek) {
                thatWeekCount += count
            }
            counts.add(thatWeekCount)
        }
        return counts
    }

    /**
     * [date]의 연도에 있는 1~12월의 개비 수를 반환
     * 0번 요소는 1월에 핀 개비 수, 11번 요소는 12월에 핀 개비 수
     * @param date 기준 날짜(보통은 금일)*/
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCigaretteCountPerMonth(date: LocalDate): ArrayList<Int> {
        val counts: ArrayList<Int> = ArrayList()
        val year = date.year
        for (i in 1..12) {
            val dateForMonth = LocalDate.of(year, i, 1)
            val lengthOfMonth = dateForMonth.lengthOfMonth()
            var monthCount = 0
            for (day in 1..lengthOfMonth) {
                val memo = smokeMemos.value?.find { it.date == LocalDate.of(year, i, day) }
                monthCount += memo?.cigaretteCount ?: 0
            }
            counts.add(monthCount)
        }
        return counts
    }

    /**
     * 총 흡연기간을 반환하는 함수*/
    @RequiresApi(Build.VERSION_CODES.O)
    fun getTotalSmokePeriod(): Int {
        var firstDay = LocalDate.now()
        if (smokeMemos.value.isNullOrEmpty()) {
            return 0
        }
        for (memo in smokeMemos.value!!) {
            if (memo.date.isBefore(firstDay)) {
                firstDay = memo.date
            }
        }
        val difference = Duration.between(firstDay.atStartOfDay(), LocalDate.now().atStartOfDay())
        return difference.toDays().toInt()
    }

    /**
     * 핀 모든 담배 개비 수 반환*/
    fun getTotalCigaretteCount(): Int {
        if (smokeMemos.value.isNullOrEmpty()) {
            return 0
        }
        var count = 0
        for (memo in smokeMemos.value!!) {
            count += memo.cigaretteCount
        }
        return count
    }

    /**
     * 담배 사는데 쓴 모든 돈 반환(한 갑에 4500원이라 가정)*/
    fun getTotalSpendMoney(): Int {
        return getTotalCigaretteCount() * 4500 / 20
    }

    /**
     * 지금까지 마신 타르 양 반환(한 개비에 1mg이라 가정)
     */
    fun getTotalTar(): Int {
        return getTotalCigaretteCount()
    }

    /**
     * 모든 흡연 메모 삭제하는 함수*/
    fun deleteAllMemos() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteAllMemoEntities()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun resetMemos() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteAllMemoEntities()
            val today = LocalDate.now()
            val counts = arrayOf(13,17,16,10,14,16,12)
            for (i in 0..6) {
                val date = today.minusDays(i.toLong())
                repository.insertSmokeMemoEntity(
                    SmokeMemoEntity(
                        id = date.toString(),
                        date = date,
                        cigaretteCount = counts[i]
                    )
                )
            }
        }
    }
}

class SmokeMemoViewModelFactory(private val repository: SmokeMemoRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SmokeMemoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SmokeMemoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}