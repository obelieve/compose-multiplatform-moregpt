@file:OptIn(ExperimentalResourceApi::class)

package co.moregpt.ai.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.zxy.core.db.SessionViewEntity
import com.zxy.core.sqldelight.SessionTag
import composemultiplatformapigpt.composeapp.generated.resources.Res
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

/**
 *   @desc content
 *   Created by zxy
 **/
//@Preview(showSystemUi = true)
@Composable
fun testTestHistoryScreen() {
    HistoryScreen(Navigator(),{ a, b->}, mutableListOf(),"", mutableListOf(),{})
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUnitApi::class)
@Composable
fun HistoryScreen(navController: Navigator, startChatClick:(String?,Int)->Unit, tags:List<SessionTag>, curTag:String,
                  curSessions:List<SessionViewEntity>,
                  querySessionsClick:(String)->Unit) {
    Column {
        val selectedRowIndex = remember {
            mutableStateOf(0)
        }
        LazyRow(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
            if(tags.isNotEmpty()){
                items(tags.size) {
                    val tag = tags[it]
                    Row {
                        Card(
                            onClick = {
                                selectedRowIndex.value = it
                                querySessionsClick(tag.id)
                            }, shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(
                                containerColor = if (selectedRowIndex.value == it) co.moregpt.ai.ui.AppColor.Main else co.moregpt.ai.ui.AppColor.Background2
                            )
                        ) {
                            Text(text = if(tag.id=="")"全部" else tag.id, modifier = Modifier.padding(10.dp),if(selectedRowIndex.value==it) co.moregpt.ai.ui.AppColor.SelectedTab else co.moregpt.ai.ui.AppColor.UnSelectedTab)
                        }
                        Spacer(modifier = Modifier.padding(end = 10.dp))
                    }
                }
            }else{
                item {
                    Row {
                        Card(
                            onClick = {
                                selectedRowIndex.value = 0
                                querySessionsClick("")
                            }, shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(
                                containerColor = if (selectedRowIndex.value == 0) co.moregpt.ai.ui.AppColor.Main else co.moregpt.ai.ui.AppColor.Background2
                            )
                        ) {
                            Text(text = "全部", modifier = Modifier.padding(10.dp),if(selectedRowIndex.value==0) co.moregpt.ai.ui.AppColor.SelectedTab else co.moregpt.ai.ui.AppColor.UnSelectedTab)
                        }
                        Spacer(modifier = Modifier.padding(end = 10.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
        LazyColumn {
            items(curSessions.size) {
                val curSession = curSessions[it]
                if(it==0){
                    Spacer(modifier = Modifier.padding(5.dp))
                }
                Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
                    Card(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth().clickable {
                               startChatClick.invoke(null,curSession.id.toInt())
                            },
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = co.moregpt.ai.ui.AppColor.Background2
                        )
                    ) {
                        ConstraintLayout(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            val (tvSessionTitle, tvSessionDesc, ivArrow) = createRefs()
                            Text(
                                modifier = Modifier.fillMaxWidth().constrainAs(tvSessionTitle) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start, margin = 10.dp)
                                    end.linkTo(ivArrow.start, margin = 10.dp)
                                    horizontalChainWeight = 1f
                                }, maxLines = 2,
                                text = curSession.title,
                                color = co.moregpt.ai.ui.AppColor.Text,
                                fontSize = TextUnit(20F, TextUnitType.Sp)
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth().constrainAs(
                                    tvSessionDesc
                                ) {
                                    top.linkTo(tvSessionTitle.bottom, margin = 10.dp)
                                    start.linkTo(parent.start, margin = 10.dp)
                                    end.linkTo(ivArrow.start, margin = 10.dp)
                                    horizontalChainWeight = 1f
                                }, maxLines = 2,
                                text = curSession.getContent(),
                                color = co.moregpt.ai.ui.AppColor.Gray10
                            )
                            Image(
                                modifier = Modifier.constrainAs(ivArrow) {
                                    top.linkTo(tvSessionTitle.top, margin = 0.dp)
                                    bottom.linkTo(tvSessionDesc.bottom, margin = 0.dp)
                                    end.linkTo(parent.end, margin = 0.dp)
                                    width = Dimension.fillToConstraints
                                },
                                painter = painterResource(Res.drawable.ic_arrow_forward),
                                contentDescription = ""
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }
        }
    }
}