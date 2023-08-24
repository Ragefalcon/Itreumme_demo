package MainTabs

import MainTabs.Avatar.*
import MyDialog.MyDialogLayout
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.EnumDiskretSeekBar
import ru.ragefalcon.sharedcode.viewmodels.MainViewModels.EnumData.tabElement
import viewmodel.MainDB

class MainAvatarTabs(val dialLay: MyDialogLayout) {

    init {
        println("init MainAvatarTabs: $this")
    }

//    val dialLay = MyDialogLayout()
    val bestDays = BestDaysPanel()
    val goals = GoalsPanel()
    val characteristics = CharacteristicsPanel()
    val dream = DreamsPanel()
    val skill = SkillsPanel() //JLJLKJLKJ()
    val skillTree = TreeSkillsPanel() //JLJLKJLKJ()

    enum class AvatarTabsEnum(override val nameTab: String) : tabElement {
        Characteristics("Характеристики"),
        Goals("Цели"),
        Skills("Навыки"),
        Dreams("Мечты");
    }

    val dreamsGoalsSeekBar =
        EnumDiskretSeekBar(AvatarTabsEnum::class, AvatarTabsEnum.Goals)
//    val dreamsGoalsSeekBar =
//        DiskretSeekBar(
//            listOf(
//                "Характеристики" to "Characteristics",
//                "Цели" to "Goals",
//                "Навыки" to "Skills",
//                "Мечты" to "Dreams"),
//            "Characteristics"
//        ) {}

    @Composable
    fun show() {
        LaunchedEffect(MainDB){
            MainDB.setAvatarDreamGoalListener()
        }
        Box {
            Row {
                Column(Modifier.fillMaxWidth(0.65f).padding(start = 10.dp)) {
                    dreamsGoalsSeekBar.show(Modifier.fillMaxWidth().padding(bottom = 0.dp),MainDB.styleParam.avatarParam.seekBarStyle)
                    when (dreamsGoalsSeekBar.active) {
                        AvatarTabsEnum.Characteristics -> characteristics.show(dialLay, Modifier.fillMaxWidth())
                        AvatarTabsEnum.Goals -> goals.show(dialLay, Modifier.fillMaxWidth()) //0.5f
//                        AvatarTabsEnum.Skills -> skill.show(dialLay, Modifier.fillMaxWidth())
                        AvatarTabsEnum.Skills -> skillTree.show(dialLay, Modifier.fillMaxWidth())
                        AvatarTabsEnum.Dreams -> dream.show(dialLay, Modifier.fillMaxWidth())
                    }
                }
                Spacer(Modifier.width(10.dp))
                bestDays.show(dialLay, Modifier.fillMaxWidth())
            }
//            dialLay.getLay()
        }
    }
}