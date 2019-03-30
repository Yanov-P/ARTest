package su.arq.artest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.app.ActivityManager
import android.app.Activity
import android.content.Context
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_main.*


class AugmentedActivity : AppCompatActivity() {

    private val MIN_OPENGL_VERSION = 3.0
    private lateinit var arFragment: ArFragment
    var myModelManager = ModelManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return
        }

        //    String zipFile = Environment.getExternalStorageDirectory() + "/files.zip";
//    String unzipLocation = Environment.getExternalStorageDirectory() + "/unzipped/";
//
//    Decompress d = new Decompress(zipFile, unzipLocation);
//    d.unzip();


        myModelManager.createRenderable(this, R.raw.cube)
        myModelManager.loadZipFile(this,"modelZip")

        setContentView(R.layout.activity_main)

        arFragment = (ar_ux_fragment as ArFragment)
        arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            Toast.makeText(this, "ArPlane tapped",Toast.LENGTH_SHORT).show()
            if(myModelManager.renderable != null){
                val anchor = hitResult.createAnchor()
                val anchorNode = AnchorNode(anchor)
                anchorNode.setParent(arFragment.arSceneView.scene)

                val andy = TransformableNode(arFragment.transformationSystem)
                andy.setParent(anchorNode)
                andy.renderable = myModelManager.renderable
            }
        }


    }


    private fun checkIsSupportedDeviceOrFinish(activity: Activity): Boolean {

        val openGlVersionString = (activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
            .deviceConfigurationInfo
            .glEsVersion
        if (openGlVersionString.toDouble() < MIN_OPENGL_VERSION) {
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG).show()
            activity.finish()
            return false
        }
        return true
    }

}
