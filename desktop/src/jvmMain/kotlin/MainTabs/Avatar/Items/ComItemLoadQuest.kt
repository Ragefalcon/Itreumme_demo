package MainTabs.Avatar.Items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.*
import extensions.format
import ru.ragefalcon.sharedcode.models.data.ItemLoadQuest
import java.util.*

class ComItemLoadQuest(
    val item: ItemLoadQuest,
    val selection: SingleSelectionType<ItemLoadQuest>,
    val doubleClick: (ItemLoadQuest) -> Unit = {},
    val dropMenu: @Composable ColumnScope.(ItemLoadQuest, MutableState<Boolean>) -> Unit = { _, _ -> }
) {
    var expandedDropMenu = mutableStateOf(false)

    val expandedOpis = mutableStateOf(!item.sver)

    @Composable
    fun getComposable() {
        MyCardStyle1(selection.isActive(item), 0, {
            selection.selected = item
//            expandedDropMenu.value = this.buttons.isSecondaryPressed
        }, {
            doubleClick(item)
            expandedOpis.value = !expandedOpis.value
        }, dropMenu = { exp -> dropMenu(item, exp) }
        ) {
            Column {
                Row(
                    Modifier.padding(start = 15.dp).padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

//                    Image(
//                        bitmap = useResource("ic_stat_00.png", ::loadImageBitmap), //BitmapPainter(
//                        "statVxod",
//                        Modifier
//                            .height(50.dp)
//                            .width(50.dp),
//                        colorFilter = ColorFilter.tint(
//                            when (item.stat.toInt()) {
//                                0 -> Color(0xFF2FA61D)
//                                1 -> Color(0xFF7FFAF6)
//                                2 -> Color(0xFFFFF42B)
//                                3 -> Color(0xFFFFA825)
//                                4 -> Color(0xFFFF5858)
//                                else -> Color(0xFFFFF42B)
//                            },
//                            BlendMode.Modulate
//                        ),
////                    alpha = 0.5F,
//                        contentScale = ContentScale.FillBounds,
//                        filterQuality = FilterQuality.High
//                    )

                    Column(modifier = Modifier.padding(5.dp).padding(end = 10.dp).weight(1f)) {
                        Row {

                            Column(Modifier.padding(0.dp).weight(1f)) {
                                MyTextStyle1(
                                    modifier = Modifier.padding(start = 10.dp),
                                    text = item.name,
//                                    style = TextStyle(color = Color(0xFFFFF7D9)),
//                                    fontSize = 15.sp
                                )
                                Text(
                                    modifier = Modifier.padding(start = 10.dp),
                                    text = Date(item.dateopen).format("dd.MM.yyyy HH:mm"),
                                    style = TextStyle(color = Color(0xAFFFF7D9)),
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
//                    Spacer(Modifier.weight(1f))
                    if (selection.isActive(item)) MyButtDropdownMenuStyle1(
                        Modifier.padding(horizontal = 15.dp).padding(vertical = 5.dp), expandedDropMenu
                    ) { setDissFun ->
                        dropMenu(item, expandedDropMenu)
                    }
//                    if (item.opis != "") RotationButtStyle1(
//                        expandedOpis,
//                        modifier = Modifier
//                            .padding(horizontal = 10.dp)
//                            .padding(end = 20.dp)
//                    ) {
//                        item.sver = item.sver.not()
//                    }
                }
//                if ((item.opis != "")) { //(selection.selected == item) &&&&(expandedOpis.value)
//                    BoxExpand(
//                        expandedOpis,
//                        Modifier.myModWithBound1(),
//                        Modifier.fillMaxWidth()
//                    ) {  //, endModif = Modifier::withMyBound1
//                        Text(
//                            modifier = Modifier
//                                .padding(5.dp)
//                                .padding(start = 10.dp),
//                            text = item.opis,
//                            style = TextStyle(color = Color(0xFFFFF7D9)),
//                            fontSize = 15.sp
//                        )
//                    }
//                }
            }
        }
    }
}
