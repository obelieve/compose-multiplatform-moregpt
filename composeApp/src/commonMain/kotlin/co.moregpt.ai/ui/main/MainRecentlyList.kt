@file:OptIn(ExperimentalResourceApi::class)

package co.moregpt.ai.ui.main


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.zxy.core.db.SessionViewEntity
import composemultiplatformapigpt.composeapp.generated.resources.Res
import getScreenWidthPixels
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


/**
 *   @desc content
 *   Created by zxy
 **/
//@Preview(showSystemUi = true)
@Composable
fun testMainRecentlyList() {
    MainRecentlyList(mutableListOf(),{a,b->},{})
}

@OptIn(ExperimentalUnitApi::class, ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun MainRecentlyList(recentlySessions:List<SessionViewEntity>, startChatClick:(String?, Int?)->Unit, lookAllClick:()->Unit) {
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()) {
        val (tvTitle, tvShowAll,lcList) = createRefs()
        Text(modifier = Modifier.constrainAs(tvTitle) {
            top.linkTo(parent.top)
            start.linkTo(parent.start, margin = 0.dp)
            width = Dimension.fillToConstraints
        },text="最近对话", color = co.moregpt.ai.ui.AppColor.Text, fontSize = TextUnit(20F, TextUnitType.Sp))
//        Text(modifier = Modifier
//            .constrainAs(tvShowAll) {
//                top.linkTo(tvTitle.top, margin = 2.dp)
//                end.linkTo(parent.end, margin = 0.dp)
//                width = Dimension.fillToConstraints
//            }
//            .clickable {
//                lookAllClick.invoke()
//            }, text = "查看全部", color = AppColor.Gray10)
        if(recentlySessions.isEmpty()) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(lcList) {
                    top.linkTo(tvTitle.bottom, margin = 20.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                    end.linkTo(parent.end, margin = 0.dp)
                    width = Dimension.fillToConstraints
                }) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Text(text = "还没有对话😞", color = co.moregpt.ai.ui.AppColor.Text2, fontSize = TextUnit(20F,TextUnitType.Sp))
                    }
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    Card(modifier = Modifier.align(Alignment.CenterHorizontally), shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(
                            containerColor = co.moregpt.ai.ui.AppColor.Main)) {
                        Box(modifier = Modifier.height(36.dp).clickable {
                            startChatClick.invoke(null,null)
                        }){
                            Text(modifier = Modifier
                                .padding(start = 30.dp, end = 30.dp)
                                .align(Alignment.Center),text = "开始", color = co.moregpt.ai.ui.AppColor.Text)
                        }
                    }
                }
            }
        }else{
            LazyRow(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(lcList) {
                    top.linkTo(tvTitle.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                    end.linkTo(parent.end, margin = 0.dp)
                    width = Dimension.fillToConstraints
                }
            ) {
                items(recentlySessions.size){
                    val recentlySession = recentlySessions[it]
                    Row {
                        Card(modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth().clickable {
                                startChatClick.invoke(null,recentlySession.id.toInt())
                            }, shape = RoundedCornerShape(15.dp),colors = CardDefaults.cardColors(
                            containerColor = co.moregpt.ai.ui.AppColor.Background2)) {
                            val screenWidth = getScreenWidthPixels()
                            val density = LocalDensity.current
                            ConstraintLayout(modifier = Modifier
                                .padding(16.dp)
                            ) {
                                val (boxSessionTitle, tvSessionDesc,ivMore) = createRefs()
                                Box(modifier = Modifier
                                    .constrainAs(boxSessionTitle) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start, margin = 0.dp)
                                        width = Dimension.fillToConstraints
                                    }
                                    .width(
                                        (screenWidth * 0.6f)
                                            .div(density.density)
                                            .toInt().dp
                                    )){
                                    Text(modifier = Modifier, text=recentlySession.title,
                                        maxLines = 2, minLines = 2
                                        ,color = co.moregpt.ai.ui.AppColor.Text, fontSize = TextUnit(20F, TextUnitType.Sp))
                                }

                                Image(modifier = Modifier.constrainAs(ivMore){
                                    top.linkTo(boxSessionTitle.bottom, margin = 15.dp)
                                    end.linkTo(parent.end, margin = 0.dp)
                                },painter = painterResource(Res.drawable.ic_more), contentDescription ="")
                                Text(modifier = Modifier.constrainAs(tvSessionDesc) {
                                    top.linkTo(ivMore.top, margin = 10.dp)
                                    bottom.linkTo(ivMore.bottom, margin = 10.dp)
                                    start.linkTo(boxSessionTitle.start, margin = 0.dp)
                                    width = Dimension.fillToConstraints
                                }, text = recentlySession.getContent(), color = co.moregpt.ai.ui.AppColor.Gray10)
                            }
                        }
                        Spacer(modifier = Modifier.padding(5.dp))
                    }
                }
            }
        }
    }
}