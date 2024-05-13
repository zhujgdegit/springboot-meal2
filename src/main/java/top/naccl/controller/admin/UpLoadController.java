package top.naccl.controller.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author huangkun
 * @description:
 * @date 2024/3/21 09:34
 */
@RestController
@RequestMapping("/upload")
public class UpLoadController {

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // get file name
            String fileName = file.getOriginalFilename();
            // Specify save path
            String filePath = uploadPath + "/" + fileName;
            // Save the file locally
            file.transferTo(new File(filePath));
            return "Upload successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Fail to upload";
        }
    }
}
