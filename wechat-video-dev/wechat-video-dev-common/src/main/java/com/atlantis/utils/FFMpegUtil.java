package com.atlantis.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Atlantis
 * @Date: 2019/12/8
 * @Description: FFMpeg音频处理工具
 * @version: 1.0
 */
public class FFMpegUtil {
    private String ffmpegExe;

    public FFMpegUtil(String ffmpegExe) {
        super();
        this.ffmpegExe = ffmpegExe;
    }

    /**
     * @Description: 视频与背景音乐合并
     * 使用CMD命令：$ ffmpeg -i input.mp4 -i bgm.mp3 -t 7 -y output.mp4
     */
    public void mergeVideoBgm(String bgmInputPath, String videoInputPath, double seconds, String videoOutputPath) throws Exception {
        List<String> command = new ArrayList<>();
        command.add(ffmpegExe);
        command.add("-i");
        command.add(bgmInputPath);
        command.add("-i");
        command.add(videoInputPath);
        command.add("-t");
        command.add(String.valueOf(seconds));
        command.add("-y");
        command.add(videoOutputPath);

        startProcess(command);
    }

    /**
     * @Description: 截取视频的第1秒为封面
     * 使用CMD命令：$ ffmpeg -ss 00:00:01 -y -i input.mp4 -vframes 1 output.jpg
     */
    public void getVideoImage(String videoInputPath, String coverOutputPath) throws Exception {
        List<String> command = new ArrayList<>();
        command.add(ffmpegExe);
        // 指定截取第1秒
        command.add("-ss");
        command.add("00:00:01");
        command.add("-y");
        command.add("-i");
        command.add(videoInputPath);
        command.add("-vframes");
        command.add("1");
        command.add(coverOutputPath);
        startProcess(command);
    }

    /**
     * @Description: 执行cmd命令，最后关闭流
     */
    public void startProcess(List<String> command) throws Exception {
        // cmd命令类
        ProcessBuilder builder = new ProcessBuilder(command);
        // 执行cmd命令
        Process process = builder.start();

        /* 解决可能出现的线程冲突 */
        // 获取流
        InputStream errorStream = process.getErrorStream();
        InputStreamReader isr = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(isr);

        String line = "";
        while ((line = br.readLine()) != null) {
        }
        // 关闭资源
        if (br != null) {
            br.close();
        }
        if (isr != null) {
            isr.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }

    public static void main(String[] args) {
        /*MergeVideoBgm ffmpeg = new MergeVideoBgm("C:\\ffmpeg\\bin\\x86\\ffmpeg.exe");
        String videoInputPath = "C:\\abc.mp4";
        String bgmInputPath = "C:\\123.mp3";
        String videoOutputPath = "C:\\new.mp4";
        try {
            ffmpeg.merge(bgmInputPath, videoInputPath, 42, videoOutputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
