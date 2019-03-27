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
import android.view.MotionEvent
import com.google.ar.core.Anchor
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URI


class AugmentedActivity : AppCompatActivity() {

    val TAG = "agag"

    private val MIN_OPENGL_VERSION = 3.0

    var andyRenderable: ModelRenderable? = null

    lateinit var arFragment: ArFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return
        }

        ModelRenderable.builder()
            .setSource(this, R.raw.cube)
            .build()
            .thenAccept{ renderable: ModelRenderable ->
                andyRenderable = renderable
                Toast.makeText(this, "Model Loaded",Toast.LENGTH_SHORT).show()
            }
            .exceptionally(
                { _: Throwable ->
                    val toast = Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                    null
                })

        setContentView(R.layout.activity_main)

        arFragment = (ar_ux_fragment as ArFragment)
        arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            Toast.makeText(this, "ArPlane tapped",Toast.LENGTH_SHORT).show()
            if(andyRenderable != null){
                val anchor = hitResult.createAnchor()
                val anchorNode = AnchorNode(anchor)
                anchorNode.setParent(arFragment.arSceneView.scene)

                val andy = TransformableNode(arFragment.transformationSystem)
                andy.setParent(anchorNode)
                andy.renderable = andyRenderable
            }
        }


    }



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
