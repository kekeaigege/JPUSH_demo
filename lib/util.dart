// import 'package:jpush_flutter/jpush_flutter.dart';
//
// class PushUtil {
//   final JPush jpush = JPush();
//   //单例
//   static PushUtil _instance;
//   PushUtil._();
//   static PushUtil getInstance(){
//     if(_instance == null){
//       _instance = PushaUtil._();
//     }
//     return _instance;
//   }
//    PushUtil.addEventHandler(
//   onReceiveNotification: (Map<String, dynamic> message) async {
//   print("flutter onReceiveNotification: $message");
// },
//        )
//     try {
//       pushUtil!.addEventHandler(
//           onReceiveNotification: (Map<String, dynamic> message) async {
//             debugPrint("flutter onReceiveNotification: $message");
//           },
//
//           ///点击的回调事件
//           onOpenNotification: (Map<String, dynamic> message) async {
//             debugPrint("flutter onOpenNotification: $message");
//           }, onReceiveMessage: (Map<String, dynamic> message) async {
//         debugPrint("flutter onReceiveMessage: $message");
//       }, onReceiveNotificationAuthorization:
//           (Map<String, dynamic> message) async {
//         debugPrint("flutter onReceiveNotificationAuthorization: $message");
//       });
//     } on PlatformException {
//       // platformVersion = 'Failed to get platform version.';
//     }
//
//     pushUtil!.setup(
//       appKey: Platform.isIOS ? 'iOS AppKey' : "Android AppKey", //你自己应用的 AppKey
//       channel: "theChannel",
//       production: false,
//       debug: true,
//     );
//
//     ///声音，震动等设置
//     pushUtil!.applyPushAuthority(
//         new NotificationSettingsIOS(sound: true, alert: true, badge: false));
//
//     ///给后台的registration id
//     pushUtil!.getRegistrationID().then((rid) {
//       print("flutter get registration id : $rid");
//     });
//   }
// }