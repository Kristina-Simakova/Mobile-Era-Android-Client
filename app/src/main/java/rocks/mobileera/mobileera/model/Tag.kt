package rocks.mobileera.mobileera.model

import android.graphics.Color


class Tag {
    fun color(name: String): Int {

        when (name) {
            "Odin" -> return Color.rgb(187, 94, 125)
            "Freyja" -> return Color.rgb(135, 124, 176)
            "Thon" -> return Color.rgb(64, 127, 127)
            "Android" -> return Color.rgb(164, 198, 57)
            "iOS" -> return Color.rgb(95, 201, 248)
            "Cross-platform" -> return Color.rgb(179, 142, 248)
            "Productivity" -> return Color.rgb(255, 89, 124)
            "Mobile Web" -> return Color.rgb(140, 136, 124)
            "Security" -> return Color.rgb(69, 69, 69)
            "IoT" -> return Color.rgb(255, 158, 124)
            "AI" -> return Color.rgb(242, 213, 0)
            "Machine Learning" -> return Color.rgb(72, 31, 240)
            "Architecture" -> return Color.rgb(253, 151, 39)
            "Backend" -> return Color.rgb(43, 152, 240)
            "CI" -> return Color.rgb(205, 218, 73)
        }

        return Color.BLACK
    }
}