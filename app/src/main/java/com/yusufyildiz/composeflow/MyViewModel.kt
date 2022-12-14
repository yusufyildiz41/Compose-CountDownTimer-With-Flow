package com.yusufyildiz.composeflow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    val countDownTimerFlow = flow<Int> {
        val countDownFrom = 10
        var counter = countDownFrom
        emit(countDownFrom)
        while (counter > 0){
            delay(1000)
            counter--
            emit(counter)
        }
    }

    init {
        collectInViewModel()
    }

    private fun collectInViewModel()
    {
        viewModelScope.launch {
            countDownTimerFlow
                .filter {
                    it % 3 == 0
                }
                .map {
                    it + it
                }
                .onEach {

                }
                .collect {
                println("counter is : ${it}")
            }


        }

        /*
        countDownTimerFlow.onEach {
            println(it)
        }.launchIn(viewModelScope)

         */
    }

    //LiveData comparisons



    private val _liveData = MutableLiveData<String>("KotlinLiveData") // ilk degerin verilmesi zorunlu degildir
    val liveData : LiveData<String> = _liveData

    fun changeLiveDataValue()
    {
        _liveData.value = "Live Data"
    }

    private val _stateFlow = MutableStateFlow("KotlinStateFlow") // ilk degerin verilmesi zorunludur
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun changeStateFlowValue()
    {
        _stateFlow.value = "State Flow"
    }

    fun changeSharedFlowValue()
    {
        viewModelScope.launch {
            _sharedFlow.emit("Shared Flow")
        }
    }

}