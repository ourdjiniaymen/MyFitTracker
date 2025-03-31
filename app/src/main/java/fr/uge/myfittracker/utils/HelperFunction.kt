package fr.uge.myfittracker.utils


class HelperFunction {
    companion object {
        fun durationFormat(durationInSeconds: Int): String {
            val minute: Int = durationInSeconds / 60
            val seconds: Int = durationInSeconds % 60
            return "${formatTwoPositions(minute)}:${formatTwoPositions(seconds)}"
        }

        fun countFormat(repetition : Int): String{
            return "${repetition}x"
        }

        private fun formatTwoPositions(value: Int): String {
            return value.toString().padStart(2, '0')
        }
    }
}