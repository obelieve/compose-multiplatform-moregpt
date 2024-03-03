package co.moregpt.ai.ui.feedback

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch
import kotlin.concurrent.Volatile

/**
 *   @desc content
 *   Created by zxy
 **/
class FeedbackViewModel: ViewModel() {
    val toastLV: MutableLiveData<String?> = MutableLiveData(null)
    val errorHintLV: MutableLiveData<String> = MutableLiveData("")
    val successLV: MutableLiveData<Boolean> = MutableLiveData(false)
    val loadingLV: MutableLiveData<Boolean> = MutableLiveData(false)
    val maxCount = 2

    @Volatile
    var count = 0

    fun feedback(text: String) {
        if(text.isEmpty()){
            toastLV.value = "反馈内容为空"
            return
        }
        if(count>maxCount){
            toastLV.value = "\uD83D\uDEAB请求太多，请稍后再试"
            return
        }
        viewModelScope.launch {
            loadingLV.value = true
            loadingLV.value = false
        }
    }

    fun refresh() {
        successLV.value = false
    }
}