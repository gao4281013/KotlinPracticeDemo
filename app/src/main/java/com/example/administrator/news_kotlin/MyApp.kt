package com.example.administrator.news_kotlin

import android.app.Activity
import android.app.Application
import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.support.compat.BuildConfig
import android.util.Log
import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import org.litepal.LitePal
import org.litepal.LitePalApplication
import kotlin.properties.Delegates

/**
 * Created by Administrator on 2018/1/12.
 */
class MyApp:LitePalApplication() {

  private var refWather:RefWatcher? = null


  companion object {
    var instance:MyApp by Delegates.notNull()

    var TAG = "MyApp"

    fun getRefWatcher(context: Context):RefWatcher?{
      val myApp = context.applicationContext as MyApp
      return myApp.refWather
    }
  }

  override fun onCreate() {
    super.onCreate()
    instance = this
    refWather = setUpLeakCanary()

    initConfig()
    registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
  }

  private fun initConfig() {

    val formatStrategy = PrettyFormatStrategy.newBuilder()
        .showThreadInfo(true)
        .methodCount(2)
        .methodOffset(7)
        .tag("gavin_gao")
        .build()
    Logger.addLogAdapter(object:AndroidLogAdapter(formatStrategy){
      override fun isLoggable(priority: Int, tag: String?): Boolean {
        return BuildConfig.DEBUG
      }
    })


    //初始化数据库
    LitePal.initialize(this)
    //初始化侧滑返回组件
    BGASwipeBackManager.getInstance().init(this)
  }

  fun setUpLeakCanary():RefWatcher{
    return if (LeakCanary.isInAnalyzerProcess(this)){
      RefWatcher.DISABLED
    }else LeakCanary.install(this)
  }


  private val mActivityLifecycleCallbacks = object:Application.ActivityLifecycleCallbacks{
    override fun onActivityPaused(p0: Activity?) {
    }

    override fun onActivityResumed(p0: Activity?) {
    }

    override fun onActivityStarted(p0: Activity?) {
      Log.d(TAG, "onCreated: " + p0?.componentName?.className)
    }

    override fun onActivityDestroyed(p0: Activity?) {
      Log.d(TAG, "onCreated: " + p0?.componentName?.className)
    }

    override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
    }

    override fun onActivityStopped(p0: Activity?) {
    }

    override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
      Log.d(TAG, "onCreated: " + p0?.componentName?.className)
    }
  }
}