import 'dart:io';
import 'package:flutter/services.dart';

class FlutterPointClick {
  static const MethodChannel _channel = MethodChannel('flutter_point_click');

  FlutterPointClick._();

  //TODO UserInfo에 대한 처리 필요
  static Future<void> showOfferwall({
    required String title,
    required String pickerUid,
    required int buttonTemplateIdx,

  }) async {
    if (Platform.isAndroid) {
      await _channel.invokeMethod('showOfferwall', {
        'title': title,
        'pickerUid': pickerUid,
        'buttonTemplateIdx': buttonTemplateIdx,
      });
    }
    throw Exception('is not supported on ${Platform.operatingSystem}');
  }
}

/**
  * 또는 버튼 색상을 바꾸고 싶은 경우. 단, 버튼의 색상은 아래의 5가지중 선택하실 수 있습니다.
  * BUTTON_TEMPLATE.OFFERWALL_BTN_PRIMARY (기본값)
  * BUTTON_TEMPLATE.OFFERWALL_BTN_GRREN
  * BUTTON_TEMPLATE.OFFERWALL_BTN_PURPLE
  * BUTTON_TEMPLATE.OFFERWALL_BTN_RED
  * BUTTON_TEMPLATE.OFFERWALL_BTN_YELLOW
*/
class ButtonTemplate {
  static const int OFFERWALL_BTN_PRIMARY = 5;
  static const int OFFERWALL_BTN_GRREN = 6;
  static const int OFFERWALL_BTN_PURPLE = 7;
  static const int OFFERWALL_BTN_RED = 8;
  static const int OFFERWALL_BTN_YELLOW = 9;
}