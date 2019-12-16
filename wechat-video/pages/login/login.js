const app = getApp()

Page({
  /* 页面数据绑定 */
  data: {
    imgPaths: [
      '/resource/images/dsp1.jpg',
      '/resource/images/dsp2.jpg',
      '/resource/images/dsp3.jpg',
      '/resource/images/dsp4.jpg'
    ],
    autoplay: true
  },

  /* 页面加载 */
  onLoad: function(params) {
    var me = this;
    var redirectUrl = params.redirectUrl;
    // debugger;
    if (redirectUrl != null && redirectUrl != undefined && redirectUrl != '') {
      redirectUrl = redirectUrl.replace(/#/g, "?");
      redirectUrl = redirectUrl.replace(/@/g, "=");

      me.redirectUrl = redirectUrl;
    }
  },

  /* 用户登录 */
  doLogin: function(e) {
    var me = this;
    var formObject = e.detail.value;
    var username = formObject.username.trim();
    var password = formObject.password.trim();
    var serverUrl = app.serverUrl;

    // 有效性验证
    if (username.length == 0 || password.length == 0) {
      wx.showToast({
        title: '账号或密码不能为空',
        icon: 'none',
        duration: 1500
      });
      return;
    }

    if (username.length < 3 || username.length > 12 || password.length < 3 || password.length > 12) {
      wx.showToast({
        title: '账号或密码长度为3~12',
        icon: 'none',
        duration: 1500
      });
      return;
    }

    wx.showLoading({
      title: '正在连接...',
    });

    wx.request({
      url: serverUrl + '/login',
      method: "POST",
      data: {
        username: username,
        password: password
      },
      header: {
        'content-type': 'application/json' //官方默认格式
      },
      success: function(res) {
        // console.log(res.data);
        wx.hideLoading();
        // 获取状态码
        var status = res.data.status;
        if (status == 200) {
          wx.showToast({
            title: "登录成功",
            icon: 'success',
            duration: 1500
          });
          // app.userInfo = res.data.data;
          // fixme:修改为将用户信息添加至本地缓存
          app.setGlobalUserInfo(res.data.data);
          // 页面跳转
          var redirectUrl = me.redirectUrl;
          if (redirectUrl != null && redirectUrl != undefined && redirectUrl != '') {
            console.log(redirectUrl)
            wx.redirectTo({
              url: redirectUrl,
            })
          } else {
            wx.redirectTo({
              url: '../mine/mine',
            })
          }
        } else {
          wx.showToast({
            title: res.data.msg,
            icon: 'none',
            duration: 1500
          });
        }
      }
    })

  },

  /* 跳转到注册页面 */
  goRegistPage: function() {
    wx.redirectTo({
      url: '../register/register'
    })
  }
})