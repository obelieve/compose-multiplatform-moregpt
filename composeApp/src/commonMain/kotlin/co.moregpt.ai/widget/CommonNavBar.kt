@file:OptIn(ExperimentalResourceApi::class)

package co.moregpt.ai.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import composemultiplatformapigpt.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

/**
 *   @desc content
 *   Created by zxy
 **/
//@Preview(showSystemUi = true)
@Composable
fun testCommonNavBar() {
    CommonNavBar("",{})
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CommonNavBar(title:String,closeClick:()->Unit) {
    Box(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()) {
        Text(
            text = title,
            color = co.moregpt.ai.ui.AppColor.Text,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 20.sp
        )
        Row(modifier = Modifier.fillMaxHeight().padding(start = 20.dp).align(Alignment.CenterStart)) {
            Image(
                painter = painterResource(Res.drawable.ic_close),
                modifier = Modifier
                    .size(15.dp).align(Alignment.CenterVertically).clickable {
                        closeClick.invoke()
                    },
                contentDescription = ""
            )
        }
    }

}