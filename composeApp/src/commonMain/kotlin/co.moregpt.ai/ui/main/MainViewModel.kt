package co.moregpt.ai.ui.main



import com.zxy.core.db.DBManager
import com.zxy.core.db.SessionViewEntity
import com.zxy.core.db.toSessionViewEntity
import com.zxy.core.sqldelight.SessionTag
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import postValue


/**
 *   @desc content
 *   Created by zxy
 **/
class MainViewModel : ViewModel() {
    companion object {
        const val TAG = "MainViewModel"
    }

    val recentlySessionsLV: MutableLiveData<MutableList<SessionViewEntity>> = MutableLiveData(mutableListOf())
    val sessionTagsLV: MutableLiveData<MutableList<SessionTag>> = MutableLiveData(mutableListOf())

    val historySessionTagLV: MutableLiveData<String> = MutableLiveData("")
    val historySessionsLV: MutableLiveData<MutableList<SessionViewEntity>> = MutableLiveData(mutableListOf())

    fun init() {
        querySessionsLimit()
        getAllSessionTag()
        queryAllSessionFromTag("")
    }

    fun querySessionsLimit() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = DBManager.getDatabase().querySessionsLimit()
            if (list.isNotEmpty()) {
                recentlySessionsLV.postValue(list.map { it.toSessionViewEntity() }.toMutableList())
            } else {
                recentlySessionsLV.postValue(mutableListOf())
            }
//            LogUtil.i(TAG, "recentlySessionsLV size=${recentlySessionsLV.value?.size}")
        }
    }

    fun queryAllSessionFromTag(tag: String) {
        viewModelScope.launch(Dispatchers.IO) {
            historySessionTagLV.postValue(tag)
            val list = if (tag.isEmpty()) {
                DBManager.getDatabase().getAllSession()
            } else {
                DBManager.getDatabase().queryAllSessionFromTag(tag)
            }
            if (list.isNotEmpty()) {
                historySessionsLV.postValue(list.map { it.toSessionViewEntity() }.toMutableList())
            } else {
                historySessionsLV.postValue(mutableListOf())
            }
//            LogUtil.i(TAG, "historySessionsLV size=${historySessionsLV.value?.size}")
        }
    }

    fun getAllSessionTag() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = DBManager.getDatabase().getAllSessionTag()
            if (list.isNotEmpty()) {
                sessionTagsLV.postValue(list.toMutableList())
            } else {
                sessionTagsLV.postValue(mutableListOf())
            }
//            LogUtil.i(TAG, "sessionTagsLV size=${sessionTagsLV.value?.size}")
        }
    }

}