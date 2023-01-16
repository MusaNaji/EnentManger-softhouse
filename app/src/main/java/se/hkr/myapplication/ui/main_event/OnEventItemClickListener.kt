package se.hkr.myapplication.ui.main_event

import se.hkr.myapplication.ui.models.Event

interface OnEventItemClickListener {
    fun onEventItemClick(item: Event, position: Int)

}
