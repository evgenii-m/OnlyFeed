/**
 * 
 */
package org.push.onlyfeed.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author push
 *
 */
@Component
public class FileUploader {
    private static Logger logger = LogManager.getLogger(FileUploader.class);
    @Value("${storage.uploads.baseDirPath}")
    private String uploadsBaseDirPath;
    @Value("${storage.uploads.baseUrl}")
    private String uploadsBaseUrl;
   

    public boolean validateImage(MultipartFile image) {
        return (image.getContentType().equals("image/jpeg"));
    }
    
    public String upload(String filename, MultipartFile file) {
        File uploadedFile = new File(uploadsBaseDirPath + filename);
        try {
            FileUtils.writeByteArrayToFile(uploadedFile, file.getBytes());
            return uploadsBaseUrl + filename;
        } catch (IOException e) {
            logger.error("Error while uploadImage (filename=" + filename 
                    + ", exception: " + e + ")");
            e.printStackTrace();
            return null;
        }
    }
    
    public String uploadJpeg(MultipartFile file) {
        UUID id = UUID.randomUUID();
        String randomFilename = id.toString().replaceAll("-","") + ".jpg";
        return upload(randomFilename, file);
    }
    
}
