package co.moregpt.ai.widget

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import kotlinx.coroutines.delay

/**
 *   @desc content
 *   Created by zxy
 **/
//@Preview(showSystemUi = true)
@Composable
fun testaa() {
    TextCursorBlinking("",Modifier)
}

@Composable
fun TextCursorBlinking(text:String, modifier: Modifier = Modifier,
                       color: Color = Color.Unspecified,cursorColor:Color = Color.Unspecified) {
    var showCursor by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            showCursor = !showCursor
        }
    }
    Text(modifier = modifier, text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = color)){
            append(text)
        }
        if(showCursor){
            withStyle(style = SpanStyle(color = cursorColor)){
                append("|")
            }
        }
    })
}