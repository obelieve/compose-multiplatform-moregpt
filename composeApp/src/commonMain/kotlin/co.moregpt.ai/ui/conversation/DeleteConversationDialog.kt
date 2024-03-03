package co.moregpt.ai.ui.conversation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import composemultiplatformapigpt.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalResourceApi::class)
@Composable
fun DeleteConversationDialog(id:Int,title:String,onCancel: () -> Unit, onConfirm: (Int) -> Unit) {
    AlertDialog(
        textContentColor = co.moregpt.ai.ui.AppColor.Text,
        titleContentColor = co.moregpt.ai.ui.AppColor.Text,
        containerColor = co.moregpt.ai.ui.AppColor.Background2,
        onDismissRequest = { onCancel.invoke() },
        title = { Text(text = "删除会话") },
        text = {
            Text(text = title)
        },
        confirmButton = {
            Button(colors = ButtonDefaults.buttonColors(
                containerColor = co.moregpt.ai.ui.AppColor.Main
            ),
            onClick = {
                onConfirm.invoke(id)
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

//@Preview
@Composable
fun DeleteConversationDialogPreview() {
    DeleteConversationDialog(0,"",{}) {}
}