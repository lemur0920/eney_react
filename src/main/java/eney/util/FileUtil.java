package eney.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class FileUtil {

    public File convert(MultipartFile file){
        File convFile = new File(file.getOriginalFilename());
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            return convFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public File multipartFile(MultipartFile multipartFile){
        File convFIle = new File(multipartFile.getOriginalFilename());
        try {
            multipartFile.transferTo(convFIle);
            return convFIle;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
