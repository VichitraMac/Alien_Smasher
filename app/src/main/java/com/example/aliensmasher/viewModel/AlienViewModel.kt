package com.example.aliensmasher.viewModel


import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aliensmasher.R
import com.example.aliensmasher.models.AlienModel
import com.example.aliensmasher.utils.Resource
import com.example.aliensmasher.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random

class AlienViewModel : ViewModel() {
    var itemCount = 36
    var timerMutableLiveDate = MutableLiveData<Resource<String>>()
    var aliens = MutableStateFlow(prepareAlienList(itemCount))
    var score = MutableStateFlow(0)
    var musicIconState = MutableStateFlow(R.drawable.icon_music)
    val columnSize by lazy {
        6
//        sqrt(16.0).roundToInt()
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

    var aliensImage = arrayListOf<Int>(R.drawable.green_alien_game_content,
        R.drawable.blue_alien_game_content, R.drawable.violet_alien_game_content,
        R.drawable.purple_three_eye_alien_game_content, R.drawable.sky_blue_alien_game_content,
        R.drawable.red_alien_game_game_content, R.drawable.purple_alien_game_content,
        R.drawable.yellow_alien_game_content)

    private fun prepareAlienList(count: Int): ArrayList<AlienModel> {
        val list = arrayListOf<AlienModel>()
        list.add(AlienModel(id = 0, point = 100, image = R.drawable.sky_blue_alien_game_content))
        list.add(AlienModel(id = 1, point = 100, image = R.drawable.sky_blue_alien_game_content))
        list.add(AlienModel(id = 2, point = 100, image = R.drawable.purple_three_eye_alien_game_content))
        list.add(AlienModel(id = 3, point = 100, image = R.drawable.blue_alien_game_content))
        list.add(AlienModel(id = 4, point = 100, image = R.drawable.red_alien_game_game_content))
        list.add(AlienModel(id = 5, point = 100, image = R.drawable.sky_blue_alien_game_content))
        list.add(AlienModel(id = 6, point = 100, image = R.drawable.yellow_alien_game_content))
        list.add(AlienModel(id = 7, point = 100, image = R.drawable.violet_alien_game_content))
        list.add(AlienModel(id = 8, point = 100, image = R.drawable.purple_three_eye_alien_game_content))
        list.add(AlienModel(id = 9, point = 100, image = R.drawable.red_alien_game_game_content))
        list.add(AlienModel(id = 10, point = 100, image = R.drawable.purple_alien_game_content))
        list.add(AlienModel(id = 11, point = 100, image = R.drawable.sky_blue_alien_game_content))
        list.add(AlienModel(id = 12, point = 100, image = R.drawable.yellow_alien_game_content))
        list.add(AlienModel(id = 13, point = 100, image = R.drawable.green_alien_game_content))
        list.add(AlienModel(id = 14, point = 100, image = R.drawable.sky_blue_alien_game_content))
        list.add(AlienModel(id = 15, point = 100, image = R.drawable.sky_blue_alien_game_content))
        list.add(AlienModel(id = 16, point = 100, image = R.drawable.sky_blue_alien_game_content))
        list.add(AlienModel(id = 17, point = 100, image = R.drawable.sky_blue_alien_game_content))
        list.add(AlienModel(id = 18, point = 100, image = R.drawable.purple_three_eye_alien_game_content))
        list.add(AlienModel(id = 19, point = 100, image = R.drawable.blue_alien_game_content))
        list.add(AlienModel(id = 20, point = 100, image = R.drawable.red_alien_game_game_content))
        list.add(AlienModel(id = 21, point = 100, image = R.drawable.sky_blue_alien_game_content))
        list.add(AlienModel(id = 22, point = 100, image = R.drawable.yellow_alien_game_content))
        list.add(AlienModel(id = 23, point = 100, image = R.drawable.violet_alien_game_content))
        list.add(AlienModel(id = 24, point = 100, image = R.drawable.purple_three_eye_alien_game_content))
        list.add(AlienModel(id = 25, point = 100, image = R.drawable.red_alien_game_game_content))
        list.add(AlienModel(id = 26, point = 100, image = R.drawable.purple_alien_game_content))
        list.add(AlienModel(id = 27, point = 100, image = R.drawable.sky_blue_alien_game_content))
        list.add(AlienModel(id = 28, point = 100, image = R.drawable.yellow_alien_game_content))
        list.add(AlienModel(id = 29, point = 100, image = R.drawable.green_alien_game_content))
        list.add(AlienModel(id = 30, point = 100, image = R.drawable.sky_blue_alien_game_content))
        list.add(AlienModel(id = 31, point = 1, image = R.drawable.sky_blue_alien_game_content))
        list.add(AlienModel(id = 32, point = 1, image = R.drawable.purple_alien_game_content))
        list.add(AlienModel(id = 33, point = 1, image = R.drawable.sky_blue_alien_game_content))
        list.add(AlienModel(id = 34, point = 1, image = R.drawable.yellow_alien_game_content))
        list.add(AlienModel(id = 35, point = 1, image = R.drawable.green_alien_game_content))

       /* for (i in 1..count) {
            var myRandomValues = Random.nextInt(0, 7)
            list.add(
                AlienModel(
                    id = i, point = 1, image = aliensImage[myRandomValues]
                )
            )
        }*/
        return list
    }

    fun onAlienClicked(pos: Int) {
        score.value++
        Log.e("fun", "onAlienClicked:$score", )
        aliens.value[pos].isVisible.value = false
    }


    fun getResult(): String {
       return if (score.value == itemCount) {
            "Won"
       } else {
            "lose"
       }
    }

    fun restart() {
        initTimer()
        score.value = 0
        aliens = MutableStateFlow(prepareAlienList(itemCount))
    }

    fun getFormattedStopWatch(ms: Long): String {
        var milliseconds = ms
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

        return "${if (hours < 10) "0" else ""}$hours:" +
                "${if (minutes < 10) "0" else ""}$minutes:" +
                "${if (seconds < 10) "0" else ""}$seconds"
    }

    fun changeMusicIcon(icon: Int){
        musicIconState.value = icon
    }
}


