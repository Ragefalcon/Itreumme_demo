package ru.ragefalcon.tutatores.adapter.unirvadapter.rvitems;

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.ragefalcon.sharedcode.extensions.roundToString
import ru.ragefalcon.sharedcode.models.data.ItemPrivsGoal
import ru.ragefalcon.tutatores.R
import ru.ragefalcon.tutatores.adapter.unirvadapter.*
import ru.ragefalcon.tutatores.databinding.ItemPrivPlanOrStapBinding
import ru.ragefalcon.tutatores.extensions.*
import java.util.*

class PrivsGoalRVItem(
    data: ItemPrivsGoal,
    recyclerView: RecyclerView,
    selectListener: ((ItemPrivsGoal) -> Unit)? = null,
    tapListener: ((ItemPrivsGoal) -> Unit)? = null,
    longTapListener: ((ItemPrivsGoal) -> Unit)? = null
) : BaseUniRVItem<ItemPrivsGoal>(
    data,
    getUniRVViewHolder(ItemPrivPlanOrStapBinding::inflate) { vh, item, rvset ->
        if (vh.binding is ItemPrivPlanOrStapBinding) {
            with(vh.binding) {

                fun sverItemOpis(sver: Boolean, anim: Boolean) {
                    sverOpis(recyclerView, rvset.position, textOpis, sver, anim)
                    if (textOpis.text != "") {
                        rotateElemItem(ivStatDp, sver, anim, 270F)
                        sverWidthElemItem(viewSvertext, !sver, anim, 200.dpToPx)
                    } else {
                        sverWidthElemItem(viewSvertext, true, false, 200.dpToPx)
                    }
                }

                textSumhour.text = item.hour.roundToString(1)
                if (item.stap != "0") {
//                    val str = "${item.nameprpl}${if(item.namestap!="") " -> [${item.namestap}]" else ""}"
                    textNameStap.text = "${item.name}\n[${item.namePlan}]"
                    textNamePlan.visibility = ViewGroup.GONE
                    textNameStap.visibility = ViewGroup.VISIBLE
                } else {
                    textNamePlan.text = item.name
                    textNamePlan.visibility = ViewGroup.VISIBLE
                    textNameStap.visibility = ViewGroup.GONE
                }
                progbarGotov.progress = (progbarGotov.max * item.gotov / 100).toInt()
                textOpis.text = item.opis
                when (item.vajn.toInt()) {
                    0 -> ivStatDp.setColorFilter(
                        ContextCompat.getColor(
                            vh.itemView.context,
                            R.color.colorStatTimeSquareTint_00
                        ), android.graphics.PorterDuff.Mode.MULTIPLY
                    )

                    1 -> ivStatDp.setColorFilter(
                        ContextCompat.getColor(
                            vh.itemView.context,
                            R.color.colorStatTimeSquareTint_01
                        ), android.graphics.PorterDuff.Mode.MULTIPLY
                    )

                    2 -> ivStatDp.setColorFilter(
                        ContextCompat.getColor(
                            vh.itemView.context,
                            R.color.colorStatTimeSquareTint_02
                        ), android.graphics.PorterDuff.Mode.MULTIPLY
                    )

                    3 -> ivStatDp.setColorFilter(
                        ContextCompat.getColor(
                            vh.itemView.context,
                            R.color.colorStatTimeSquareTint_03
                        ), android.graphics.PorterDuff.Mode.MULTIPLY
                    )
                    /**
                     * https://stackoverflow.com/questions/20121938/how-to-set-tint-for-an-image-view-programmatically-in-android/45571812#45571812
                     *
                     * У пользователя @Tad есть свой ответ в правильном направлении, но он работает только с API 21+.
                     * Чтобы установить оттенок на всех версиях Android, используйте ImageViewCompat:
                     * ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(yourTint));
                     * Обратите внимание, что yourTintв этом случае должен быть "цвет int". Если у вас есть ресурс цвета, например R.color.blue, вам нужно сначала загрузить цвет int:
                     * ContextCompat.getColor(context, R.color.blue);
                     * */
                }
                sverItemOpis(item.sver, false)
                ivStatDp.setOnClickListener {
                    item.sver = item.sver.not()
                    sverItemOpis(item.sver, true)
                    if (vh.itemView.isSelected) {
                        vh.bindItem?.let { rvset.selFunc(it) }
                    }
                }
                if (vh.itemView.isSelected) {
                    selectListener?.invoke(item)
                }
                vh.itemView.setOnClickListener { // } .setOnClickListener {
                    vh.bindItem?.let { rvset.selFunc(it) }
                    tapListener?.invoke(item)
                }
                vh.itemView.setOnLongClickListener {
                    vh.bindItem?.let { rvset.selFunc(it) }
                    longTapListener?.invoke(item)
                    true
                }
            }
        }
    }
)