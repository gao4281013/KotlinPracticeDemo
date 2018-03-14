package com.example.administrator.news_kotlin.base

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.classic.common.MultipleStatusView
import com.example.administrator.news_kotlin.MyApp

abstract class BaseActivity : AppCompatActivity() {

  protected var mLayoutStatusView : MultipleStatusView? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layoutId())
    initData()
    initView()
    start()
    initListener()
  }

  /**
   * 初始化多状态view　的监听事件
   * */
  private fun initListener(){
    mLayoutStatusView?.setOnClickListener(onRetryClickListener)

  }

  open val onRetryClickListener:View.OnClickListener = View.OnClickListener {
    start()
  }

  /**
   * 初始化控件
   * */
  abstract fun initView()

  /**
   * 初始化数据
   * */
  abstract fun initData()
  /**
   * 开始请求
   * */
  abstract fun  start()

  /**
   * 加载布局
   * */
  abstract fun layoutId(): Int


  /**
   * 打开软键盘
   * */
  fun openKeyBoard(mEditText: EditText,mContext:Context){
    val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(mEditText,InputMethodManager.RESULT_SHOWN)
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.SHOW_FORCED)
  }


  /**
   * 打开软键盘
   * */
  fun hideKeyBoard(mEditText: EditText,mContext:Context){
    val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromInputMethod(mEditText.windowToken,0)
  }

  override fun onDestroy() {
    super.onDestroy()
    MyApp?.getRefWatcher(this)?.watch(this)
  }
}
