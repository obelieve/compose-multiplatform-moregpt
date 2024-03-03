package co.moregpt.ai.ui.account


import com.zxy.core.cache.GlobalCacheRepository
import com.zxy.core.model.InitialResBean
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel

/**
 *   @desc content
 *   Created by zxy
 **/
class MyAccountViewModel: ViewModel() {

    val initialInfoLV: MutableLiveData<InitialResBean?> = MutableLiveData(GlobalCacheRepository.initialResBean)

    fun refreshInitialInfo() {
        initialInfoLV.value = GlobalCacheRepository.initialResBean
    }
}