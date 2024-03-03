@file:OptIn(ExperimentalResourceApi::class, ExperimentalResourceApi::class)

package co.moregpt.ai.ui.conversation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zxy.core.db.SessionViewEntity
import composemultiplatformapigpt.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


//@Preview
@Composable
fun testSideBar() {
    val showState = remember {
        mutableStateOf(false)
    }
    val sessionList = mutableListOf(SessionViewEntity(0, "会话", 0))
    SideBar(modifier = Modifier, showState, sessionList, 0, {}, {},{})
}

/**
 *   @desc content
 *   Created by zxy
 **/
@OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
@Composable
fun SideBar(
    modifier: Modifier = Modifier,
    showState: MutableState<Boolean>,
    sessionList: List<SessionViewEntity>,
    currentSessionId: Int,
    newConversionClick: () -> Unit,
    enterConversionClick: (Int) -> Unit,
     deleteConversionClick: (Int) -> Unit
) {
    Box(modifier = modifier
        .fillMaxSize()
        .background(Color.Black.copy(alpha = 0f))
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            if (showState.value) {
                showState.value = false
            }
        }) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(200.dp)
                .background(color = Color.White)
                .clickable(
                    onClick = {},
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.offset(y = (-10).dp),
                onClick = { newConversionClick.invoke() },
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = stringResource(Res.string.new_conversation))
            }
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                items(sessionList) { session ->
                    val showClose = remember {
                        mutableStateOf(false)
                    }
                    val clickClose = remember {
                        mutableStateOf(false)
                    }
                    Box(modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)){
                        Card(  shape=RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .combinedClickable(true, onLongClick = {
                                    if(currentSessionId.toLong() !=session.id){
                                        if (!showClose.value) {
                                            showClose.value = true
                                        }
                                    }
                                }, onClick = {
                                    if(showClose.value){
                                        showClose.value = false
                                    }else{
                                        enterConversionClick.invoke(session.id.toInt())
                                    }
                                }).background(MaterialTheme.colorScheme.background),
                        ) {
                            Row(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = (if (session.title.isEmpty()) "${stringResource(Res.string.new_conversation)}${session.id}" else session.title),
                                    color = (if (currentSessionId.toLong() == session.id) MaterialTheme.colorScheme.secondaryContainer else Color.Black),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center
                                ) //新会话
                                if(showClose.value){
                                    Image(
                                        painter = painterResource(Res.drawable.ic_circle_close),
                                        contentDescription = "close"
                                        ,modifier=Modifier.clickable {
                                            clickClose.value = true
                                        })
                                }
                                if(clickClose.value){
                                    DeleteConversationDialog(
                                        id = session.id.toInt(),
                                        title = (if (session.title.isEmpty()) "${stringResource(Res.string.new_conversation)}${session.id}" else session.title),
                                        onCancel = { clickClose.value = false
                                            showClose.value = false}
                                    ) {
                                        clickClose.value = false
                                        showClose.value = false
                                        deleteConversionClick.invoke(session.id.toInt())
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = modifier.height(10.dp))
                }
            }

        }

    }
}