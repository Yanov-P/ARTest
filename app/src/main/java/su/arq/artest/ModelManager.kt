package su.arq.artest

import android.content.Context
import com.google.ar.sceneform.rendering.ModelRenderable

class ModelManager(context: Context) {

    val context:Context
    var renderables : ArrayList<ModelRenderable>

    init {
        this.context=context
        this.renderables = arrayListOf()
    }

//
//    val TAG = "myModelManager"
//    var renderable: ModelRenderable? = null
//    var zipFile: File? = null
//    var zipFileName: String? = null

    private fun addRenderable(r:ModelRenderable){
        renderables.add(r)
    }

    fun createRenderable(resId: Int){
        ModelRenderable.builder()
            .setSource(context, resId)
            .build()
            .thenAccept{ r: ModelRenderable -> addRenderable(r)}

    }

    fun downloadAndCreateRenderable(url:String, filename:String){
        FileAssistant.downloadAndUnzipFileAsync(context.cacheDir.toString(),url,filename)
    }

}


//    String zipFile = Environment.getExternalStorageDirectory() + "/files.zip";
//    String unzipLocation = Environment.getExternalStorageDirectory() + "/unzipped/";
//
//    Decompress d = new Decompress(zipFile, unzipLocation);
//    d.unzipAndDelete();