package edu.online.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Mp4VideoUtil extends VideoUtil {

    String ffmpeg_path;//ffmpeg的安装位置
    String video_path;
    String mp4_name;
    String mp4folder_path;

    public Mp4VideoUtil(String ffmpeg_path, String video_path, String mp4_name, String mp4folder_path) {
        super(ffmpeg_path);
        this.ffmpeg_path = ffmpeg_path;
        this.video_path = video_path;
        this.mp4_name = mp4_name;
        this.mp4folder_path = mp4folder_path;
    }

    //清除已生成的mp4
    private void clear_mp4(String mp4_path) {
        File mp4File = new File(mp4_path);
        if (mp4File.exists() && mp4File.isFile()) {
            mp4File.delete();
        }
    }

    /**
     * 视频编码，生成mp4文件
     *
     * @return 成功返回success，失败返回控制台日志
     */
    public String generateMp4() {
        //清除已生成的mp4
        clear_mp4(mp4folder_path + mp4_name);
        /*
        ffmpeg.exe -i  lucene.avi -c:v libx264 -s 1280x720 -pix_fmt yuv420p -b:a 63k -b:v 753k -r 18 .\lucene.mp4
         */
        List<String> list = new ArrayList<String>();
        list.add(ffmpeg_path);
        list.add("-i");
        list.add(video_path);
        list.add("-c:v");
        list.add("libx264");
        list.add("-y");//覆盖输出文件
        list.add("-s");
        list.add("1280x720");
        list.add("-pix_fmt");
        list.add("yuv420p");
        list.add("-b:a");
        list.add("63k");
        list.add("-b:v");
        list.add("753k");
        list.add("-r");
        list.add("18");
        list.add(mp4folder_path + mp4_name);
        String outstring = null;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(list);
            //将标准输入流和错误输入流合并，通过标准输入流程读取信息
            builder.redirectErrorStream(true);
            Process p = builder.start();
            outstring = waitFor(p);

        } catch (Exception ex) {

            ex.printStackTrace();

        }
        Boolean check_video_time = this.check_video_time(video_path, mp4folder_path + mp4_name);
        if (!check_video_time) {
            return outstring;
        } else {
            return "success";
        }

    }
}
