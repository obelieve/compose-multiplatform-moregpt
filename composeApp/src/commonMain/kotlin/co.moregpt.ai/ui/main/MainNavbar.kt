@file:OptIn(ExperimentalResourceApi::class)

package co.moregpt.ai.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import composemultiplatformapigpt.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


/**
 *   @desc content
 *   Created by zxy
 **/
//@Preview(showSystemUi = true)
@Composable
fun testMainNavBar() {
    MainNavBar(Modifier, stringResource(Res.string.app_name),{})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavBar(modifier: Modifier, title:String, settingsClick: () -> Unit) {
    CenterAlignedTopAppBar(modifier = modifier,colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = co.moregpt.ai.ui.AppColor.Background), title = {
        Text(text = title, maxLines = 1, style = co.moregpt.ai.ui.AppTypography.titleMedium, color = co.moregpt.ai.ui.AppColor.Text)
    }, navigationIcon = {
        UpgradeIcon()
    }, actions = {
        Row{
            IconButton(onClick = {
                settingsClick.invoke()
            }) {
                Icon(Icons.Rounded.Settings, tint = Color.White, contentDescription = "more")
            }
        }
    })
}

@Composable
fun UpgradeIcon(){
    Row(modifier = Modifier
        .background(color = co.moregpt.ai.ui.AppColor.Background2, shape = RoundedCornerShape(26.dp))
        .padding(8.dp, 4.dp, 8.dp, 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(modifier = Modifier.size(26.dp), painter = painterResource(Res.drawable.ic_upgrade), contentDescription = "")
        Text(modifier = Modifier.padding(start = 4.dp), color = co.moregpt.ai.ui.AppColor.Text, text = "升级", style = co.moregpt.ai.ui.AppTypography.labelMedium)
    }
}