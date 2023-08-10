package com.sula.sport7

import android.app.Application
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp


const val ONESIGNAL_APP_ID = "b29fb969-ec61-4467-9b1d-b703336a0560"


@HiltAndroidApp
class IndogpoApp:Application() {
    override fun onCreate() {
        super.onCreate()

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

        OneSignal.promptForPushNotifications();
    }
}