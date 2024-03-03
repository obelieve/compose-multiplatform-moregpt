package co.moregpt.ai.ui.feedback

//import com.github.compose.waveloading.DrawType
//import com.github.compose.waveloading.WaveLoading
//import com.github.compose.waveloading.rememberDrawColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import co.moregpt.ai.widget.CommonNavBar
import dev.icerock.moko.mvvm.livedata.compose.observeAsState

/**
 *   @desc content
 *   Created by zxy
 **/
@Composable
fun FeedbackScreen(statusHeight:Int,showToast:(String)->Unit,closePage:()->Unit) {
    val viewModel by remember { mutableStateOf(FeedbackViewModel()) }
    LaunchedEffect(true){
        viewModel.toastLV.addObserver {str->
            str?.let {
                showToast.invoke(it)
            }
        }
    }
    val statusHeightDp = with(LocalDensity.current) { statusHeight.toDp() }
    val loadingLV by viewModel.loadingLV.observeAsState()
    val successLV by viewModel.successLV.observeAsState()
    Box(
        modifier = Modifier
            .background(color = co.moregpt.ai.ui.AppColor.Background)
            .fillMaxSize()
            .padding(top = statusHeightDp)
    ) {
        Column {
            CommonNavBar(title = "反馈") {
                closePage.invoke()
            }
            FeedbackScreenContent(successLV){
                viewModel.feedback(it)
            }
        }
        LaunchedEffect(successLV){
            viewModel.refresh()
        }
        if(loadingLV){
//            WaveLoading (modifier = Modifier,
//                foreDrawType = DrawType.DrawImage,
//                backDrawType= rememberDrawColor(color = AppColor.White10),
//                progress = 0.3f,
//                amplitude = 1.0f,
//                velocity = 0.5f
//            ) {
//                Image(painter = painterResource(Res.drawable.ic_logo_loading), modifier = Modifier.size(96.dp), contentDescription = "")
//            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreenContent(sent:Boolean,feedbackClick:(String)->Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 65.dp),
            horizontalAlignment = Alignment.Start
        ) {
            val feedbackText = remember { mutableStateOf("") }
            Card(modifier = Modifier.fillMaxWidth()
                , shape = RoundedCornerShape(15.dp), colors = CardDefaults.cardColors(
                    containerColor = co.moregpt.ai.ui.AppColor.Background2)) {
                // 输入框
                TextField(
                    value = feedbackText.value,
                    onValueChange = { feedbackText.value = it },
                    label = { Text("请输入反馈内容", color = co.moregpt.ai.ui.AppColor.Text2) },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = co.moregpt.ai.ui.AppColor.Text,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = co.moregpt.ai.ui.AppColor.Main,
                        backgroundColor = co.moregpt.ai.ui.AppColor.Background2
                    ),
                    minLines = 2,
                    maxLines = 5
                )
            }

            // 提交按钮
            Button(
                onClick = { feedbackClick.invoke(feedbackText.value) },
                modifier = Modifier.padding(top = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = co.moregpt.ai.ui.AppColor.Text,
                    backgroundColor = co.moregpt.ai.ui.AppColor.Main
                )
            ) {
                Text("提交")
            }
            if(sent){
                feedbackText.value = ""
            }
        }
    }
}