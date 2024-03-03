@file:OptIn(ExperimentalResourceApi::class, ExperimentalResourceApi::class)

package co.moregpt.ai.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composemultiplatformapigpt.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


/**
 *   @desc content
 *   Created by zxy
 **/
//@Preview(showSystemUi = true)
@Composable
fun testTestSendBar() {
    MainSendBar({a,b->})
}

@Composable
fun MainSendBar(sendBarClick:(String?,Int?)->Unit) {
    Card(modifier = Modifier.clickable {
        sendBarClick.invoke("",null)
    }, shape = RoundedCornerShape(15.dp), backgroundColor = co.moregpt.ai.ui.AppColor.Background2) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(50.dp)) {
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text(text = "输入您想问的..", color = co.moregpt.ai.ui.AppColor.Text2, modifier = Modifier.weight(1f))
            Image(painterResource(Res.drawable.ic_send_green), contentDescription = "send",modifier = Modifier
                .width(30.dp)
                .padding(end = 10.dp))
        }
    }
}