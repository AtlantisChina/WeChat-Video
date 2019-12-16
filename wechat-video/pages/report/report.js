const app = getApp()

Page({
  /* 页面数据绑定 */
  data: {
    reasonType: "请选择原因",
    reportReasonArray: app.reportReasonArray,
    publishUserId: "",
    videoId: ""
  },

  /* 页面加载 */
  onLoad: function(params) {
    var me = this;
    var videoId = params.videoId;
    var publishUserId = params.publishUserId;

    me.setData({
      publishUserId: publishUserId,
      videoId: videoId
    });
  },

  changeMe: function(e) {
    var me = this;
    var index = e.detail.value;
    var reasonType = app.reportReasonArray[index];

    me.setData({
      reasonType: reasonType
    });
  },

  submitReport: function(e) {
    var me = this;
    var reasonIndex = e.detail.value.reasonIndex;
    var reasonContent = e.detail.value.reasonContent.trim();
    var user = app.getGlobalUserInfo();

    if (reasonIndex == null || reasonIndex == '' || reasonIndex == undefined) {
      wx.showToast({
        title: '选择举报理由',
        icon: "none"
      })
      return;
    }

    var serverUrl = app.serverUrl;
    wx.request({
      url: serverUrl + '/user/reportUser',
      method: 'POST',
      data: {
        dealUserId: me.data.publishUserId,
        dealVideoId: me.data.videoId,
        title: app.reportReasonArray[reasonIndex],
        content: reasonContent,
        userid: user.id
      },
      header: {
        'content-type': 'application/json', // 默认值
        'headerUserId': user.id,
        'headerUserToken': user.userToken
      },
      success: function(res) {
        wx.navigateBack();
        wx.showToast({
          title: res.data.msg,
          duration: 2000,
          icon: 'none'
        })
      }
    })
  }
})