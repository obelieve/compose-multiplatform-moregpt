package co.moregpt.ai.ui.conversation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.zxy.core.db.MessageViewEntity
import com.zxy.core.db.SessionTagViewEntity
import com.zxy.core.db.SessionViewEntity
import com.zxy.core.enumtype.MessageRoleEnum
import dev.icerock.moko.mvvm.livedata.compose.observeAsState

/**
 *   @desc content
 *   Created by zxy
 **/
@Composable
fun ConversationScreen(statusHeight:Int,sessionId:Int?,message:String?,closePage:()->Unit,showToast:(String)->Unit) {
    val viewModel:ConversationViewModel by remember { mutableStateOf(ConversationViewModel()) }
    LaunchedEffect(true) {
//        Log.i("初始化","init,sessionId=$sessionId,message:$message")
        viewModel.init(sessionId)
    }
    MaterialTheme {
        val statusHeightDp = with(LocalDensity.current) { statusHeight.toDp() }
        val messageList by viewModel.messageListLV.observeAsState()
        val inputtingMessageContent by viewModel.inputtingMessageContentLV.observeAsState()
        val sendMessaging by viewModel.sendMessagingLV.observeAsState()
        val title by viewModel.titleLV.observeAsState()
        val currentSessionId by viewModel.currentSessionIdLV.observeAsState()
        val currentSession by viewModel.currentSessionLV.observeAsState()
        val sessionTags by viewModel.sessionTagsLV.observeAsState()
        val rankLV by viewModel.rankLV.observeAsState()
        val sendNumLV by viewModel.sendNumLV.observeAsState()
        val toastLV by viewModel.toastLV.observeAsState()
        if(toastLV?.isNotEmpty()==true){
            toastLV?.let{
                showToast.invoke(it)
            }
        }
        ConversationScreenContent(
            statusHeightDp,
            viewModel,
            title,
            sendNumLV,
            rankLV,
            messageList,
            sendMessaging,
            message?:"",currentSessionId,
            currentSession,inputtingMessageContent,{
                viewModel.updateSessionTag(it)
            },{
                viewModel.deleteSession(it,closePage)
            },sessionTags
        ) {
            viewModel.getAllSessionTag()
        }
    }
}


@Composable
fun ConversationScreenContent(
    statusHeightDp: Dp,
    viewModel: ConversationViewModel,
    title: String,
    sendNum:Int,
    rank:Int,
    messageList: MutableList<MessageViewEntity>,
    sendMessaging: Boolean,
    message:String?,
    currentSessionId:Int,
    currentSession: SessionViewEntity?,
    inputtingMessageContent:Pair<Int,String>?,
    editTagConfirmClick:(String)->Unit,
    deleteConversationConfirmClick:(Int)->Unit,
    sessionTags: MutableList<SessionTagViewEntity>,
    querySessionTagsClick:()->Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(co.moregpt.ai.ui.AppColor.Background)
            .padding(top = statusHeightDp)
    ) {
        val isOpenPopup = remember {
            mutableStateOf(false)
        }
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (topNavRef, contentRef, bottomBarRef) = createRefs()
            TopNavBar(modifier = Modifier.constrainAs(topNavRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },  title) {
                isOpenPopup.value = !isOpenPopup.value
            }
            Box(modifier = Modifier
                .constrainAs(contentRef) {
                    top.linkTo(topNavRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomBarRef.top)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }) {
                val scrollState = rememberLazyListState()
                var inputStateContent = remember {
                    mutableStateOf("")
                }
                LazyColumn(state = scrollState, content = {
                    items(messageList) {
                        Spacer(modifier = Modifier.height(10.dp))
                        if (MessageRoleEnum.USER.role == it.role) {
                            RightView(message = it){resendMsg->
                                viewModel.reSendMessage(resendMsg)
                            }
                        } else if (MessageRoleEnum.ASSISTANT.role == it.role) {
                            if(it.inputting){
                                LeftInputtingView(initialIndex = inputtingMessageContent?.first?:inputtingMessageContent?.second?.length?:-1,message = if(inputStateContent.value.isNotEmpty()==true)inputStateContent.value else it.content)
                                LaunchedEffect(key1 = inputtingMessageContent?.second) {
                                    inputStateContent.value = inputtingMessageContent?.second?:""
                                }
                            }else{
                                LeftView(message = it.content)
                            }
                        } else if (MessageRoleEnum.SYSTEM.role == it.role) {
                            SystemView(message = it)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                })
                if (messageList.isNotEmpty()) {
                    LaunchedEffect(key1 = messageList.size,inputtingMessageContent?.second) {
                        scrollState.animateScrollToItem(messageList.size)
                    }
                }
            }
            BottomBar(
                modifier = Modifier
                    .constrainAs(bottomBarRef) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        width = Dimension.fillToConstraints
                        end.linkTo(parent.end)
                    }
                    .padding(start = 10.dp, end = 10.dp, bottom = 20.dp, top = 10.dp),
                sendNum,
                rank,
                sendMessaging,
                message
            ) {
                viewModel.sendMessage(it)
            }
        }
        if(isOpenPopup.value&&currentSession!=null){
            querySessionTagsClick.invoke()
            MorePopup(sessionTags,session = currentSession,{tag->
                editTagConfirmClick.invoke(tag)
            },{id->
                deleteConversationConfirmClick.invoke(id)
            },{
                isOpenPopup.value = false
            })
        }
    }
}
