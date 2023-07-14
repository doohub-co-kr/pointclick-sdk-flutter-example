package kr.co.pointclick.pointclick_flutter_example

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.util.Log
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import kr.co.pointclick.sdk.offerwall.core.PointClickAd
import kr.co.pointclick.sdk.offerwall.core.events.PackageReceiver

class FlutterPointClickPlugin : FlutterPlugin, MethodChannel.MethodCallHandler, ActivityAware {
    private val placementUidKey = "d00e7d4e-29d5-4884-a409-c1e12b3ffbf7"
    private val pickerUidKey = "test"
    private val showOfferwall: String = "showOfferwall"

    private lateinit var channel: MethodChannel
    private lateinit var packageReceiver: PackageReceiver
    private lateinit var context: Context
    private var activity: Activity? = null

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {

        Log.d("TAG", "onAttachedToEngine: Start")

        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_point_click")
        channel.setMethodCallHandler(this)

        context = flutterPluginBinding.applicationContext

        try {
            val intentFilter = IntentFilter()
            intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED)
            intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED)
            intentFilter.addDataScheme("package")
            packageReceiver = PackageReceiver()
            context.registerReceiver(packageReceiver, intentFilter)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: MethodChannel.Result) {
        println("call.method : " + call.method);
        when (call.method) {
            showOfferwall -> showOfferwall(call)
            else -> result.notImplemented()
        }
    }

    //TODO UserInfo에 대한 처리 추가해야 됨
    private fun showOfferwall(call: MethodCall) {
        val title: String? = call.argument<String>("title")
        val pickerUid: String? = call.argument<String>("pickerUid")
        val buttonTemplateIdx: Int? = call.argument<Int>("buttonTemplateIdx")
        PointClickAd.init(getPlacementId(), pickerUid)

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

    private fun getPlacementId(): String? {
        val appInfo = context.packageManager.getApplicationInfo(
            context.packageName,
            PackageManager.GET_META_DATA
        )
        val bundle = appInfo.metaData

        return bundle.getString(placementUidKey)
    }

    override fun onDetachedFromEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        flutterPluginBinding.applicationContext.unregisterReceiver(packageReceiver)
        channel.setMethodCallHandler(null)
    }

    override fun onDetachedFromActivityForConfigChanges() {
        this.activity = null
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        onAttachedToActivity(binding)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        this.activity = binding.activity
    }

    override fun onDetachedFromActivity() {
        this.activity = null
    }
}