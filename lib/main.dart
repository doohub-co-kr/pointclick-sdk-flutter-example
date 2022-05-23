import 'package:flutter/material.dart';

import 'flutter_pointclick.dart';

void main() {
  runApp(MaterialApp(
    title: 'PointClick Flutter Demo',
    home: Scaffold(
        appBar: AppBar(
          title: Text('PointClick Flutter Demo'),
        ),
        body: Center(
          child: OutlinedButton(
            child: Text('Open Offerwall'),
            onPressed: () async {
              print("Open Offerwall");
              await FlutterPointClick.showOfferwall(title: "Test", pickerUid: "test");
            },
          ),
        )
    )
  ));
}
