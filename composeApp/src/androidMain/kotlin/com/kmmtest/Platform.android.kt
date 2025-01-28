package com.kmmtest

import android.content.Intent
import android.net.Uri
import com.kmmtest.commoninterface.DeviceConfig
import com.kmmtest.diimp.AndroidDeviceConfig
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.Module
import org.koin.dsl.module


actual val platformModule: Module = module {

    single<DeviceConfig> { AndroidDeviceConfig() }


}


class AndroidNavHandler : NavHandler, KoinComponent {

    private val contextProvider: ContextProvider by inject()

    override fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        contextProvider.getMainActivity().startActivity(intent)
    }

}

class AndroidContextProvider(private val mainActivity: MainActivity) : ContextProvider {
    override fun getMainActivity(): MainActivity {
        return mainActivity
    }

}


interface ContextProvider {
    fun getMainActivity(): MainActivity
}