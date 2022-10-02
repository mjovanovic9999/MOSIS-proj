package mosis.streetsandtotems.core

import android.app.Application
import cat.ereza.customactivityoncrash.config.CaocConfig
import dagger.hilt.android.HiltAndroidApp
import mosis.streetsandtotems.R


@HiltAndroidApp
class StreetsAndTotemsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        CaocConfig.Builder.create()
            .errorDrawable(R.drawable.logo_only_tiki)
            .apply()
    }
}