package kz.yeltayev.aqms.module.place.widget

data class Filter(
    val id: Int,
    val name: String
)

const val TEMPERATURE = 1
const val HUMIDITY = 2
const val PRESSURE = 3
const val TGS2600 = 4
const val TGS2602 = 5

const val HOURS_IN_DAY = 24
const val DAYS_IN_WEEK = 7
const val DAYS_IN_MONTH = 30
