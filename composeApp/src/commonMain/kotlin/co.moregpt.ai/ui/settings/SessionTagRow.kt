package co.moregpt.ai.ui.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zxy.core.db.SessionTagViewEntity

/**
 *   @desc content
 *   Created by zxy
 **/
//@Preview(showSystemUi = true)
@Composable
fun testSessionTagRow() {
    SessionTagRow(mutableListOf())
}

@Composable
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
fun SessionTagRow(list: List<SessionTagViewEntity>, defaultIndex:Int = 0, needSelected:Boolean = false, selectClick:((String)->Unit)?= null) {
    Box(modifier = Modifier.height(150.dp)) {
        var scrollState = remember {
            ScrollState(0)
        }
        val selectedRowIndex = remember {
            mutableStateOf(defaultIndex)
        }
        FlowRow(
            modifier = Modifier.fillMaxHeight()
                .verticalScroll(scrollState)
        ) {
            for (it in 0 until list.size) {
                val item = list[it]
                Row {
                    Card(
                        border = BorderStroke(1.dp, if (needSelected)
                            (if(selectedRowIndex.value == it) co.moregpt.ai.ui.AppColor.StrokeSelectedCategory2 else co.moregpt.ai.ui.AppColor.Background2)
                        else co.moregpt.ai.ui.AppColor.SelectedCategory),
                        shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(
                            containerColor = co.moregpt.ai.ui.AppColor.SelectedCategory
                        ),onClick = {
                            selectedRowIndex.value = it
                            selectClick?.invoke(item.id)
                        }, modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(start = 12.dp, end = 12.dp)
                                .height(40.dp)
                        ) {
                            Text(
                                text = if (item.id.isEmpty()) "全部" else item.id,
                                modifier = Modifier
                                    .padding(start = 0.dp)
                                    .align(Alignment.CenterVertically),
                                co.moregpt.ai.ui.AppColor.UnSelectedTab,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(end = 10.dp))
                }
            }
            if (list.isNotEmpty()) {
                LaunchedEffect(key1 = list) {
                    scrollState.scrollTo(scrollState.maxValue)
                }
            }
        }
    }
}
