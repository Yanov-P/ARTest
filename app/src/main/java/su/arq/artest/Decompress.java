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

    public Decompress(String zipFile, String location) {
        _zipFile = zipFile;
        _location = location;

        _dirChecker("");
    }

    public void unzip() {
        try  {
            FileInputStream fin = new FileInputStream(_zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                Log.d("myModelManager", "Unzipping " + ze.getName());

                if(ze.isDirectory()) {
                    Log.d("myModelManager", "ze.isDirectory ");

                    _dirChecker(ze.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(_location + ze.getName());
                    Log.d("myModelManager", "ze.isDirectory false");

                    for (int c = zin.read(); c != -1; c = zin.read()) {

//                        Log.d("myModelManager", "for cycle c=" + Integer.toString(c) );

                        fout.write(c);
                    }
                    Log.d("myModelManager", "for ended");

                    zin.closeEntry();
                    fout.close();
                }

            }
            zin.close();
        } catch(Exception e) {
            Log.e("myModelManager", "unzip", e);
            Log.d("myModelManager error:", "unzip", e);
        }

    }

    private void _dirChecker(String dir) {
        File f = new File(_location + dir);

        if(!f.isDirectory()) {
            f.mkdirs();
        }
    }

     void extractZipFile() {

        try {
            BufferedOutputStream dest = null;
            ZipInputStream zis = new ZipInputStream(new FileInputStream(_zipFile));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File file = new File(_location + entry.getName());


                if (file.exists()) {
//                    textView.append(“\n” + file.getAbsolutePath() + “\texists”);
                    Log.d("myModelManager","\n" + file.getAbsolutePath() + "\texists");
                    continue;
                }
                if (entry.isDirectory()) {
                    if (!file.exists())
                        file.mkdirs();
//                    textView.append(“\nCreate directory: “
//                    + file.getAbsolutePath());
                    Log.d("myModelManager","\nCreate directory: " + file.getAbsolutePath());

                    continue;
                }
//                textView.append(“\nExtracting:” + entry);
                Log.d("myModelManager","\nExtracting:" + entry);
                int count;
                byte data[] = new byte[DEFAULT_BUFFER_SIZE];
//                textView.append(” to ” + file.getAbsolutePath());
                Log.d("myModelManager"," to " + file.getAbsolutePath());


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

//    String zipFile = Environment.getExternalStorageDirectory() + "/files.zip";
//    String unzipLocation = Environment.getExternalStorageDirectory() + "/unzipped/";
//
//    Decompress d = new Decompress(zipFile, unzipLocation);
//    d.unzip();