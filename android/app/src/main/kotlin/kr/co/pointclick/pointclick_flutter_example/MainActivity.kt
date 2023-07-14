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
        val buttonTemplateIdx: Int? = call.argument<Int>("buttonTemplateIdx")
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

        /**
         * Call showOfferwall() API(Required)
         */
        /**
         * 또는 버튼 색상을 바꾸고 싶은 경우. 단, 버튼의 색상은 아래의 5가지중 선택하실 수 있습니다.
         * BUTTON_TEMPLATE.OFFERWALL_BTN_PRIMARY (기본값)
         * BUTTON_TEMPLATE.OFFERWALL_BTN_GRREN
         * BUTTON_TEMPLATE.OFFERWALL_BTN_PURPLE
         * BUTTON_TEMPLATE.OFFERWALL_BTN_RED
         * BUTTON_TEMPLATE.OFFERWALL_BTN_YELLOW
         * context : 매체앱의 액티비티 객체
         * title : 오퍼월 화면 상단에 보여지는 타이틀
         * btnTemplate : 오퍼월 버튼 템플릿 인덱스
         */
        PointClickAd.showOfferwall(activity, title, buttonTemplateIdx!!)
    }
}
