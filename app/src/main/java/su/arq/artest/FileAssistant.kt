package su.arq.artest

import android.util.Log
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import java.io.File
import java.net.URL

class FileAssistant {
    companion object {
        val TAG = "FileAssistant"
        fun downloadAndUnzipFileAsync( directory: String, url:String, filename: String){

            doAsync {

                val zFile = File(directory, filename)
                Log.d(TAG, "download started")
                URL(url + filename).openStream().use{
                    it.copyTo(zFile.outputStream())
                }
                onComplete {
                    unzipAndDelete(zFile, directory )
                }
            }
        }

        fun unzipAndDelete(file:File, directory: String){
            Log.d(TAG, "unzipping file $file in directory $directory started")
            doAsync {
                val decompress = Decompress(file.toString(), "$directory/" )
                decompress.extractZipFile()
                onComplete {
                    Log.d(TAG, "unzipping $file completed")
                    Log.d(TAG, "$file deleted")
                    file.delete()
                }
            }
        }
    }


}

//"https://api.ari.arq.su/model/dodge_test"