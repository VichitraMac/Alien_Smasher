package com.example.aliensmasher.viewModel


import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aliensmasher.models.AlienModel
import com.example.aliensmasher.utils.Resource
import com.example.aliensmasher.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt
import kotlin.math.sqrt

class AlienViewModel : ViewModel() {
    var itemCount = 16
    var timerMutableLiveDate = MutableLiveData<Resource<String>>()
    var aliens = MutableStateFlow(prepareAlienList(itemCount))
    var score: Int = 0
    val columnSize by lazy {
        sqrt(16.0).roundToInt()
    }
    private var timer: CountDownTimer? = null

    init {
        initTimer()
    }

    private fun initTimer() = viewModelScope.launch(Dispatchers.Main) {
        timer?.cancel()
        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.e("fun", "onTick: ${timerMutableLiveDate.value?.data}")
                this@AlienViewModel.timerMutableLiveDate.postValue(
                    Resource(
                        Status.LOADING, getFormattedStopWatch(millisUntilFinished),""
                    )
                )
            }

            override fun onFinish() {
                this@AlienViewModel.timerMutableLiveDate.postValue(
                    Resource(
                        Status.SUCCESS, "", getResult()
                    )
                )
            }
        }
        timer?.start()
    }

    private fun prepareAlienList(count: Int): ArrayList<AlienModel> {
        val list = arrayListOf<AlienModel>()
        for (i in 1..count) {
            list.add(
                AlienModel(
                    id = i, point = 1
                )
            )
        }
        return list
    }

    fun onAlienClicked(pos: Int) {
        score++
        Log.e("fun", "onAlienClicked:$score", )
        aliens.value[pos].isVisible.value = false
    }


    fun getResult(): String {
       return if (score == itemCount) {
            "Won"
        } else {
            "lose"
        }
    }

    fun restart() {
        initTimer()
        score = 0
        aliens = MutableStateFlow(prepareAlienList(itemCount))
    }

    fun getFormattedStopWatch(ms: Long): String {
        var milliseconds = ms
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

        return "${if (minutes < 10) "0" else ""}$minutes:" +
                "${if (seconds < 10) "0" else ""}$seconds"
    }
}


