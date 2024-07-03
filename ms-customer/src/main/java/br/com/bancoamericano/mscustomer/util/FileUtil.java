package br.com.bancoamericano.mscustomer.util;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@UtilityClass
public class FileUtil {

    public static File convertToFile(String base64, String email) {
        String fileName = "profilepicture" + email+".jpg";
        byte[] imageBytes = Base64.getDecoder().decode(base64);

        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)){
            fos.write(imageBytes);
            return file;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
