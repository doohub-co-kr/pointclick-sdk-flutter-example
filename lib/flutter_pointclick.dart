import 'dart:io';
import 'package:flutter/services.dart';

class FlutterPointClick {
  static const MethodChannel _channel = MethodChannel('flutter_point_click');

  FlutterPointClick._();

  //TODO UserInfo에 대한 처리 필요
  static Future<void> showOfferwall({
    required String title,
    required String pickerUid,
  }) async {
    if (Platform.isAndroid) {
      await _channel.invokeMethod('showOfferwall', {
        'title': title,
        'pickerUid': pickerUid,
      });
    }
    throw Exception('is not supported on ${Platform.operatingSystem}');
  }
}
