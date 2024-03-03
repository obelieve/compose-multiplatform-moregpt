package co.moregpt.ai.widget

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.delay

/**
 *   打字机效果文本
 *   @desc content
 *   Created by zxy
 **/
//@Preview(showSystemUi = true)
@Composable
fun testTypewriterText() {
    TypewriterText(0,"文本")
}

@Composable
fun TypewriterText(initialIndex:Int, content:String,modifier: Modifier = Modifier, speed:Long = 80, showLastBlinking:Boolean = false, lastBlinkingSpeed:Long = 249,
                   color: Color = Color.Unspecified,fontSize:TextUnit = TextUnit.Unspecified,style: TextStyle = LocalTextStyle.current) {
    if(initialIndex<0||initialIndex>=content.length){
        Text(text = content,modifier = modifier, color = color,style = style)
    }else{
        var index by remember { mutableStateOf(initialIndex) }
        val iShowLastBlinking by remember { mutableStateOf(showLastBlinking) }
        var cursorBlinking by remember { mutableStateOf(false) }
        val length = content.length
        val text = try{
            content.substring(0, index)
        }catch (e:Exception){//有异常，index==22 length==2
            e.printStackTrace()
            content
        }
        val resultText = if (index % 2 == 0) (if (index != length) "$text|" else if(iShowLastBlinking&&cursorBlinking) "$text|" else "$text" ) else
            if(iShowLastBlinking&&cursorBlinking) "$text|" else "$text"
        Text(text = resultText,modifier = modifier, color = color,fontSize = fontSize,style = style)
        LaunchedEffect(key1 = index,iShowLastBlinking,cursorBlinking) {
            delay(if(iShowLastBlinking&&index==length) lastBlinkingSpeed else speed)
            if (index != length) {
                index++
                if (index > length) {
                    index = length
                }
            }
            if(iShowLastBlinking){
                cursorBlinking = !cursorBlinking
            }
        }
    }
}
