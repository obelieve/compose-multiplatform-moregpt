@file:OptIn(ExperimentalResourceApi::class)

package co.moregpt.ai.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.zxy.core.db.SessionViewEntity
import composemultiplatformapigpt.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


/**
 *   @desc content
 *   Created by zxy
 **/
//@Preview(showSystemUi = true)
@Composable
fun testMainTemplateList2() {
    MainTemplateList(Modifier, mutableListOf(),{ a, b->},{})
}
@ExperimentalResourceApi
fun getChatCategoryBeanList():MutableList<com.zxy.core.model.local.ChatCategoryBean>{
    val list:MutableList<com.zxy.core.model.local.ChatCategoryBean> = mutableListOf()
    list.apply {
        add(
            com.zxy.core.model.local.ChatCategoryBean(
                Res.drawable.ic_tag_education, "教育",
                mutableListOf<String>().apply {
                    this@apply.add("科学谈话")
                    this@apply.add("英语教师")
                    this@apply.add("翻译")
                })
        )
        add(
            com.zxy.core.model.local.ChatCategoryBean(
                Res.drawable.ic_tag_work, "工作",
                mutableListOf<String>().apply {
                    this@apply.add("面试问题")
                    this@apply.add("职业顾问")
                    this@apply.add("日报")
                    this@apply.add("周报")
                    this@apply.add("月报")
                })
        )
        add(
            com.zxy.core.model.local.ChatCategoryBean(Res.drawable.ic_tag_book, "书籍",
                mutableListOf<String>().apply {
                    this@apply.add("书籍推荐")
                })
        )
        add(
            com.zxy.core.model.local.ChatCategoryBean(Res.drawable.ic_tag_art, "艺术",
                mutableListOf<String>().apply {
                    this@apply.add("诗歌生成器")
                    this@apply.add("写一集《南方公园》")
                    this@apply.add("电影和电视剧评论家")
                })
        )
        add(
            com.zxy.core.model.local.ChatCategoryBean(Res.drawable.ic_tag_email, "电子邮件",
                mutableListOf<String>().apply {
                    this@apply.add("写一封促进销售的电子邮件")
                    this@apply.add("电子简报模版")
                    this@apply.add("给愤怒的客户的邮件回复")
                    this@apply.add("电子邮件回复器（友善的/专业的）")
                    this@apply.add("群发市场的营销电子邮件")
                })
        )
    }
    return list
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun MainTemplateList(modifier: Modifier, recentlySessions:List<SessionViewEntity>, startChatClick:(String?, Int?)->Unit, lookAllClick:()->Unit) {
    val selectedRowIndex = remember {
        mutableStateOf(0)
    }

    Box(modifier = modifier){
        val list = getChatCategoryBeanList()
        LazyColumn {
            item {
                Spacer(modifier = Modifier.padding(top = 20.dp))
                MainRecentlyList(recentlySessions,{msg,id->
                    startChatClick.invoke(msg,id)
                },lookAllClick)
                Text(text = "聊天人工智能", color = co.moregpt.ai.ui.AppColor.Main, modifier = Modifier.padding(top = 20.dp, bottom = 5.dp))
                Text(text = "可以帮助您：", color = co.moregpt.ai.ui.AppColor.Text2, modifier = Modifier.padding(bottom = 10.dp))
            }
            item {
                FlowRow{
                    for(it in 0 until list.size) {
                        val item = list[it]
                        Row {
                            Card(border = BorderStroke(1.dp,if (selectedRowIndex.value == it) co.moregpt.ai.ui.AppColor.StrokeSelectedCategory else co.moregpt.ai.ui.AppColor.Background2),
                                onClick = {
                                    selectedRowIndex.value = it
                                }, shape = RoundedCornerShape(15.dp), colors = CardDefaults.cardColors(
                                    containerColor = if (selectedRowIndex.value == it) co.moregpt.ai.ui.AppColor.SelectedCategory else co.moregpt.ai.ui.AppColor.Background2
                                )
                            ) {
                                Row(modifier = Modifier
                                    .padding(start = 12.dp, end = 12.dp)
                                    .height(40.dp)) {
                                    Image(modifier= Modifier
                                        .width(20.dp)
                                        .wrapContentHeight()
                                        .align(Alignment.CenterVertically),contentScale = ContentScale.FillWidth,painter = painterResource(item.icon), contentDescription = "")
                                    Text(text = item.title, modifier = Modifier
                                        .padding(start = 8.dp)
                                        .align(Alignment.CenterVertically),if(selectedRowIndex.value==it) co.moregpt.ai.ui.AppColor.SelectedTab else co.moregpt.ai.ui.AppColor.UnSelectedTab)
                                }
                            }
                            Spacer(modifier = Modifier.padding(end = 10.dp))
                        }
                    }
                }

            }
            val subItems = list[selectedRowIndex.value].items
            items(subItems.size){
                val subItem = subItems[it]
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = if (it == 0) 10.dp else 0.dp,
                        start = 0.dp,
                        end = 0.dp,
                        bottom = 10.dp
                    )
                    .height(50.dp).clickable {
                        startChatClick.invoke(subItem,null)
                    },colors= CardDefaults.cardColors(containerColor = co.moregpt.ai.ui.AppColor.Background2)
                    ,shape = RoundedCornerShape(15.dp)
                ) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()){
                        Text(modifier = Modifier.align(Alignment.Center), text = subItem, color = if(selectedRowIndex.value==it) co.moregpt.ai.ui.AppColor.SelectedTab else co.moregpt.ai.ui.AppColor.UnSelectedTab)
                    }
                }
            }

        }
    }
}