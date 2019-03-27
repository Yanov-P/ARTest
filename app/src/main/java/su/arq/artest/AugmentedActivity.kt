package su.arq.artest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.a.b.a.a.a.e
import android.content.Context.ACTIVITY_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.app.ActivityManager
import android.os.Build.VERSION_CODES
import android.os.Build
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.Gravity
import com.google.ar.sceneform.rendering.ModelRenderable




class AugmentedActivity : AppCompatActivity() {

    val TAG = "agag"

    private val MIN_OPENGL_VERSION = 3.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return
        }


        ModelRenderable.builder()
            .setSource(this, R.raw.andy)
            .build()
            .thenAccept({ renderable -> andyRenderable = renderable })
            .exceptionally(
                { throwable ->
                    val toast = Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                    null
                })

        setContentView(R.layout.activity_main)


    }

//    fun buildRenderable(sourceId: Int): ModelRenderable{
//        var renderable = ModelRenderable()
//        ModelRenderable.builder()
//            .setSource(this, sourceId)
//            .build().thenAccept {r : ModelRenderable ->  renderable = r}
//            .exceptionally { Toast.makeText(this, "Unable to load renderable $sourceId",Toast.LENGTH_SHORT).show()  }
//        return renderable
//    }

    fun checkIsSupportedDeviceOrFinish(activity: Activity): Boolean {

        val openGlVersionString = (activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
            .deviceConfigurationInfo
            .glEsVersion
        if (openGlVersionString.toDouble() < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later")
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG).show()
            activity.finish()
            return false
        }
        return true
    }

}
