//package com.saint.struct.service
//
//import android.accessibilityservice.AccessibilityService
//import android.app.IntentService
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import android.os.Message
//import android.text.TextUtils
//import android.util.Log
//import android.view.accessibility.AccessibilityEvent
//import android.view.accessibility.AccessibilityNodeInfo
//import java.util.Stack
//
//class HandleAccessibilityEventService : IntentService("HandleAccessibilityEventService") {
//    class AccessibilityReceiver : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            val action = intent.action
//            if (ACTION_POP_RESULT == action) {
//                userId = intent.getStringExtra("userId")
//                mobile = intent.getStringExtra("mobile")
//                inviteSettingId = intent.getStringExtra("inviteSettingId")
//                inviteMessage = intent.getStringExtra("inviteMessage")
//                isRealTime = intent.getBooleanExtra("isRealTime", false)
//                SDLog.debug(
//                    TAG, "userId:" + userId + " mobile:" + mobile + " inviteSettingId:"
//                            + inviteSettingId + " inviteMessage:" + inviteMessage
//                )
//                currentInviteErrorText = null
//                if (!TextUtils.isEmpty(mobile)) {
//                    setState(STATE_RUNNING)
//                    try {
//                        onGlobalSearch(mobile)
//                    } catch (e: NullPointerException) {
//                        setState(STATE_STARTING)
//                    }
//                }
//            } else if (ACTION_COMMAND_RESULT == action) {
//                val command = intent.getStringExtra("command")
//                SDLog.debug(TAG, "command:$command")
//                when (command) {
//                    COMMAND_START_UP -> if (state != STATE_RUNNING && state != STATE_STARTING && state != STATE_IDLE) {
//                        setState(STATE_STARTING)
//                        if (!AccessibilityHelper.isServiceRunning(mService)) {
//                            SDLog.debug(TAG, "isServiceRunning is false")
//                            AccessibilityHelper.openAccessibilityServiceSettings(Global.getContext())
//                        } else {
//                            SDLog.debug(TAG, "start Wework")
//                            //                                Global.postRunnable2UI(() -> {
////                                    WeworkHelper.wakeUpAndUnlock();
////                                    WeworkHelper.openWework(Global.getContext());
////                                });
//                        }
//                    }
//
//                    COMMAND_SHUTDOWN -> setState(STATE_ONLINE)
//                    COMMAND_FLUSH_HEALTH -> Global.postRunnable {
//                        WeworkReceiver.broadCastSaveHealth(
//                            Global.getContext(),
//                            state,
//                            currentInviteErrorText
//                        )
//                    }
//                }
//                Global.postRunnableDelay({ WeworkReceiver.broadCastGetCommand(Global.getContext(), 1) }, 1000)
//            } else if (ACTION_SET_STATE == action) {
//                setState(intent.getStringExtra("state"))
//            }
//        }
//
//        companion object {
//            const val ACTION_POP_RESULT = "com.tencent.wework.intent.action.popresult"
//            const val ACTION_COMMAND_RESULT = "com.tencent.wework.intent.action.commandresult"
//            const val ACTION_SET_STATE = "com.tencent.wework.intent.action.setstate"
//        }
//    }
//
//    override fun onHandleIntent(intent: Intent?) {
//        if (intent == null) return
//        val event = intent.getParcelableExtra<AccessibilityEvent>(EXTRA_EVENT) ?: return
//        val eventType = event.eventType
//        val className = event.className.toString()
//        if (className.startsWith("com.tencent.wework")) {
//            currentActivity = className
//        }
//        if (event.text.contains(WEWORK_ERROR_TEXT_2) || event.text.contains(WEWORK_ERROR_TEXT_3)
//            || event.text.contains(WEWORK_ERROR_TEXT_4) || event.text.contains(WEWORK_ERROR_TEXT_5)
//            || event.text.contains(WEWORK_ERROR_TEXT_6) || event.text.contains(WEWORK_ERROR_TEXT_7)
//        ) {
//            currentInviteErrorText = event.text.toString()
//        }
//        val nodeRoot = mService!!.rootInActiveWindow ?: return
//        if (STATE_LOGOUT != state) {
//            // 未登录状态，显示登录页面logo
//            Global.postRunnableDelay({
//                val list1 = nodeRoot.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/gkq")
//                if (list1 != null && list1.size > 0) {
//                    setState(STATE_LOGOUT)
//                }
//            }, 500L)
//        }
//        if (STATE_STARTING == state) {
//            stack.clear()
//            val list = nodeRoot.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/gud")
//            if (list != null && list.size > 0) {
//                SDLog.debug(TAG, "in WwMainActivity $className")
//                setState(STATE_RUNNING)
//                onWwMain()
//            } else {
//                // 点击"跳过"
//                Global.postRunnableDelay({
//                    val list1 = nodeRoot.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/aca")
//                    if (list1 != null && list1.size > 0) {
//                        list1[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                    }
//                }, 2000L)
//                // 点击左上角返回
//                Global.postRunnableDelay({
//                    val list1 = nodeRoot.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/h9e")
//                    if (list1 != null && list1.size > 0) {
//                        list1[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                    }
//                }, 4000L)
//                // 点击左上角向下箭头
//                Global.postRunnableDelay({
//                    val list1 = nodeRoot.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/cqm")
//                    if (list1 != null && list1.size > 0) {
//                        list1[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                    }
//                }, 6000L)
//            }
//        } else if (STATE_RUNNING == state || STATE_IDLE == state) {
//            if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
//                when (className) {
//                    WEWORK_ACTIVITY_MAIN -> onWwMain()
//                    WEWORK_ACTIVITY_GLOBAL_SEARCH -> {}
//                    WEWORK_ACTIVITY_WECHAT_CONTACT_INFO -> {}
//                    WEWORK_ACTIVITY_FRIEND_ADD_MULTIIDENTITY -> {}
//                    WEWORK_ACTIVITY_WECHAT_CONTACT_SEND_VERIFY -> {}
//                    WEWORK_DIALOG -> currentActivity = WEWORK_DIALOG
//                }
//            }
//        }
//    }
//
//    @Synchronized
//    private fun onWwMain() {
//        // 点击"消息"
//        Global.postRunnableDelay({
//            val nodeInfo = mService!!.rootInActiveWindow
//            if (nodeInfo != null && stack.empty()) {
//                val list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/gud")
//                if (list != null && list.size > 0) {
//                    list[0].getChild(0).performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                }
//            }
//        }, 3000L)
//        // 点击右上角放大镜
//        Global.postRunnableDelay({
//            val nodeInfo = mService!!.rootInActiveWindow
//            if (nodeInfo != null && stack.empty()) {
//                val list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/h9z")
//                if (list != null && list.size > 0) {
//                    list[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                    WeworkReceiver.broadCastStartSearch(this)
//                }
//            }
//        }, 6000L)
//    }
//
//    companion object {
//        private val TAG = HandleAccessibilityEventService::class.java.getSimpleName()
//        @JvmField
//        var mService: AccessibilityService? = null
//        private const val SERVICE_CONNECTED = "service_connected"
//        private const val EXTRA_EVENT = "extra_event"
//        const val STATE_STARTING = "启动中"
//        const val STATE_RUNNING = "加人中"
//        const val STATE_IDLE = "无线索"
//        const val STATE_ONLINE = "待加人"
//        const val STATE_OFFLINE = "已离线"
//        const val STATE_FULL = "已加满"
//        const val STATE_BAN = "被封禁"
//        const val STATE_LOGOUT = "已登出"
//        var state: String? = STATE_OFFLINE
//        const val WEWORK_ERROR_TEXT_1 = "该用户不存在"
//        const val WEWORK_ERROR_TEXT_2 = "操作异常，暂无法使用"
//        const val WEWORK_ERROR_TEXT_3 = "帐号涉嫌违规"
//        const val WEWORK_ERROR_TEXT_4 = "添加好友过于频繁"
//        const val WEWORK_ERROR_TEXT_5 = "对应的微信帐号异常，操作失败"
//        const val WEWORK_ERROR_TEXT_6 = "当前网络不可用，请检查你的网络设置"
//        const val WEWORK_ERROR_TEXT_7 = "由于违规行为"
//        const val WEWORK_ERROR_TEXT_8 = "仅搜索到企业微信帐户"
//        const val WEWORK_ERROR_TEXT_9 = "无法找到该用户"
//        const val WEWORK_ACTIVITY_MAIN = "com.tencent.wework.launch.WwMainActivity"
//        const val WEWORK_ACTIVITY_GLOBAL_SEARCH = "com.tencent.wework.contact.controller.GlobalSearchActivity"
//        const val WEWORK_ACTIVITY_WECHAT_CONTACT_INFO = "com.tencent.wework.contact.controller.WechatContactInfoActivity"
//        const val WEWORK_ACTIVITY_CONTACT_DETAIL = "com.tencent.wework.contact.controller.ContactDetailActivity"
//        const val WEWORK_ACTIVITY_FRIEND_ADD_MULTIIDENTITY =
//            "com.tencent.wework.friends.controller.FriendAddMultiIdentityActivity"
//        const val WEWORK_ACTIVITY_WECHAT_CONTACT_SEND_VERIFY =
//            "com.tencent.wework.friends.controller.WechatContactSendVerifyActivity"
//        const val WEWORK_DIALOG = "dxa"
//        var currentActivity: String? = null
//        var currentInviteErrorText: String? = null
//        var userId: String? = null
//        var mobile: String? = null
//        var inviteSettingId: String? = null
//        var inviteMessage: String? = null
//        var isRealTime = false
//        private val stack = Stack<String>()
//        const val COMMAND_START_UP = "START_UP"
//        const val COMMAND_SHUTDOWN = "SHUTDOWN"
//        const val COMMAND_FLUSH_HEALTH = "FLUSH_HEALTH"
//        @JvmField
//        var receiver: AccessibilityReceiver? = null
//        @JvmStatic
//        fun startWithEvent(context: Context, event: AccessibilityEvent?) {
//            val intent = Intent(context, HandleAccessibilityEventService::class.java)
//            intent.putExtra(EXTRA_EVENT, event)
//            context.startService(intent)
//        }
//
//        private const val FLAG_COMMAND_CIRCLE = 0x01
//        private const val FLAG_onFriendAddMultiIdentity = 0x02
//        private const val FLAG_onWechatContactInfo = 0x03
//        private const val FLAG_onSendInviteMsg = 0x04
//        private const val FLAG_onBack = 0x05
//        private const val FLAG_onFull = 0x06
//        private val handler: Handler = object : Handler(Looper.getMainLooper()) {
//            override fun handleMessage(msg: Message) {
//                super.handleMessage(msg)
//                val what = msg.what
//                when (what) {
//                    FLAG_COMMAND_CIRCLE -> command
//                    FLAG_onFriendAddMultiIdentity -> onFriendAddMultiIdentity()
//                    FLAG_onWechatContactInfo -> onWechatContactInfo()
//                    FLAG_onSendInviteMsg -> {}
//                    FLAG_onBack -> onBack()
//                    FLAG_onFull -> onFull()
//                }
//            }
//        }
//        @JvmStatic
//        val command: Unit
//            get() {
//                Global.postRunnableDelay({
//                    Log.d(TAG, "state: " + state + " alarm:" + currentInviteErrorText)
//                    WeworkReceiver.broadCastGetCommand(Global.getContext(), 1)
//                    WeworkReceiver.broadCastSaveHealth(Global.getContext(), state, currentInviteErrorText)
//                    val msg = Message.obtain()
//                    msg.what = FLAG_COMMAND_CIRCLE
//                    handler.sendMessage(msg)
//                }, 30000)
//            }
//
//        @JvmStatic
//        fun setState(s: String?) {
//            state = s
//            Global.postRunnable { WeworkReceiver.broadCastSaveHealth(Global.getContext(), state, currentInviteErrorText) }
//        }
//
//        @Throws(NullPointerException::class)
//        fun onGlobalSearch(mobile: String?) {
//            // 点击叉号
//            Global.postRunnableDelay({
//                val nodeInfo = mService!!.rootInActiveWindow
//                if (nodeInfo != null) {
//                    val list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/fxb")
//                    if (list != null && list.size > 0) {
//                        list[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                    }
//                }
//            }, 5000L)
//            // 输入手机号
//            Global.postRunnableDelay({
//                val nodeInfo = mService!!.rootInActiveWindow
//                if (nodeInfo != null) {
//                    val list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/fxc")
//                    if (list != null && list.size > 0) {
//                        val node1 = list[0]
//                        val arguments = Bundle()
//                        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, mobile)
//                        node1.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
//                    } else {
//                        setState(STATE_STARTING)
//                    }
//                } else {
//                    setState(STATE_STARTING)
//                }
//            }, 10000L)
//            // 点击搜索
//            Global.postRunnableDelay({
//                val nodeInfo = mService!!.rootInActiveWindow
//                if (nodeInfo != null) {
//                    val list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/aya")
//                    if (list != null && list.size > 0) {
//                        val node2 = list[0]
//                        node2.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                        stack.push("Search")
//                        onFriendAddMultiIdentity()
//                    } else {
//                        setState(STATE_STARTING)
//                    }
//                } else {
//                    setState(STATE_STARTING)
//                }
//            }, 20000L)
//        }
//
//        @Throws(NullPointerException::class)
//        private fun onWechatContactInfo() {
//            // 点击"添加为联系人"
//            Global.postRunnableDelay({
//                val nodeInfo = mService!!.rootInActiveWindow
//                if (nodeInfo != null) {
//                    val list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/fq")
//                    if (list != null && list.size > 0) {
//                        list[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                        onWechatContactSendVerify()
//                    } else {
//                        SDLog.debug(TAG, "event error, currentActivity: " + currentActivity)
//                        onOK()
//                    }
//                } else {
//                    setState(STATE_STARTING)
//                }
//            }, 5000L)
//        }
//
//        @Throws(NullPointerException::class)
//        private fun onFriendAddMultiIdentity() {
//            Global.postRunnableDelay({
//                val nodeInfo = mService!!.rootInActiveWindow
//                if (nodeInfo != null) {
//                    var list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/f8u") // 查找中
//                    if (list != null && list.size > 0) {
//                        mService!!.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
//                        setState(STATE_STARTING)
//                        SDLog.debug(TAG, "查找中")
//                    } else {
//                        // 搜到多个联系人信息，点击微信用户
//                        list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/bki")
//                        if (list != null && list.size > 0) {
//                            list[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                            stack.push("MultiIdentity")
//                        }
//                        onWechatContactInfo()
//                    }
//                }
//            }, 12000L)
//        }
//
//        @Throws(NullPointerException::class)
//        private fun onWechatContactSendVerify() {
//            Global.postRunnableDelay({
//                val nodeInfo = mService!!.rootInActiveWindow
//                if (nodeInfo != null) {
//                    // 获取界面上的欢迎文案
//                    var list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/acu")
//                    if (list != null && list.size > 0) {
//                        val currentInviteMessage = list[0].getText().toString()
//                        if (!TextUtils.isEmpty(inviteMessage) && inviteMessage != currentInviteMessage) {
//                            try {
//                                Thread.sleep(2000L)
//                            } catch (ignored: Exception) {
//                            }
//                            // 点击画笔
//                            AccessibilityHelper.adbClick("com.tencent.wework:id/acv")
//                            try {
//                                Thread.sleep(2000L)
//                            } catch (ignored: Exception) {
//                            }
//                            // 点击叉号
//                            AccessibilityHelper.adbClick("com.tencent.wework:id/acv")
//                            try {
//                                Thread.sleep(2000L)
//                            } catch (ignored: Exception) {
//                            }
//                            list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/act")
//                            if (list != null && list.size > 0) {
//                                val arguments = Bundle()
//                                arguments.putCharSequence(
//                                    AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
//                                    inviteMessage
//                                )
//                                list[0].performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
//                            }
//                            try {
//                                Thread.sleep(2000L)
//                            } catch (ignored: Exception) {
//                            }
//                        }
//                    }
//                    // 点击"发送添加邀请"
//                    list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/cr0")
//                    if (list != null && list.size > 0) {
//                        list[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                        try {
//                            Thread.sleep(6000L)
//                        } catch (ignored: Exception) {
//                        }
//                        if (!TextUtils.isEmpty(currentInviteErrorText) && (currentInviteErrorText!!.contains(WEWORK_ERROR_TEXT_2)
//                                    || currentInviteErrorText!!.contains(WEWORK_ERROR_TEXT_3) || currentInviteErrorText!!.contains(
//                                WEWORK_ERROR_TEXT_4
//                            )
//                                    || currentInviteErrorText!!.contains(WEWORK_ERROR_TEXT_5) || currentInviteErrorText!!.contains(
//                                WEWORK_ERROR_TEXT_7
//                            ))
//                        ) {
//                            WeworkReceiver.broadCastReportAddFriends(
//                                Global.getContext(),
//                                userId,
//                                mobile,
//                                inviteSettingId,
//                                false,
//                                currentInviteErrorText,
//                                isRealTime
//                            )
//                            SDLog.debug(
//                                TAG,
//                                "ReportAddFriend userId:" + userId + " mobile:" + mobile + " inviteSettingId:" + inviteSettingId + " isSuccess:false" + " failReason:" + currentInviteErrorText + " isReal:" + isRealTime
//                            )
//                            if (currentInviteErrorText!!.contains(WEWORK_ERROR_TEXT_5) || currentInviteErrorText!!.contains(
//                                    WEWORK_ERROR_TEXT_7
//                                )
//                            ) {
//                                setState(STATE_BAN)
//                                onFull()
//                            } else if (currentInviteErrorText!!.contains(WEWORK_ERROR_TEXT_2) || currentInviteErrorText!!.contains(
//                                    WEWORK_ERROR_TEXT_3
//                                )
//                            ) {
//                                setState(STATE_FULL)
//                                onFull()
//                            } else if (currentInviteErrorText!!.contains(WEWORK_ERROR_TEXT_4)) {
//                                try {
//                                    Thread.sleep(300000L)
//                                } catch (ignored: Exception) {
//                                }
//                                setState(STATE_ONLINE)
//                            }
//                        } else {
//                            WeworkReceiver.broadCastReportAddFriends(
//                                Global.getContext(),
//                                userId,
//                                mobile,
//                                inviteSettingId,
//                                true,
//                                null,
//                                isRealTime
//                            )
//                            SDLog.debug(
//                                TAG,
//                                "ReportAddFriend userId:" + userId + " mobile:" + mobile + " inviteSettingId:" + inviteSettingId + " isSuccess:true" + " isReal:" + isRealTime
//                            )
//                            currentInviteErrorText = null
//                            try {
//                                Thread.sleep(5000L)
//                            } catch (ignored: Exception) {
//                            }
//                            onBack()
//                        }
//                    }
//                    mobile = null
//                }
//            }, 5000L)
//        }
//
//        @Throws(NullPointerException::class)
//        private fun onOK() {
//            Global.postRunnableDelay({
//                val nodeInfo = mService!!.rootInActiveWindow
//                if (nodeInfo != null) {
//                    if (!stack.isEmpty()) stack.pop()
//                    try {
//                        Thread.sleep(2000L)
//                    } catch (ignored: Exception) {
//                    }
//                    var errorText = ""
//                    var list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/ads") // 企业微信用户信息页
//                    if (list != null && list.size > 0 || WEWORK_ACTIVITY_CONTACT_DETAIL == currentActivity) {
//                        errorText = WEWORK_ERROR_TEXT_8
//                        mService!!.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
//                    } else {
//                        list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/bca") // 对话框
//                        if (list != null && list.size > 0) {
//                            // 是对话框
//                            val errorlist1 = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/bcf")
//                            val errorlist2 = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/bck")
//                            if (errorlist1 != null && errorlist1.size > 0) {
//                                errorText = errorlist1[0].getText().toString()
//                            } else if (errorlist2 != null && errorlist2.size > 0) {
//                                errorText = errorlist2[0].getText().toString()
//                            }
//                        }
//                    }
//                    try {
//                        Thread.sleep(3000L)
//                    } catch (ignored: Exception) {
//                    }
//                    // "取消"按钮
//                    val listCancel = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/bcg")
//                    if (listCancel != null && listCancel.size > 0) {
//                        // 双选对话框，含"取消"按钮
//                        listCancel[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                    } else {
//                        // 无"取消"按钮，按单选框处理
//                        list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/bci")
//                        if (list != null && list.size > 0) {
//                            // 点击"确定"
//                            list[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                        }
//                    }
//                    currentInviteErrorText = errorText
//                    WeworkReceiver.broadCastReportAddFriends(
//                        Global.getContext(),
//                        userId,
//                        mobile,
//                        inviteSettingId,
//                        false,
//                        errorText,
//                        isRealTime
//                    )
//                    SDLog.debug(
//                        TAG,
//                        "ReportAddFriend userId:" + userId + " mobile:" + mobile + " inviteSettingId:" + inviteSettingId + " isSuccess:false" + " failReason:" + errorText + " isReal:" + isRealTime
//                    )
//                    mobile = null
//                    try {
//                        Thread.sleep(3000L)
//                    } catch (ignored: Exception) {
//                    }
//                    if (errorText.contains(WEWORK_ERROR_TEXT_5) || errorText.contains(WEWORK_ERROR_TEXT_7)) {
//                        setState(STATE_BAN)
//                        onFull()
//                    } else if (errorText.contains(WEWORK_ERROR_TEXT_2) || errorText.contains(WEWORK_ERROR_TEXT_3)) {
//                        setState(STATE_FULL)
//                        onFull()
//                    } else if (errorText.contains(WEWORK_ERROR_TEXT_4)) {
//                        try {
//                            Thread.sleep(300000L)
//                        } catch (ignored: Exception) {
//                        }
//                        setState(STATE_ONLINE)
//                    } else {
//                        onBack()
//                    }
//                } else {
//                    onOK()
//                }
//            }, 3000L)
//        }
//
//        private fun onBack() {
//            while (!stack.isEmpty()) {
//                try {
//                    Thread.sleep(3000)
//                } catch (ignored: Exception) {
//                }
//                mService!!.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
//                val popStr = stack.pop()
//                if (popStr == "Search") {
//                    stack.clear()
//                    break
//                }
//            }
//            WeworkReceiver.broadCastStartSearch(Global.getContext())
//        }
//
//        private fun onFull() {
//            while (!stack.isEmpty()) {
//                try {
//                    Thread.sleep(3000)
//                } catch (ignored: Exception) {
//                }
//                mService!!.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
//                val popStr = stack.pop()
//                if (popStr == "Search") {
//                    stack.clear()
//                    break
//                }
//            }
//            mService!!.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
//            // 点击"我"
//            Global.postRunnableDelay({
//                val nodeInfo = mService!!.rootInActiveWindow
//                if (nodeInfo != null) {
//                    val list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/gud")
//                    if (list != null && list.size > 0) {
//                        list[0].getChild(3).performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                    }
//                }
//            }, 2000L)
//            // 点击"开始使用"
//            Global.postRunnableDelay({
//                val nodeInfo = mService!!.rootInActiveWindow
//                if (nodeInfo != null) {
//                    val list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/glp")
//                    if (list != null && list.size > 0) {
//                        list[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                    }
//                }
//            }, 4000L)
//            // 点击"我对外名片"
//            Global.postRunnableDelay({
//                val nodeInfo = mService!!.rootInActiveWindow
//                if (nodeInfo != null) {
//                    val list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.wework:id/eky")
//                    if (list != null && list.size > 0) {
//                        list[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                    }
//                }
//            }, 6000L)
//        }
//    }
//}
