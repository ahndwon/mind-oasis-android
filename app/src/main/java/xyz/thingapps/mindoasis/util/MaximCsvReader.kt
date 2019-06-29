package xyz.thingapps.mindoasis.util

import com.opencsv.CSVReader
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import xyz.thingapps.mindoasis.model.Maxim
import java.io.InputStream
import java.io.InputStreamReader

class MaximCsvReader : AnkoLogger {
    var startIndex = 0
    var maximCount = 50

    fun read(inputStream: InputStream): ArrayList<Maxim> {
        val maximList = ArrayList<Maxim>()
        val reader = CSVReader(InputStreamReader(inputStream))
        reader.use {
            var count = startIndex
            while (count < maximCount) {
                count++
                val nextRecord = it.readNext()
                maximList.add(
                    Maxim(
                        nextRecord[0],
                        count,
                        nextRecord[1],
                        nextRecord[2],
                        nextRecord[3]
                    )
                )
                info("maxim : ${nextRecord[1]}, ${nextRecord[2]} , ${nextRecord[3]}")
            }
        }
        return maximList
    }

}