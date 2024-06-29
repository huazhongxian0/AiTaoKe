import App from './App'
// #ifndef VUE3
import Vue from 'vue'
import './uni.promisify.adaptor'
import { $http } from '@escook/request-miniprogram'
import store from './store/store.js'
//网络配置部分
  //设置请求根目录
uni.$http = $http
$http.baseUrl = 'https://api-hmugo-web.itheima.net'
  //请求拦截器
$http.beforeRequest = function (options) {
	 uni.showLoading({
	    title: '数据加载中...',
	  })
// 判断请求的是否为有权限的 API 接口
  if (options.url.indexOf('/my/') !== -1) {
    // 为请求头添加身份认证字段
    options.header = {
      // 字段的值可以直接从 vuex 中进行获取
      Authorization: store.state.m_user.token,
    }
  }
}
  //响应拦截器
$http.afterRequest = function () {
  wx.hideLoading()
}
//封装showModal
uni.$showMsg = function (title = '数据加载失败！', duration = 1500) {
  uni.showToast({
    title,
    duration,
    icon: 'none',
  })
}
//继续，，，
Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
  ...App,
   store,
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif