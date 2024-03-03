@file:OptIn(ExperimentalMaterial3Api::class)

package co.moregpt.ai.ui.conversation

import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


//@Preview
@Composable
fun testTopNavBar(){
    TopNavBar(modifier = Modifier,"标题",{})
}

/**
 *   @desc content
 *   Created by zxy
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(modifier: Modifier, title:String,  moreClick: () -> Unit) {
    SmallTopAppBar(modifier = modifier,colors =  TopAppBarDefaults.smallTopAppBarColors(
        containerColor = co.moregpt.ai.ui.AppColor.Background
    ), title = {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            val scrollState = rememberScrollState(0)
            Box(modifier = Modifier.horizontalScroll(scrollState)) {
                Text(text = title, maxLines = 1, color = co.moregpt.ai.ui.AppColor.Text)
            }
            LaunchedEffect(Unit) {
                while (true) {
                    delay(16)
                    if (scrollState.value == scrollState.maxValue) {
                        delay(1000)
                        scrollState.scrollTo(0)
                        delay(1000)
                    } else {
                        scrollState.scrollBy(1f)
                    }
                }
            }
        }
    }, navigationIcon = {
        Spacer(modifier = Modifier.size(48.dp))
    }, actions = {
       Row{
           IconButton(onClick = {
               moreClick.invoke()
           }) {
               Icon(Icons.Default.MoreVert, tint = co.moregpt.ai.ui.AppColor.White10, contentDescription = "more")
           }
       }
    })
}