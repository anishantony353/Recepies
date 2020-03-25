package com.anish.recepies.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService


object Utility {
    private val TAG = Utility::class.java.simpleName
    private const val isLogEnabled = true

    @JvmStatic
    fun log(TAG: String?, msg: String?) {
        if (isLogEnabled) {
            Log.i(TAG, msg)
        }
    }


    fun showToast(
        msg: String?,
        length: Int,
        context: Context?
    ) {
        Toast.makeText(context, msg, length).show()
    }

    /*fun isNetworkAvailable(context:Context): Boolean {
        val cm =
            getSystemService<Any>(context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
    }*/
}