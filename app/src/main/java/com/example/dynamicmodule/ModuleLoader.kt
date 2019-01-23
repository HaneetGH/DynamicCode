package com.example.dynamicmodule

import android.util.Log
import dalvik.system.DexClassLoader
import java.io.File

class ModuleLoader(val cacheDir: String) {
    
    fun load(dex: File, cls: String = "com.example.testing"): String {

        try {
            val classLoader = DexClassLoader(dex.absolutePath, cacheDir,
                    null, this.javaClass.classLoader)

            val moduleClass = classLoader.loadClass(cls)
            val myInstance = moduleClass.newInstance()
            val doSomething = moduleClass.getMethod("newInstance")
            doSomething.invoke(myInstance)
           // if (IDynamicModule::class.java.isAssignableFrom(moduleClass)) {
                return moduleClass.newInstance().toString()+"";
         //   }
        } catch (e: Exception) {
            Log.e("ModuleLoader", e.message, e)
        }

        return  "Failed to load"
    }
}