package co.moregpt.ai.ui.settings


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zxy.core.db.SessionTagViewEntity
import composemultiplatformapigpt.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import toastShow


@OptIn(ExperimentalResourceApi::class)
@Composable
fun ConversationTagDialog(sessionTags:List<SessionTagViewEntity>, onCancel: () -> Unit, onConfirm: (String) -> Unit) {
    var tag by remember {
        mutableStateOf("")
    }

    AlertDialog(
        textContentColor = co.moregpt.ai.ui.AppColor.Text,
        titleContentColor = co.moregpt.ai.ui.AppColor.Text,
        containerColor = co.moregpt.ai.ui.AppColor.Background2,
        onDismissRequest = { onCancel.invoke() },
        title = { Text(text = "会话标签") },
        text = {
            DialogContent(sessionTags) {
                tag = it
            }
        },
        confirmButton = {
            Button(colors = ButtonDefaults.buttonColors(
                containerColor = co.moregpt.ai.ui.AppColor.Main
            ),onClick = {
                if (tag.isEmpty()) {
                    toastShow("标签不能为空")
                    return@Button
                }
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

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun DialogContent(sessionTags:List<SessionTagViewEntity>, addSessionTagClick: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Box {
        Column(modifier = Modifier) {
            val list = sessionTags
            SessionTagRow(list)
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(text = "新建标签",color = co.moregpt.ai.ui.AppColor.Text, style = co.moregpt.ai.ui.AppTypography.labelMedium)
            TextField(
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedTextColor = co.moregpt.ai.ui.AppColor.Text,
                    containerColor = co.moregpt.ai.ui.AppColor.Background2,
                    cursorColor = co.moregpt.ai.ui.AppColor.Main,
                    focusedIndicatorColor = co.moregpt.ai.ui.AppColor.Main
                ),
                value = text,
                onValueChange = {
                    text = it
                    addSessionTagClick.invoke(text)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
                    .align(Alignment.CenterHorizontally),
            )
        }
    }
}

//@Preview
@Composable
fun ConversationTagDialogPreview() {
    ConversationTagDialog(mutableListOf(),{}, {})
}