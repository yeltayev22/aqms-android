package kz.yeltayev.aqms.utils

import java.math.BigDecimal
import java.math.RoundingMode

const val PRECISION = 0

fun BigDecimal.round(): BigDecimal {
    return this.setScale(PRECISION, RoundingMode.HALF_UP)
}

fun BigDecimal.toKiloPascal(): BigDecimal {
    return this.divide(BigDecimal(1000))
}


