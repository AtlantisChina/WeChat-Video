const app = getApp()

Page({
  /* 页面数据绑定 */
  data: {
    bgmList: [], // 背景音乐
    serverUrl: app.serverUrl,
    videoParams: {} //用户上传视频的信息
  },

  /* 页面加载 */
  onLoad: function(params) {
    var me = this; // 作用域的对象
    var serverUrl = app.serverUrl;
    var user = app.getGlobalUserInfo();
    // console.log(params)
    me.setData({
      videoParams: params
    });

    wx.showLoading({
      title: '正在获取信息...',
    });

    wx.request({
      url: serverUrl + '/bgm/queryBgmList',
      method: "POST",
      header: {
        'content-type': 'application/json', //官方默认格式
        'headerUserId': user.id,
        'headerUserToken': user.userToken
      },
      success: function(res) {
        wx.hideLoading();
        //console.log(res);

        if (res.data.status == 200) {
          var bgmData = res.data.data;
          //console.log(bgmData);
          me.setData({
            bgmList: bgmData,
            serverUrl: serverUrl
          });
        } else if (res.data.status == 502) {
          wx.redirectTo({
            url: '../login/login',
          });
          wx.showToast({
            title: res.data.msg,
            duration: 2000,
            icon: "none"
          });
        }
      }
    })
  },

  /* 上传视频 */
  uploadVideo: function(e) {
    var me = this;
    var serverUrl = app.serverUrl;
    var user = app.getGlobalUserInfo(); // fixme: 从本地缓存中获取全局对象
    var bgmId = e.detail.value.bgmId;
    var desc = e.detail.value.desc.trim();

    var tmpDuration = me.data.videoParams.tmpDuration;
    var tmpHeight = me.data.videoParams.tmpHeight;
    var tmpWidth = me.data.videoParams.tmpWidth;
    var tmpVideoUrl = me.data.videoParams.tmpVideoUrl;
    var tmpCoverUrl = me.data.videoParams.tmpCoverUrl;

    wx.showLoading({
      title: '正在上传中...',
    });

    wx.uploadFile({
      url: serverUrl + '/video/uploadVideo',
      formData: {
        userId: user.id, // fixme：原先不使用本地缓存 app.user.id
        bgmId: bgmId,
        videoDesc: desc,
        videoSeconds: tmpDuration,
        videoHeight: tmpHeight,
        videoWidth: tmpWidth,
      },
      filePath: tmpVideoUrl,
      name: 'file',
      header: {
        'content-type': 'application/json', //官方默认格式
        'headerUserId': user.id,
        'headerUserToken': user.userToken
      },
      success: function(res) {
        wx.hideLoading();
        var data = JSON.parse(res.data); // 将返回的数据转换成JSON格式
        // console.log(data);
        if (data.status == 200) {
          // 上传成功后跳回之前的页面
          wx.navigateBack({
            delta: 1,
          })
          wx.showToast({
            title: "视频上传成功",
            icon: 'success',
            duration: 2000
          });
        } else if (res.data.status == 502) {
          wx.showToast({
            title: res.data.msg,
            duration: 2000,
            icon: "none"
          });
          wx.redirectTo({
            url: '../login/login',
          })
        } else {
          wx.showToast({
            title: data.msg,
            icon: 'none',
            duration: 2000
          });
        }
      }
    })
  }
})