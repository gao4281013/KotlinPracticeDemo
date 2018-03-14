package com.example.administrator.news_kotlin.base

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.classic.common.MultipleStatusView
import com.example.administrator.news_kotlin.MyApp

/**
 *@author gavin_gao
 * fragment基类
 */
abstract class BaseFragment : Fragment() {

  /**
   * 视图是否加载完毕
   * */
  private var isViewPrepare = false

  /**
   * 数据是否已经加载
   * */
  private var hasLoadData = false

  /**
   * 多种状态的View 切换
   * */
  protected var mLayoutStatusView:MultipleStatusView?=null


  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater!!.inflate(getLayoutId(), container, false)
  }

  abstract fun getLayoutId(): Int


  override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    super.setUserVisibleHint(isVisibleToUser)
    if (isVisibleToUser){
      lazyLoadDataIfPrepared()
    }
  }

  private fun lazyLoadDataIfPrepared() {
    if (userVisibleHint && isViewPrepare && !hasLoadData){
      lazyLoad()
      hasLoadData = true
    }
  }

  /**
   * 懒加载
   * */
  abstract fun lazyLoad()


  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    isViewPrepare = true
    initView()
    lazyLoadDataIfPrepared()
    //多状态view的点击事件
    mLayoutStatusView?.setOnClickListener(onRetryClickListener)
  }


  open val onRetryClickListener:View.OnClickListener = View.OnClickListener {
    lazyLoad()
  }

  /**
   * 初始化控件
   * */
  abstract fun initView()

  override fun onDestroy() {
    super.onDestroy()
    MyApp.getRefWatcher(activity)?.watch(this)
  }
}
