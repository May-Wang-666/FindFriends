package cn.edu.zju.socialnetwork.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class ImageUtil {

    /**
     * decode base64编码的图片文件并将其保存到指定目录
     *
     * @param dataURL 有base64编码的图片的url
     * @param path    图片存放路径
     */
    public static String saveBase64Image(String dataURL, String path) {

        // 生成随机文件名
        String suffix = ".png";
        String dateFolder = getYearMonth();
        String randomName = UUID.randomUUID().toString();
        String imgName = dateFolder + "/" + randomName + suffix;

        // 解码base64编码，保存本地文件
        String base64 = dataURL.substring(dataURL.indexOf(",") + 1);
        try {
            File newFile = new File(path + imgName);
            // 检查是否存在目录
            if (!newFile.getParentFile().exists()) {
                if (!newFile.getParentFile().mkdirs()) {
                    String res = "创建文件夹失败！";
                    System.out.println(res);
                    return "can't make folder!";
                }
            }
            FileOutputStream write = new FileOutputStream(newFile);
            byte[] decoderBytes = Base64.getDecoder().decode(base64);
            write.write(decoderBytes);
            write.close();
        } catch (FileNotFoundException e1) {
            System.out.println("FileNotFound: 指定路径不存在！");
            e1.printStackTrace();
        } catch (IOException e2) {
            System.out.println("IOException: 写文件出错！");
            e2.printStackTrace();
        }
        return StaticValues.visitPath + imgName;
    }


    // 获取年和月，用于分类照片
    private static String getYearMonth() {
        String ts = String.valueOf(System.currentTimeMillis());
        String formatStr = "yyyyMM";
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(new Date(Long.valueOf(ts)));
    }


}
