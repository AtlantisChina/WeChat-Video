//app.js 全局配置
App({
  // 服务器URL
  serverUrl: "http://127.0.0.1:8080",

  // 用户信息
  userInfo: null,

  // 设置用户信息本地缓存
  setGlobalUserInfo: function(user) {
    wx.setStorageSync("userInfo", user);
  },

  // 从本地缓存中获取用户信息
  getGlobalUserInfo: function() {
    return wx.getStorageSync("userInfo");
  },

  // 举报信息分类
  reportReasonArray: [
    "色情低俗",
    "政治敏感",
    "涉嫌诈骗",
    "辱骂谩骂",
    "广告垃圾",
    "诱导分享",
    "引人不适",
    "过于暴力",
    "违法违纪",
    "其它原因"
  ]
})