package goingmerry.cent.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
public class FileUploadUtil {


    public static void saveNewImage(String uploadDir, String fileName,
                                    MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {// 디렉토리 생성
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {

            Path filePath = uploadPath.resolve(fileName);
//            log.info(filePath.toString());
            System.out.println(filePath);

            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            // Files.copy(source, destination, option) : source 에 해당하는 파일 destination 으로 복사

        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
}
