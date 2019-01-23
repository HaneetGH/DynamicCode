package com.example.dthomas.dynamicallyloadcode

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.example.dynamicmodule.ModuleLoader

import org.apache.commons.io.FileUtils

import java.io.*
import java.net.URL
import android.content.Context.MODE_PRIVATE
import android.os.StrictMode
import android.os.StrictMode.setThreadPolicy






class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
       val file = File(Environment.getExternalStorageDirectory(), "/classes2.dex")
       downloadFile("http://technorapper.com/classes2.dex",file);
         loadDexFile(Environment.getExternalStorageDirectory().toString()
                 + "/classes2.dex")
    }

    fun sourceFile(name: String) = packageManager.getPackageInfo(packageName, 0)
            .applicationInfo.dataDir + "/files/" + name

    fun copyDexFile(name: String) = FileUtils.copyToFile(FileInputStream(File((Environment.getExternalStorageDirectory().toString()
            + "/classes2.dex").toString())), File(sourceFile(name)))
            .let { File(sourceFile(name)) }

    fun loadDexFile(name: String) = ModuleLoader(cacheDir.absolutePath).load(copyDexFile(name))

    private fun downloadFile(url: String, outputFile: File) {
        try {
            val u = URL(url)
            val conn = u.openConnection()
            val contentLength = conn.getContentLength()

            val stream = DataInputStream(u.openStream())

            val buffer = ByteArray(contentLength)
            stream.readFully(buffer)
            stream.close()

            val fos = DataOutputStream(FileOutputStream(outputFile))
            fos.write(buffer)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            return  // swallow a 404
        } catch (e: IOException) {
            return  // swallow a 404
        }

    }


}
