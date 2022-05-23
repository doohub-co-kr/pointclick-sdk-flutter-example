package kr.co.pointclick.pointclick_flutter_example

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugins.GeneratedPluginRegistrant
import kr.co.pointclick.sdk.offerwall.core.PointClickAd
import kr.co.pointclick.sdk.offerwall.core.events.PackageReceiver


class MainActivity: FlutterActivity() {

    private lateinit var packageReceiver: PackageReceiver

    private val handler =
        MethodCallHandler { methodCall: MethodCall, result: MethodChannel.Result ->
            when (methodCall.method) {
                "showOfferwall" -> showOfferwall(methodCall)
                else -> result.notImplemented()
            }
        }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        Log.d("TAG", "configureFlutterEngine: Start")

        // fdsafd
        GeneratedPluginRegistrant.registerWith(flutterEngine)
        val channel = MethodChannel(flutterEngine.dartExecutor, "flutter_point_click")
        channel.setMethodCallHandler(handler)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            if(packageReceiver == null) {
                val intentFilter = IntentFilter()
                intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED)
                intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED)
                intentFilter.addDataScheme("package")
                packageReceiver = PackageReceiver()
                applicationContext.registerReceiver(packageReceiver, intentFilter)
            }
        } catch (e: Exception) {
            e.printStackTrace();
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if(packageReceiver != null) {
            applicationContext.unregisterReceiver(packageReceiver);
        }
    }

    private fun showOfferwall(call: MethodCall) {
        val title: String? = call.argument<String>("title")
        val pickerUid: String? = call.argument<String>("pickerUid")

        PointClickAd.init("d00e7d4e-29d5-4884-a409-c1e12b3ffbf7", pickerUid)

        /**
         * Create UserInfo Object(Optional)
         */
//        val userInfo = UserInfo.UserInfoBuilder(userName)
//            .setAge(userAge)
//            .setGender(0)
//            .setPhoneNumber("01011112222")
//            .build()
//
//        PointClickAd.setUserInfo(userInfo)

        PointClickAd.showOfferwall(activity, title)
    }
}
