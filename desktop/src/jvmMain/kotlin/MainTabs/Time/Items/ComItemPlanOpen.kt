package MainTabs.Time.Items

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import common.*
import extensions.*
import ru.ragefalcon.sharedcode.extensions.MyColorARGB
import ru.ragefalcon.sharedcode.extensions.roundToStringProb
import ru.ragefalcon.sharedcode.models.data.ItemPlan
import ru.ragefalcon.sharedcode.viewmodels.MainViewModels.EnumData.TypeStatPlan

@Composable
fun ComItemPlanOpen(
    item: ItemPlan,
    closeStaps: () -> Unit = {},
    itemPlanStyleState: ItemPlanStyleState,
    stapsContent: @Composable ColumnScope.() -> Unit = {},
) {
        val progressGotov = remember { mutableStateOf((item.gotov / 100f).toFloat()) }
    with(itemPlanStyleState) {

            MyCardStyle1(
                false,
                0,
                backBrush = when (item.stat) {
                    TypeStatPlan.COMPLETE -> background_brush_gotov
                    TypeStatPlan.CLOSE -> if (item.direction) background_brush_direction_close else background_brush_close
                    TypeStatPlan.FREEZE -> if (item.direction) background_brush_direction_freeze else background_brush_freeze
                    TypeStatPlan.UNBLOCKNOW -> background_brush_unblock
                    else -> if (item.direction) background_brush_direction else null
                },
                borderBrush = when (item.stat) {
                    TypeStatPlan.COMPLETE -> border_brush_gotov
                    TypeStatPlan.CLOSE -> if (item.direction) border_brush_direction_close else border_brush_close
                    TypeStatPlan.FREEZE -> if (item.direction) border_brush_direction_freeze else border_brush_freeze
                    TypeStatPlan.UNBLOCKNOW -> border_brush_unblock
                    else -> if (item.direction) border_brush_direction else null
                },
                modifierThen = if (item.namequest == "") Modifier else Modifier.border(
                    width = BORDER_WIDTH_QUEST,
                    brush = border_quest,
                    shape = shapeCard
                ),
//                dropMenu = { exp -> dropMenu(item, exp, {}) },
                styleSettings = itemPlanStyleState
            ) {
                Column {
                    if (item.namequest != "") {
                        MyShadowBox(quest_plate.shadow){
                            RowVA(
                                Modifier.fillMaxWidth()
                                    .padding(horizontal = 10.dp)
                                    .withSimplePlate(quest_plate)
                                    .padding(3.dp)
                                    .padding(horizontal = 2.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    item.namequest,
                                    Modifier.weight(1f, false),
                                    style = questNameText
                                )
                            }
                        }
                    }

                    Row(Modifier.clickable {
                        closeStaps()
                    },verticalAlignment = Alignment.CenterVertically) {
                        Box {
                            Image(
                                painterResource("bookmark_01.svg"), //BitmapPainter(
//                                bitmap = useResource("ic_stat_00.png", ::loadImageBitmap), //BitmapPainter(
                                "statDenPlan",
                                Modifier
                                    .height(54.dp)
                                    .width(62.dp)
//                                    .padding(start = 7.dp, end = 5.dp)
//                                    .padding(top = 2.dp, bottom = 2.dp)
                                    .padding(horizontal = 9.dp, vertical = 5.dp)
                                ,
                                colorFilter = ColorFilter.tint(
                                    when (item.vajn.toInt()) {
                                        0 -> MyColorARGB.colorStatTimeSquareTint_00.toColor()
                                        1 -> MyColorARGB.colorStatTimeSquareTint_01.toColor()
                                        2 -> MyColorARGB.colorStatTimeSquareTint_02.toColor()
                                        3 -> MyColorARGB.colorStatTimeSquareTint_03.toColor()
                                        else -> MyColorARGB.colorStatTimeSquareTint_00.toColor()
                                    },
                                    BlendMode.Modulate
                                ),
                                contentScale = ContentScale.FillBounds,
//                                filterQuality = FilterQuality.High
                            )
                        }
                        Column(
                            Modifier
                                .padding(
                                    top = if (item.namequest == "") 5.dp else 0.dp,
                                    start = 3.dp,
                                    bottom = 5.dp,
                                    end = 8.dp
                                )
                                .weight(1f)
                        ) {
                            RowVA {
                                Text(
                                    text = item.name,
                                    modifier = Modifier.padding(0.dp),
                                    style = mainTextStyle
                                )
                                if (item.countstap > 0) Text(
                                    text = "(${item.open_countstap}/${item.countstap})",
                                    modifier = Modifier.padding(0.dp)
                                        .padding(start = 3.dp),
                                    style = countStapText
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .padding(top = if (item.namequest == "") 3.dp else 0.dp, bottom = 3.dp, end = 13.dp)
                                .width(170.dp)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    modifier = Modifier.padding(start = 0.dp),
                                    text = "${item.hour.roundToStringProb(1)} ч.",
                                    style = hourTextStyle
//                                            TextStyle(color = Color(0xFFFFF7F9)),
//                                    fontSize = 15.sp
                                )
                            }
                                LinearProgressIndicator(
                                    progress = (item.gotov / 100f).toFloat(),
                                    modifier = Modifier.padding(top = 10.dp),//.fillMaxWidth(),
                                    sliderThumb,
                                    sliderInactive
                                )
                        }
                    }
                    stapsContent()
                }
            }
        }
    }



