@file:OptIn(ExperimentalResourceApi::class)

package co.moregpt.ai.ui.conversation


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.zxy.core.db.SessionTagViewEntity
import com.zxy.core.db.SessionViewEntity
import composemultiplatformapigpt.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import toastShow


/**
 *   @desc content
 *   Created by zxy
 **/
//@Preview(showSystemUi = true)
@Composable
fun testMorePopup(){
    MorePopup(mutableListOf(), SessionViewEntity(0,"",0),{},{},{})
}

@Composable
fun MorePopup(list:List<SessionTagViewEntity>, session:SessionViewEntity, editTagConfirmClick:(String)->Unit,
              deleteConversationConfirmClick:(Int)->Unit,
              closeClick:()->Unit) {
    var isShowEditTag by remember { mutableStateOf(false) }
    var isShowDeleteConversation by remember { mutableStateOf(false) }

    Popup(alignment = Alignment.TopEnd, offset = IntOffset(0, 150)) {
        Card(
            backgroundColor = co.moregpt.ai.ui.AppColor.Background2,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.width(120.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isShowEditTag = !isShowEditTag
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(painterResource(Res.drawable.ic_tag), "修改标签", modifier = Modifier.padding(10.dp).size(25.dp))
                    Text(text = "修改标签", color = co.moregpt.ai.ui.AppColor.Text)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isShowDeleteConversation = !isShowDeleteConversation
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(painterResource(Res.drawable.ic_delete), "删除会话", modifier = Modifier.padding(10.dp).size(25.dp))
                    Text(text = "删除会话", color = co.moregpt.ai.ui.AppColor.Text)
                }
            }
        }
    }
    if (isShowEditTag) {
        EditTagDialog(list,curTag = session.tag, onCancel = {
            isShowEditTag = false
        }, onConfirm = {
            editTagConfirmClick.invoke(it)
            toastShow("修改成功")
            isShowEditTag = false
        })
    }
    if (isShowDeleteConversation) {
        DeleteConversationDialog(
            id = session.id.toInt(),
            title = (if (session.title.isEmpty()) "${stringResource(Res.string.new_conversation)}${session.id}" else session.title),
            onCancel = { isShowDeleteConversation = false}
        ) {
            deleteConversationConfirmClick.invoke(it)
            toastShow("删除成功")
            isShowDeleteConversation = false
        }
    }
}
