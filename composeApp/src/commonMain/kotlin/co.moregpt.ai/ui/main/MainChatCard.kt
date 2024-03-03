@file:OptIn(ExperimentalResourceApi::class)

package co.moregpt.ai.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

/**
 *   @desc content
 *   Created by zxy
 **/
//@Preview(showSystemUi = true)
@Composable
fun testMainChatCard() {
    MainChatCard()
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainChatCard() {
    Card(
        backgroundColor = co.moregpt.ai.ui.AppColor.Background2,
        shape = RoundedCornerShape(16.dp), modifier = Modifier.wrapContentHeight().fillMaxWidth()
    ){
        ConstraintLayout(modifier = Modifier.padding(16.dp)) {
            val (imgIssue, tvTitle, tvDesc,tvChat) = createRefs()
            val textValue = remember { mutableStateOf("") }
            Icon(modifier = Modifier
                .width(30.dp)
                .constrainAs(imgIssue) {
                    top.linkTo(parent.top, margin = 5.dp)
                    start.linkTo(parent.start)
                    end.linkTo(tvTitle.start)
                }, painter = painterResource(co.moregpt.ai.ui.AppIcons.ASK), contentDescription = "", tint = Color.Unspecified)
            Text(modifier = Modifier.constrainAs(tvTitle) {
                top.linkTo(parent.top)
                start.linkTo(imgIssue.end, margin = 10.dp)
                width = Dimension.fillToConstraints
            },text="问一个问题", color = co.moregpt.ai.ui.AppColor.Text)
            Text(modifier = Modifier.constrainAs(tvDesc) {
                top.linkTo(tvTitle.bottom, margin = 2.dp)
                start.linkTo(tvTitle.start)
                end.linkTo(parent.end, margin = 0.dp)
                width = Dimension.fillToConstraints
            }, text = "从我们的AI聊天中获得帮助、创意和明智的建议", color = co.moregpt.ai.ui.AppColor.Gray10)
            Card(modifier = Modifier.size(width = 120.dp, height = 40.dp)
                .constrainAs(tvChat) {
                    top.linkTo(tvDesc.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 50.dp)
                    end.linkTo(parent.end, margin = 50.dp)
                    width = Dimension.fillToConstraints
                },
                backgroundColor = co.moregpt.ai.ui.AppColor.Main,
                shape = RoundedCornerShape(40.dp)) {
                Box{
                    Text(modifier = Modifier.align(Alignment.Center), text = "开始聊天", color = co.moregpt.ai.ui.AppColor.Text)
                }
            }
        }
    }
}