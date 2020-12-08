package cn.congee.api.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author: yang
 * @Date: 2020-12-08 10:04
 */
@Slf4j
@Component
public class FileUtils {

    /**
     * MultipartFile->File
     *
     * @param file
     * @return
     */
    public static File multipartFileToFile(MultipartFile file){
        File toFile = null;
        try {
            if(file.equals("") || file.getSize() <= 0){
                file = null;
            }else {
                InputStream ins = null;
                ins = file.getInputStream();
                toFile = new File(file.getOriginalFilename());
                inputStreamToFile(ins, toFile);
                ins.close();
            }
        }catch (Exception e){
            log.error("MultipartFile->File报错: " + e.getMessage());
            e.printStackTrace();
        }finally {
            return toFile;
        }
    }

    /**
     * 获取流文件
     *
     * @param ins
     * @param file
     */
    public static void inputStreamToFile(InputStream ins, File file){
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1){
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        }catch (Exception e){
            log.error("获取流文件报错: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 删除本地临时文件
     *
     * @param file
     */
    public static void deleteTempFile(File file){
        if(file != null){
            File del = new File(file.toURI());
            del.delete();
        }
    }

}
