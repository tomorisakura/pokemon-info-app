package grevi.msx.poketest.Static

import android.view.View

class OnItemClicked(val position : Int, val onItemClicked : OnItemCLickCallBack) : View.OnClickListener {

    interface OnItemCLickCallBack {
        fun onItemCLicked(view : View?, position : Int)
    }

    override fun onClick(p0: View?) {
        onItemClicked.onItemCLicked(p0, position)
    }
}