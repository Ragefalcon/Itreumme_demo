package ru.ragefalcon.tutatores.ui.time

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.SimpleItemAnimator
import ru.ragefalcon.sharedcode.models.data.ItemVxod
import ru.ragefalcon.tutatores.adapter.unirvadapter.UniRVAdapter
import ru.ragefalcon.tutatores.adapter.unirvadapter.formUniRVItemList
import ru.ragefalcon.tutatores.adapter.unirvadapter.rvitems.VxodRVItem
import ru.ragefalcon.tutatores.commonfragments.*
import ru.ragefalcon.tutatores.databinding.FragmentVxodTabBinding
import ru.ragefalcon.tutatores.extensions.getSFM
import ru.ragefalcon.tutatores.extensions.showAddChangeFragDial
import java.util.*

class VxodTabFragment : BaseFragmentVM<FragmentVxodTabBinding>(FragmentVxodTabBinding::inflate) {

    private var rvmAdapter = UniRVAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    var selItem: ItemVxod? by instanceState()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuPopup = MyPopupMenuItem<ItemVxod>(this, "VxodDelChange").apply {
            addButton(MenuPopupButton.DELETE) {
                viewmodel.addTime.delVxod(it.id.toLong()){}
            }
            addButton(MenuPopupButton.CHANGE) {
                showAddChangeFragDial(TimeAddVxodPanelFragment(it))
            }
        }
        with(binding) {
            with(rvVxodList) {
                layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
//            rvmAdapter = RVMainAdapter(R.layout.itemVxod, { VxodItemViewHolder(it, stateViewModel,viewmodel)}, null)
//            { coordin, pos ->
//                val originalPos = IntArray(2)
//                rvVxodList.getLocationInWindow(originalPos)
//                if (coordin-originalPos[1]<0) this.smoothScrollToPosition(pos)
////                (rvVxodList.layoutManager as LinearLayoutManager).smoothScrollToPosition(this,RecyclerView.State(),pos)
////                (rvVxodList.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(it,0)
////                smoothScrollBy(rvVxodList.findViewHolderForItemId(it.toLong()).adapterPosition) smoothScrollToPosition(it)
//            } }, null)
                adapter = rvmAdapter
//            addItemDecoration(divider)

            }
//            rvmAdapter.selectedItem.observe(viewLifecycleOwner) {
//                selItem = it?.getItem(ItemVxod::class) // ?.getData() as ItemVxod
////                it?.let {
////                    selItem = it.getData() as ItemVxod
////                }
//                Log.d("MyTut", "selVxod: $selItem and ${it?.getData()}");
//            }
            with(viewmodel) {
                timeSpis.spisVxod.observe(viewLifecycleOwner) {
                    rvmAdapter.updateData(formUniRVItemList(it) { item ->
                        VxodRVItem(item,
                            listener = {
                                selItem = it
                            },
                            longTapListener = {
                                menuPopup.showMenu(it, name = it.name)
                            },
                            recyclerView = rvVxodList)
                    })
                    selItem?.let {
                        rvmAdapter.setSelectItem(it, VxodRVItem::class) //VxodViewHolder
                    }
                }
            }
            /**
             * строчка ниже нужна, чтобы не происходило мерцания при обновлении Item-а,
             * она отключает анимацию по умолчанию при вызове метода onBind (если я не путаю)
             * */
            (rvVxodList?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

            buttAddVxod.setOnClickListener {
                stateViewModel.tmpTimeStampLong = Calendar.getInstance().time.time
                Log.d("MyTut", "timeStamp: ${Calendar.getInstance().time.time}");
                showAddChangeFragDial(TimeAddVxodPanelFragment(), getSFM())
//            val directions = TimeMainScreenDirections.actionTimeMainScreenToAddvxodpanel()
//            findNavController().navigate(directions)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            VxodTabFragment().apply {
            }
    }
}