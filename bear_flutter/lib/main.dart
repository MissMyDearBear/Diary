import 'package:flutter/material.dart';
import 'dart:ui';
import 'package:bear_flutter/ui/WolfPage.dart';

void main() => runApp(_widgetForRoute(window.defaultRouteName));

Widget _widgetForRoute(String route){
  switch(route){
    case 'route1':
      return WolfPage();
    default:
      return Center(
        child: Text("haha"),
      );
  }
}
