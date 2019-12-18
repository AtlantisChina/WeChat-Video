package com.atlantis.enums;

/**
 * @Auther: Atlantis
 * @Date: 2019/12/8
 * @Description: 视频状态枚举
 * @version: 1.0
 */
public enum VideoStatusEnum {
    SUCCESS(1),  // 发布成功
    FORBID(2);   // 禁止播放,管理员操作

    public final int value;

    VideoStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
