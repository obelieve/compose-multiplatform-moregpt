package co.moregpt.ai.ui.settings



import com.zxy.core.cache.GlobalCacheRepository
import com.zxy.core.db.DBManager
import com.zxy.core.db.SessionTagViewEntity
import com.zxy.core.db.toSessionTag
import com.zxy.core.db.toSessionTagViewEntity
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import postValue

/**
 *   @desc content
 *   Created by zxy
 **/
class SettingsViewModel: ViewModel() {

    companion object {
        const val TAG = "SettingsViewModel"
    }

    val sessionTagsLV: MutableLiveData<MutableList<SessionTagViewEntity>> = MutableLiveData(mutableListOf())

    val initialInfoLV: MutableLiveData<com.zxy.core.model.InitialResBean?> = MutableLiveData(
        GlobalCacheRepository.initialResBean)

    fun init() {
        getAllSessionTag()
    }

    fun insertSessionTag(tag:String){
        viewModelScope.launch(Dispatchers.Main) {
            val sessionId = withContext(Dispatchers.IO){
                val id = DBManager.getDatabase().insertSessionTag(SessionTagViewEntity(tag).toSessionTag())
                if(id!=-1){
//                    EventBus.getDefault().post(SessionTagAddEvent())
                }
            }
        }
    }

    fun getAllSessionTag() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = DBManager.getDatabase().getAllSessionTag()
            if (list.isNotEmpty()) {
                sessionTagsLV.postValue(list.map { it.toSessionTagViewEntity() }.toMutableList())
            } else {
                sessionTagsLV.postValue(mutableListOf())
            }

        }
    }

    fun refreshInitialInfo() {
        initialInfoLV.value = GlobalCacheRepository.initialResBean
    }
}