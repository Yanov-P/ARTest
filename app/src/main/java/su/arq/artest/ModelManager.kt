package su.arq.artest

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.google.ar.sceneform.rendering.ModelRenderable
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import java.io.*
import java.net.URL
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class ModelManager {

    val TAG = "myModelManager"
    var renderable: ModelRenderable? = null
    var zipFile: File? = null
    var zipFileName: String? = null

    fun createRenderable(context: Context, resId: Int){
        ModelRenderable.builder()
            .setSource(context, resId)
            .build()
            .thenAccept{ r: ModelRenderable ->
                Toast.makeText(context, "ModelManager Loaded",Toast.LENGTH_SHORT).show()
                renderable = r
            }
            .exceptionally(
                { _: Throwable ->
                    Toast.makeText(context, "Unable to load andy renderable", Toast.LENGTH_LONG).show()
                    null
                })
    }

    fun loadZipFile(context: Context, name: String){

        doAsync {

            val zFile = File(context.cacheDir, name)
            val result = URL("https://api.ari.arq.su/model/dodge_test").openStream().use{
                it.copyTo(zFile.outputStream())
                zipFile=zFile
            }
            Log.d(TAG, "download started")
            onComplete {
//                Log.d(TAG, "result = $result")
                Log.d(TAG, "zipFile = $zipFile")
//                Log.d(TAG, "zipFileName=$zipFileName")
                //                zipFile = File(context.cacheDir, name)
                unzip(context, name)
            }
        }

    }

    private fun unzip(context: Context, name: String){

        val file = zipFile.toString()
        val directory = zipFile?.parent + '/'

        Log.d(TAG,"zipFile.toString() $file")
        Log.d(TAG,"directory $directory")

        doAsync {
            Log.d(TAG,"unzip started")
            val decompress = Decompress(file, directory)
            decompress.extractZipFile()
//
//            val zis = ZipInputStream(BufferedInputStream(FileInputStream(file)))
//            var ze: ZipEntry
//            var count = 0
//            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
//            //Log.d(TAG,"zis.nextEntry="+zis.nextEntry)
//            ze = zis.nextEntry
//            while( ze != null){
//                Log.d("myModelManager","entered  while")
//
//                var fileName = ze.name
//                fileName = fileName.substring(fileName.indexOf("/") + 1)
//                Log.d("myModelManager","fileName=$fileName" )
//                val file = File(directory, fileName)
//                val dir = if (ze.isDirectory) file else file.parentFile
//
//                if(!dir.isDirectory && !dir.mkdirs())
//                    throw FileNotFoundException("invalid path: " + dir.absolutePath)
//                if(ze.isDirectory) continue
//
//                val fout = FileOutputStream(file)
//                try{
//                    while(zis.read(buffer) != -1) {
//                        count = zis.read(buffer)
//                        fout.write(buffer,0, count)
//                        Log.d("myModelManager", "buffer:$buffer")
//                    }
//
//
//                }finally{
//                    fout.close()
//                }
//                ze = zis.nextEntry
//
//            }

//            Decompress(file, directory ).unzip()

            onComplete {
                Log.d(TAG,"unzipped")
            }
        }

    }
}


//    String zipFile = Environment.getExternalStorageDirectory() + "/files.zip";
//    String unzipLocation = Environment.getExternalStorageDirectory() + "/unzipped/";
//
//    Decompress d = new Decompress(zipFile, unzipLocation);
//    d.unzip();