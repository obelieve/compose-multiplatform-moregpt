@file:OptIn(ExperimentalResourceApi::class)

package co.moregpt.ai.ui.settings


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
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
fun testSettingsNavBar() {
    SettingsNavBar({})
}

@OptIn(ExperimentalUnitApi::class, ExperimentalResourceApi::class)
@Composable
fun SettingsNavBar(backClick:()->Unit) {
    Box(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxHeight()) {
            Image(
                painter = painterResource(Res.drawable.ic_arrow_back),
                modifier = Modifier
                    .size(30.dp)
                    .offset(10.dp).align(Alignment.CenterVertically)
                    .clickable {
                        backClick.invoke()
                    },
                contentDescription = ""
            )
        }
        Text(
            text = "设置",
            color = co.moregpt.ai.ui.AppColor.Text,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 20.sp
        )
    }

}