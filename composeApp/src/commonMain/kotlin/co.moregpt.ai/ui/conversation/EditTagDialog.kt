@file:OptIn(ExperimentalResourceApi::class)

package co.moregpt.ai.ui.conversation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import co.moregpt.ai.ui.settings.SessionTagRow
import com.zxy.core.db.SessionTagViewEntity
import composemultiplatformapigpt.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource


@Composable
fun EditTagDialog(list: List<SessionTagViewEntity>, curTag:String, onCancel: () -> Unit, onConfirm: (String) -> Unit) {
    var tag = ""

    AlertDialog(
        textContentColor = co.moregpt.ai.ui.AppColor.Text,
        titleContentColor = co.moregpt.ai.ui.AppColor.Text,
        containerColor = co.moregpt.ai.ui.AppColor.Background2,
        onDismissRequest = { onCancel.invoke() },
        title = { Text(text = "修改标签") },
        text = {
            DialogContent(list,curTag) {
                tag = it
            }
        },
        confirmButton = {
            Button(colors = ButtonDefaults.buttonColors(
                containerColor = co.moregpt.ai.ui.AppColor.Main
            ),onClick = {
                onConfirm.invoke(tag)
            }) {
                Text(text = stringResource(Res.string.dialog_confirm))
            }
        },
        dismissButton = {
            Button(colors = ButtonDefaults.buttonColors(
                containerColor = co.moregpt.ai.ui.AppColor.Background2
            ),onClick = {
                onCancel.invoke()
            }) {
                Text(text = stringResource(Res.string.dialog_cancel))
            }
        },
    )
}

@Composable
private fun DialogContent(list: List<SessionTagViewEntity>, curTag:String, onChangeText: (String) -> Unit) {
    Box {
        val tag = list.findLast { it.id==curTag }
        val index = if(tag!=null){
            if(list.indexOf(tag)==-1) 0 else list.indexOf(tag)
        }else{
            0
        }
        SessionTagRow(list, index,true,onChangeText)
    }
}

//@Preview
@Composable
fun ApiKeyEditDialogPreview() {
    EditTagDialog(mutableListOf(),"",{}, {})
}