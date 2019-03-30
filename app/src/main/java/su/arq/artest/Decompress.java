package su.arq.artest;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static kotlin.io.ConstantsKt.DEFAULT_BUFFER_SIZE;

/**
 *
 * @author jon
 */
public class Decompress {
    private String _zipFile;
    private String _location;
    private String TAG = "FileAssistant-Decompress";

    public Decompress(String zipFile, String location) {
        _zipFile = zipFile;
        _location = location;
    }

     void extractZipFile() {

        try {
            BufferedOutputStream dest;
            ZipInputStream zis = new ZipInputStream(new FileInputStream(_zipFile));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File file = new File(_location + entry.getName());


                if (file.exists()) {
                    Log.d(TAG,"\n" + file.getAbsolutePath() + "\texists");
                    continue;
                }
                if (entry.isDirectory()) {
                    if (!file.exists())
                        file.mkdirs();
                    Log.d(TAG,"\nCreate directory: " + file.getAbsolutePath());

                    continue;
                }
                Log.d(TAG,"\nExtracting:" + entry);
                int count;
                byte data[] = new byte[DEFAULT_BUFFER_SIZE];
                Log.d(TAG," to " + file.getAbsolutePath());


                FileOutputStream fos = new FileOutputStream(file);
                dest = new BufferedOutputStream(fos, DEFAULT_BUFFER_SIZE);
                while ((count = zis.read(data, 0, DEFAULT_BUFFER_SIZE)) != -1) {
                    dest.write(data, 0, count);
                }
                dest.flush();
                dest.close();
            }
            zis.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}
