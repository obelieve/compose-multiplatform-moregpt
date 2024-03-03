@file:OptIn(ExperimentalResourceApi::class, ExperimentalResourceApi::class)

package co.moregpt.ai.ui.conversation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import co.moregpt.ai.ui.AppIcons
import co.moregpt.ai.widget.TypewriterText
import com.zxy.core.db.MessageViewEntity
import composemultiplatformapigpt.composeapp.generated.resources.Res
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.LottieConstants
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import share
import toastShow

/**
*   @desc content
*   Created by zxy
**/

//@Preview(showSystemUi = true)
@Composable
fun testChatItem(){
    LoadingChat()
}

@Composable
fun SystemView(modifier: Modifier = Modifier.padding(start = 10.dp, end = 20.dp), message: MessageViewEntity) {
    Row(modifier = modifier) {
        Image(painterResource(Res.drawable.ic_logo), modifier = Modifier
            .clip(CircleShape)
            .size(45.dp), contentDescription = "system")
        Row( modifier = Modifier
            .padding(start = 7.dp)) {
            if(message.content.isEmpty()){
                LoadingChat()
            }
            SelectionContainer{
                Text(text = message.content, modifier = Modifier.padding(15.dp),color = Color.Red, maxLines = 3,overflow = TextOverflow.Ellipsis
                    , style = co.moregpt.ai.ui.AppTypography.bodyMedium)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeftView(modifier: Modifier = Modifier.padding(start = 10.dp, end = 20.dp), message: String) {
    Column(modifier = modifier) {
        Row() {
            Image(painterResource(Res.drawable.ic_logo), modifier = Modifier
                .clip(CircleShape)
                .size(45.dp), contentDescription = "assistant")
            Column {
                Card(modifier = Modifier.padding(start = 8.dp),border = BorderStroke(1.dp, co.moregpt.ai.ui.AppColor.StrokeSelectedCategory)
                    , shape = RoundedCornerShape(15.dp), colors = CardDefaults.cardColors(
                        containerColor = co.moregpt.ai.ui.AppColor.SelectedCategory)
                ) {
                    SelectionContainer {
                        Text(text = message, modifier = Modifier.padding(15.dp), color = co.moregpt.ai.ui.AppColor.Text
                            , style = co.moregpt.ai.ui.AppTypography.bodyMedium)
                    }
                }
                Row(modifier = Modifier.padding(start = 12.dp, top = 8.dp)) {
                   Image(modifier = Modifier
                       .size(20.dp)
                       .clickable {
                                  share(message)
                       },painter = painterResource(Res.drawable.ic_share), contentDescription = "分享")
                    val clipboardManager: ClipboardManager = LocalClipboardManager.current
                   Image(modifier = Modifier
                       .padding(start = 8.dp)
                       .size(20.dp)
                       .clickable {
                           clipboardManager.setText(AnnotatedString(message))
                           toastShow("复制成功")
                       }, painter = painterResource(Res.drawable.ic_copy), contentDescription = "复制")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeftInputtingView(modifier: Modifier = Modifier.padding(start = 10.dp, end = 20.dp),initialIndex:Int, message: String) {
    Column(modifier = modifier) {
        Row() {
            Image(painterResource(Res.drawable.ic_logo), modifier = Modifier
                .clip(CircleShape)
                .size(45.dp), contentDescription = "assistant")
            Column {
                Card(modifier = Modifier.padding(start = 8.dp),border = BorderStroke(1.dp,
                    co.moregpt.ai.ui.AppColor.StrokeSelectedCategory)
                    , shape = RoundedCornerShape(15.dp), colors = CardDefaults.cardColors(
                        containerColor = co.moregpt.ai.ui.AppColor.SelectedCategory)
                ) {
                    SelectionContainer {
                        TypewriterText(initialIndex = initialIndex,
                            content = message,
                            modifier = Modifier.padding(15.dp),
                            color = co.moregpt.ai.ui.AppColor.Text, style = co.moregpt.ai.ui.AppTypography.bodyMedium)
                    }
                }
                Row(modifier = Modifier.padding(start = 12.dp, top = 8.dp)) {
                    Image(modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            share(message)
                        },painter = painterResource(Res.drawable.ic_share), contentDescription = "分享")
                    val clipboardManager: ClipboardManager = LocalClipboardManager.current
                    Image(modifier = Modifier
                        .padding(start = 8.dp)
                        .size(20.dp)
                        .clickable {
                            clipboardManager.setText(AnnotatedString(message))
                            toastShow("复制成功")
                        }, painter = painterResource(Res.drawable.ic_copy), contentDescription = "复制")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun RightView(modifier: Modifier = Modifier.padding(start = 10.dp, end = 20.dp), message: MessageViewEntity,resendMsgClick:(MessageViewEntity)->Unit) {
    Column {
        Row(modifier = modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            AnimatedVisibility(modifier= Modifier.align(Alignment.CenterVertically),visible = message.responseTime== MessageViewEntity.RESPONSE_TIME_ERROR,enter = fadeIn(),exit = fadeOut()
            ) {
                Image(painter = painterResource(Res.drawable.ic_send_error),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            resendMsgClick.invoke(message)
                        }, contentDescription = "error")
            }
            Card(modifier = Modifier.padding(start = 8.dp)
                , shape = RoundedCornerShape(15.dp), colors = CardDefaults.cardColors(
                    containerColor = co.moregpt.ai.ui.AppColor.Background2)
            ) {
                SelectionContainer{
                    //字太长会被遮挡
                    Text(text = message.content, modifier = Modifier
                        .padding(15.dp)
                        .widthIn(0.dp, 250.dp), color = co.moregpt.ai.ui.AppColor.Text
                        , style = co.moregpt.ai.ui.AppTypography.bodyMedium)
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(Res.drawable.me), modifier = Modifier
                    .clip(CircleShape)
                    .size(45.dp), contentScale = ContentScale.Crop, contentDescription = "user"
            )
        }
        AnimatedVisibility(modifier= Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),visible = message.responseTime==MessageViewEntity.RESPONSE_TIME_ERROR,
            enter = fadeIn(),exit = fadeOut()
        ) {
            Box(modifier= Modifier.fillMaxWidth()) {
                Card(modifier = Modifier.align(Alignment.Center)
                    , shape = RoundedCornerShape(15.dp), colors = CardDefaults.cardColors(
                        containerColor = co.moregpt.ai.ui.AppColor.SendErrorBg)
                ){
                    Text(text = message.error, modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp, top = 4.dp, bottom = 4.dp
                        )
                        .widthIn(max = 200.dp),color = co.moregpt.ai.ui.AppColor.SendError,maxLines = 1,overflow = TextOverflow.Ellipsis
                        , style = co.moregpt.ai.ui.AppTypography.bodyMedium)
                }
            }
        }
    }
}
@Composable
fun LoadingChat(){
    val lottieComposition by rememberLottieComposition(
        //spec = RawRes(R.raw.img_practice),
//        spec = LottieCompositionSpec.Asset("chat_loading.json")
        spec = LottieCompositionSpec.JsonString(AppIcons.Lottie_load_jsong)
    )

    var isPlaying by remember {
        mutableStateOf(true)
    }
    var speed by remember {
        mutableStateOf(3.0f)
    }

    val lottieAnimationState by animateLottieCompositionAsState (
        composition = lottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = true
    )
//    lottieAnimationState,
    LottieAnimation(
        lottieComposition,
        modifier = Modifier
            .size(50.dp)
            .padding(end = 0.dp)
    )
}