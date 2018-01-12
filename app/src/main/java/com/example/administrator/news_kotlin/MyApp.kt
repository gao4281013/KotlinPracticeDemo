package com.example.administrator.news_kotlin

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager
import org.litepal.LitePal
import org.litepal.LitePalApplication
import kotlin.properties.Delegates

/**
 * Created by Administrator on 2018/1/12.
 */
class MyApp:LitePalApplication() {

  companion object {
    var instance:MyApp by Delegates.notNull()
  }

  override fun onCreate() {
    super.onCreate()
    instance = this

    //初始化数据库
    LitePal.initialize(this)
    //初始化侧滑返回组件
    BGASwipeBackManager.getInstance().init(this)
  }
}