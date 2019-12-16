const app = getApp()

Page({
  /* 页面数据绑定 */
  data: {
    faceUrl: "/resource/images/noneface.png",
    isMe: true,
    isFollow: false,

    videoSelClass: "video-info",
    isSelectedWork: "video-info-selected",
    isSelectedLike: "",
    isSelectedFollow: "",

    myVideoList: [],
    myVideoPage: 1,
    myVideoTotal: 1,

    likeVideoList: [],
    likeVideoPage: 1,
    likeVideoTotal: 1,

    followVideoList: [],
    followVideoPage: 1,
    followVideoTotal: 1,

    myWorkFalg: false,
    myLikesFalg: true,
    myFollowFalg: true
  },

  /* 页面加载 */
  onLoad: function(params) {
    var me = this;

    var user = app.getGlobalUserInfo(); // fixme: 从本地缓存中获取全局对象
    // var user = app.userInfo;
    var userId = user.id;
    var serverUrl = app.serverUrl;
    var isMe = false;

    var publisherId = params.publisherId;
    if (publisherId != null && publisherId != '' && publisherId != undefined) {
      if (publisherId === userId) {
        isMe = true;
      }
      me.setData({
        isMe: isMe,
        publisherId: publisherId,
        serverUrl: serverUrl
      });
      userId = publisherId;
    }

    me.setData({
      userId: userId
    })

    wx.showLoading({
      title: '获取用户信息...',
    });

    wx.request({
      url: serverUrl + '/user/queryUserInfo?userId=' + userId + "&fanId=" + user.id,
      method: "POST",
      header: {
        'content-type': 'application/json', //官方默认格式
        'headerUserId': user.id,
        'headerUserToken': user.userToken
      },
      success: function(res) {
        // console.log(result.data);
        wx.hideLoading();
        if (res.data.status == 200) {
          var userInfo = res.data.data;
          var imageFaceUrl = "/resource/images/noneface.png"; // 设置默认头像
          if (userInfo.faceImage != null && userInfo.faceImage != '' && userInfo.faceImage != undefined) {
            // 设置用户头像
            imageFaceUrl = serverUrl + userInfo.faceImage;
          }
          me.setData({
            faceUrl: imageFaceUrl,
            fansCounts: userInfo.fansCounts,
            followCounts: userInfo.followCounts,
            receiveLikeCounts: userInfo.receiveLikeCounts,
            nickname: userInfo.nickname,
            isFollow: userInfo.follow
          });
        } else if (res.data.status == 502) {
          wx.showToast({
            title: res.data.msg,
            duration: 3000,
            icon: "none",
            success: function() {
              wx.redirectTo({
                url: '../login/login',
              })
            }
          })
        } else {
          wx.showToast({
            title: res.data.msg,
            icon: 'none',
            duration: 1500
          });
        }
      }
    })
    me.getMyVideoList(1);
  },

  /* 用户注销 */
  logout: function() {
    var user = app.getGlobalUserInfo(); // fixme: 从本地缓存中获取全局对象
    // var user = app.userInfo;
    var serverUrl = app.serverUrl;

    wx.showLoading({
      title: '正在连接...',
    });

    wx.request({
      url: serverUrl + '/logout?userId=' + user.id,
      method: "POST",
      header: {
        'content-type': 'application/json' //官方默认格式
      },
      success: function(result) {
        // console.log(result.data);
        wx.hideLoading();
        // 获取状态码
        var status = result.data.status;
        if (status == 200) {
          wx.showToast({
            title: "注销成功",
            icon: 'success',
            duration: 1500
          });
          // fixme：清空用户信息的本地缓存
          wx.removeStorageSync("userInfo");
          // app.setGlobalUserInfo(null);
          // app.userInfo = null;
          // 跳转到登录页面
          wx.redirectTo({
            url: '../login/login',
          });
        }
      }
    })
  },

  /* 用户上传头像 */
  changeFace: function() {
    var that = this;
    var user = app.getGlobalUserInfo(); // fixme: 从本地缓存中获取全局对象
    // var user = app.user;
    var serverUrl = app.serverUrl;

    wx.chooseImage({
      count: 1, // 选择图片的数量，默认为9
      sizeType: ['compressed'], // 指定为压缩图
      sourceType: ['album'], // 指定图片来源为相册
      success: function(res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths;
        // console.log(tempFilePaths);
        wx.showLoading({
          title: '正在连接...',
        });

        wx.uploadFile({
          url: serverUrl + '/user/uploadFace?userId=' + user.id,
          filePath: tempFilePaths[0],
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
            var status = data.status; // 获取状态码
            if (status == 200) {
              wx.showToast({
                title: "头像上传成功",
                icon: 'success',
                duration: 1500
              });

              // 获取用户头像
              var imageFaceUrl = data.data;
              that.setData({
                faceUrl: serverUrl + imageFaceUrl
              });
            } else if (res.data.status == 502) {
              wx.showToast({
                title: res.data.msg,
                duration: 2000,
                icon: "none",
                success: function() {
                  wx.redirectTo({
                    url: '../login/login',
                  })
                }
              });
            } else {
              wx.showToast({
                title: data.msg,
                icon: 'none',
                duration: 1500
              });
            }
          }
        })
      }
    })
  },

  /* 关注我 */
  followMe: function(e) {
    var me = this;
    var user = app.getGlobalUserInfo();
    var userId = user.id;
    var publisherId = me.data.publisherId;
    var followType = e.currentTarget.dataset.followtype;

    // 1：关注 0：取消关注
    var url = '';
    if (followType == '1') {
      url = '/user/beYourFans?userId=' + publisherId + '&fanId=' + userId;
    } else {
      url = '/user/notYourFans?userId=' + publisherId + '&fanId=' + userId;
    }

    wx.showLoading();
    wx.request({
      url: app.serverUrl + url,
      method: 'POST',
      header: {
        'content-type': 'application/json', // 默认值
        'headerUserId': user.id,
        'headerUserToken': user.userToken
      },
      success: function() {
        wx.hideLoading();
        if (followType == '1') {
          wx.showToast({
            title: "关注成功",
            icon: 'success',
            duration: 1500
          });
          me.setData({
            isFollow: true,
            fansCounts: ++me.data.fansCounts
          })
        } else {
          wx.showToast({
            title: "取消关注",
            icon: 'success',
            duration: 1500
          });
          me.setData({
            isFollow: false,
            fansCounts: --me.data.fansCounts
          })
        }
      }
    })
  },


  /* 用户选择上传的短视频信息 */
  uploadVideo: function() {
    var that = this;
    wx.chooseVideo({
      sourceType: ['album'], //指定视频来源为相册
      success: function(res) {
        // console.log(res);
        var tmpDuration = res.duration;
        var tmpHeight = res.height;
        var tmpWidth = res.width;
        var tmpVideoUrl = res.tempFilePath;
        var tmpCoverUrl = res.thumbTempFilePath;

        if (res.size > 10485760) {
          wx.showToast({
            title: "上传视频大小不能超过10M",
            icon: 'none',
            duration: 2000
          });
        } else if (tmpDuration < 5) {
          wx.showToast({
            title: "请上传大于5秒的视频",
            icon: 'none',
            duration: 2000
          });
        } else {
          // 跳转到选择背景音乐界面
          wx.navigateTo({
            url: '../chooseBgm/chooseBgm?tmpDuration=' + tmpDuration +
              "&tmpHeight=" + tmpHeight +
              "&tmpWidth=" + tmpWidth +
              "&tmpVideoUrl=" + tmpVideoUrl +
              "&tmpCoverUrl=" + tmpCoverUrl
          });
        }
      }
    })
  },

  /* 我的作品 */
  doSelectWork: function() {
    this.setData({
      isSelectedWork: "video-info-selected",
      isSelectedLike: "",
      isSelectedFollow: "",

      myWorkFalg: false,
      myLikesFalg: true,
      myFollowFalg: true,

      myVideoList: [],
      myVideoPage: 1,
      myVideoTotal: 1,

      likeVideoList: [],
      likeVideoPage: 1,
      likeVideoTotal: 1,

      followVideoList: [],
      followVideoPage: 1,
      followVideoTotal: 1
    });

    this.getMyVideoList(1);
  },

  /* 我的喜欢 */
  doSelectLike: function() {
    this.setData({
      isSelectedWork: "",
      isSelectedLike: "video-info-selected",
      isSelectedFollow: "",

      myWorkFalg: true,
      myLikesFalg: false,
      myFollowFalg: true,

      myVideoList: [],
      myVideoPage: 1,
      myVideoTotal: 1,

      likeVideoList: [],
      likeVideoPage: 1,
      likeVideoTotal: 1,

      followVideoList: [],
      followVideoPage: 1,
      followVideoTotal: 1
    });

    this.getMyLikesList(1);
  },

  /* 我关注的作者发布的视频 */
  doSelectFollow: function() {
    this.setData({
      isSelectedWork: "",
      isSelectedLike: "",
      isSelectedFollow: "video-info-selected",

      myWorkFalg: true,
      myLikesFalg: true,
      myFollowFalg: false,

      myVideoList: [],
      myVideoPage: 1,
      myVideoTotal: 1,

      likeVideoList: [],
      likeVideoPage: 1,
      likeVideoTotal: 1,

      followVideoList: [],
      followVideoPage: 1,
      followVideoTotal: 1
    });

    this.getMyFollowList(1)
  },

  getMyVideoList: function(page) {
    var me = this;
    wx.showLoading();
    var serverUrl = app.serverUrl;
    wx.request({
      url: serverUrl + '/video/showAllVideos?page=' + page + '&searchText=' + me.data.userId,
      method: "POST",
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function(res) {
        //console.log(res.data);
        wx.hideLoading();
        var myVideoList = res.data.data.rows;
        var newVideoList = me.data.myVideoList;
        me.setData({
          myVideoPage: page,
          myVideoList: newVideoList.concat(myVideoList),
          myVideoTotal: res.data.data.total,
          serverUrl: app.serverUrl
        });
      }
    })
  },

  getMyLikesList: function(page) {
    var me = this;
    var userId = me.data.userId;
    var serverUrl = app.serverUrl;
    wx.showLoading();
    wx.request({
      url: serverUrl + '/video/showMyLike?userId=' + userId + '&page=' + page + '&pageSize=6',
      method: "POST",
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function(res) {
        //console.log(res.data);
        var likeVideoList = res.data.data.rows;
        wx.hideLoading();

        var newVideoList = me.data.likeVideoList;
        me.setData({
          likeVideoPage: page,
          likeVideoList: newVideoList.concat(likeVideoList),
          likeVideoTotal: res.data.data.total,
          serverUrl: app.serverUrl
        });
      }
    })
  },

  getMyFollowList: function(page) {
    var me = this;
    var userId = me.data.userId;
    var serverUrl = app.serverUrl;
    wx.showLoading();
    wx.request({
      url: serverUrl + '/video/showMyFollow?userId=' + userId + '&page=' + page + '&pageSize=6',
      method: "POST",
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function(res) {
        //console.log(res.data);
        wx.hideLoading();
        var followVideoList = res.data.data.rows;
        var newVideoList = me.data.followVideoList;
        me.setData({
          followVideoPage: page,
          followVideoList: newVideoList.concat(followVideoList),
          followVideoTotal: res.data.data.total,
          serverUrl: app.serverUrl
        });
      }
    })
  },

  // 点击跳转到视频详情页面
  showVideo: function(e) {
    //console.log(e);
    var myWorkFalg = this.data.myWorkFalg;
    var myLikesFalg = this.data.myLikesFalg;
    var myFollowFalg = this.data.myFollowFalg;

    if (!myWorkFalg) {
      var videoList = this.data.myVideoList;
    } else if (!myLikesFalg) {
      var videoList = this.data.likeVideoList;
    } else if (!myFollowFalg) {
      var videoList = this.data.followVideoList;
    }

    var arrindex = e.target.dataset.arrindex;
    var videoInfo = JSON.stringify(videoList[arrindex]);

    wx.navigateTo({
      url: '../videoInfo/videoInfo?videoInfo=' + videoInfo
    });
  },

  /* 上拉刷新 */
  lower(e) {
    var myWorkFalg = this.data.myWorkFalg;
    var myLikesFalg = this.data.myLikesFalg;
    var myFollowFalg = this.data.myFollowFalg;

    if (!myWorkFalg) {
      var currentPage = this.data.myVideoPage;
      var totalPage = this.data.myVideoTotal;
      // 获取总页数进行判断，如果当前页数和总页数相等，则不分页
      if (currentPage === totalPage) {
        wx.showToast({
          title: '已经没有视频啦...',
          icon: "none"
        });
        return;
      }
      var page = currentPage + 1;
      this.getMyVideoList(page);
    } else if (!myLikesFalg) {
      var currentPage = this.data.likeVideoPage;
      var totalPage = this.data.likeVideoTotal;
      // 获取总页数进行判断，如果当前页数和总页数相等，则不分页
      if (currentPage === totalPage) {
        wx.showToast({
          title: '已经没有视频啦...',
          icon: "none"
        });
        return;
      }
      var page = currentPage + 1;
      this.getMyLikesList(page);
    } else if (!myFollowFalg) {
      var currentPage = this.data.followVideoPage;
      var totalPage = this.data.followVideoTotal;
      // 获取总页数进行判断，如果当前页数和总页数相等，则不分页
      if (currentPage === totalPage) {
        wx.showToast({
          title: '已经没有视频啦...',
          icon: "none"
        });
        return;
      }
      var page = currentPage + 1;
      this.getMyFollowList(page);
    }
  }
})