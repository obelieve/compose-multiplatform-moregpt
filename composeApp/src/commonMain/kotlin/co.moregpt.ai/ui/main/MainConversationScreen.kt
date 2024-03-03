package co.moregpt.ai.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zxy.core.db.SessionViewEntity
import moe.tlaster.precompose.navigation.Navigator

/**
 *   @desc content
 *   Created by zxy
 **/
//@Preview(showSystemUi = true)
@Composable
fun testMainConversationScreen() {
    MainConversationScreen(Navigator(), mutableListOf(),{ a, b->},{})
}

@Composable
fun MainConversationScreen(navController: Navigator, recentlySessions:List<SessionViewEntity>, sendBarClick:(String?, Int?)->Unit, lookAllClick:()->Unit) {
    Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        .background(co.moregpt.ai.ui.AppColor.Black10)
        .fillMaxSize()) {
        MainTemplateList(modifier = Modifier.weight(1f),recentlySessions,sendBarClick,lookAllClick)
        Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
        MainSendBar(sendBarClick)
    }
}