@file:OptIn(ExperimentalResourceApi::class)

package co.moregpt.ai.ui.conversation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import composemultiplatformapigpt.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


//@Preview(showSystemUi = true, heightDp = 56)
@Composable
fun testBottomBar() {
    BottomBar(modifier = Modifier, 3,0,true,null){
        true
    }
}

/**
 *   @desc content
 *   Created by zxy
 **/
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomBar(modifier: Modifier,sendNum:Int,rank:Int, sendMessaging: Boolean,message:String?, sendMessageClick: (String) -> Boolean) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            backgroundColor = co.moregpt.ai.ui.AppColor.Background2
        ) {
            var hasFocus by remember {
                mutableStateOf(false)
            }
            val focusRequester = remember {
                FocusRequester()
            }
            LaunchedEffect(""){
                if(message?.isNotEmpty()==true){
                    focusRequester.requestFocus()
                }
            }
            val focusManager = LocalFocusManager.current
            val direction = LocalLayoutDirection.current
            val behaviour: CursorSelectionBehaviour = CursorSelectionBehaviour.END
            //val textValue = remember { mutableStateOf(message?:"") }
            var textValue by remember {
                val selection = when (behaviour) {
                    CursorSelectionBehaviour.START -> {
                        if (direction == LayoutDirection.Ltr) TextRange.Zero else TextRange(message?.length?:0)
                    }
                    CursorSelectionBehaviour.END -> {
                        if (direction == LayoutDirection.Ltr) TextRange(message?.length?:0) else TextRange.Zero
                    }
                    CursorSelectionBehaviour.SELECT_ALL -> TextRange(0, message?.length?:0)
                }
                val textFieldValue = TextFieldValue(text = message?:"", selection = selection)
                mutableStateOf(textFieldValue)
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.defaultMinSize(minHeight = 50.dp)) {
                TextField(
                    value = textValue, onValueChange = {
                        textValue = it
                    }, placeholder = {
                        Text(text = "输入您想问的..", color = co.moregpt.ai.ui.AppColor.Text2)
                    }, maxLines = 6, colors = TextFieldDefaults.textFieldColors(
                        textColor = co.moregpt.ai.ui.AppColor.Text2,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = co.moregpt.ai.ui.AppColor.Main
                    ), modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            hasFocus = it.isFocused
                        }
                )
                Image(
                    painterResource(if (textValue.text.isEmpty()||sendMessaging) Res.drawable.ic_send_green_disable else Res.drawable.ic_send_green),
                    contentDescription = "send",
                    modifier = Modifier
                        .width(30.dp)
                        .padding(end = 10.dp)
                        .clickable {
                            if (!textValue.text.isEmpty() && !sendMessaging) {
                                focusManager.clearFocus()
                                val result = sendMessageClick.invoke(textValue.text)
                                if (result) {
                                    textValue = TextFieldValue("")
                                }
                            }
                        })
            }
        }
    }
}
enum class CursorSelectionBehaviour {
    START, END, SELECT_ALL
}

@Composable
fun AutofocusTextField(
    initValue: String,
    behaviour: CursorSelectionBehaviour = CursorSelectionBehaviour.END
) {
    val direction = LocalLayoutDirection.current
    var tfv by remember {
        val selection = when (behaviour) {
            CursorSelectionBehaviour.START -> {
                if (direction == LayoutDirection.Ltr) TextRange.Zero else TextRange(initValue.length)
            }
            CursorSelectionBehaviour.END -> {
                if (direction == LayoutDirection.Ltr) TextRange(initValue.length) else TextRange.Zero
            }
            CursorSelectionBehaviour.SELECT_ALL -> TextRange(0, initValue.length)
        }
        val textFieldValue = TextFieldValue(text = initValue, selection = selection)
        mutableStateOf(textFieldValue)
    }
    val focusRequester = remember { FocusRequester.Default }
    TextField(
        modifier = Modifier.focusRequester(focusRequester),
        value = tfv,
        onValueChange = { tfv = it }
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

