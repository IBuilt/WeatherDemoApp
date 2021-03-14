package com.demo.lib.utils

object DegreeUtils {

    private val directions = arrayOf(
        "north",
        "northeast",
        "east",
        "southeast",
        "south",
        "southwest",
        "west",
        "northwest"
    )

    fun degreeToWindDirection(degree: Long): String {

        var calculations = degree


        calculations = calculations * 8 / 360


        // round to nearest integer.
        var index = calculations.toInt()


        // Ensure it"s within 0-7
        index = (index + 8) % 8


        return directions[index]
    }
}