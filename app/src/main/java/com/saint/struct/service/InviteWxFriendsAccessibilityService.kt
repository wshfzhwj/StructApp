//package com.saint.struct.service
//
//import android.accessibilityservice.AccessibilityService
//import android.content.IntentFilter
//import android.view.accessibility.AccessibilityEvent
//import com.sd.wxenterprise.common.Global
//
///**
// * 使用AccessibilityService模拟点击方式操作企业微信搜索并添加外部联系人
// */
//class InviteWxFriendsAccessibilityService : AccessibilityService() {
//    protected override fun onServiceConnected() {
//        super.onServiceConnected()
//        SDLog.debug(TAG, "[onServiceConnected]")
//        HandleAccessibilityEventService.mService = this
//        AccessibilityHelper.mService = this
//        if (HandleAccessibilityEventService.receiver != null) {
//            try {
//                unregisterReceiver(HandleAccessibilityEventService.receiver)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//        HandleAccessibilityEventService.receiver = HandleAccessibilityEventService.AccessibilityReceiver()
//        registerReceiver(
//            HandleAccessibilityEventService.receiver,
//            IntentFilter(HandleAccessibilityEventService.AccessibilityReceiver.ACTION_POP_RESULT)
//        )
//        registerReceiver(
//            HandleAccessibilityEventService.receiver,
//            IntentFilter(HandleAccessibilityEventService.AccessibilityReceiver.ACTION_COMMAND_RESULT)
//        )
//        registerReceiver(
//            HandleAccessibilityEventService.receiver,
//            IntentFilter(HandleAccessibilityEventService.AccessibilityReceiver.ACTION_SET_STATE)
//        )
//        HandleAccessibilityEventService.setState(HandleAccessibilityEventService.STATE_ONLINE)
//        Global.postRunnableDelay({
//            WeworkReceiver.broadCastSaveHealth(this, HandleAccessibilityEventService.STATE_ONLINE, "")
//            SDLog.debug(TAG, "[onServiceConnected]broadCastSaveHealth")
//        }, 15 * 1000)
//        HandleAccessibilityEventService.command
//    }
//
//    override fun onAccessibilityEvent(event: AccessibilityEvent) {
//        try {
//            HandleAccessibilityEventService.startWithEvent(getApplicationContext(), event)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    override fun onInterrupt() {
//        SDLog.debug(TAG, "onInterrupt")
//        try {
//            unregisterReceiver(HandleAccessibilityEventService.receiver)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    override fun onDestroy() {
//        SDLog.debug(TAG, "onDestroy")
//        super.onDestroy()
//        HandleAccessibilityEventService.setState(HandleAccessibilityEventService.STATE_OFFLINE)
//        try {
//            unregisterReceiver(HandleAccessibilityEventService.receiver)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    companion object {
//        private val TAG = InviteWxFriendsAccessibilityService::class.java.getSimpleName()
//    }
//}
