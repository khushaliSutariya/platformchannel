import 'package:flutter/services.dart';

Stream<String> streamTimeFromNative() {
  const timeChannel =
  EventChannel('com.example.two_eventchannels/events1');
  return timeChannel.receiveBroadcastStream().map((event) => event.toString());
}

