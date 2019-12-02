package com.brunix.wondermovie

import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PermissionRequester(private val activity: Activity, private val permission: String) {

    fun request(continuation: (Boolean) -> Unit) {
            Dexter
                .withActivity(activity)
                .withPermission(permission)
                .withListener(object : BasePermissionListener() {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        continuation(true)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        continuation(false)
                    }
                }
                ).check()
        }
}
