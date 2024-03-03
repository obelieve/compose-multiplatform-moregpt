package co.moregpt.ai.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import composemultiplatformapigpt.composeapp.generated.resources.Res
import getStatusBarHeight
import getVersionName
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 *   @desc content
 *   Created by zxy
 **/
@OptIn(ExperimentalResourceApi::class)
@Composable
fun AboutScreen(){
    val statusHeight = getStatusBarHeight()
    val statusHeightDp = with(LocalDensity.current) { statusHeight.toDp() }
    //val xx by  viewModel.xxLV.observeAsState(initial = xx)
    Box(
        modifier = Modifier
            .background(color = co.moregpt.ai.ui.AppColor.Background)
            .fillMaxSize()
            .padding(top = statusHeightDp)
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Image(painter = painterResource(Res.drawable.ic_logo), modifier = Modifier.size(65.dp).align(
                Alignment.CenterHorizontally), contentDescription = "")
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(text = "${stringResource(Res.string.app_name)} v${getVersionName()}",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = co.moregpt.ai.ui.AppColor.Text, style = co.moregpt.ai.ui.AppTypography.titleLarge)
        }
    }
}