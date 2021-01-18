package devanmejia.services.image;

import com.mysql.cj.util.Base64Decoder;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ProductImageService {
    @Value("${imageStoragePath}")
    private String IMAGE_STORAGE_PATH;

    public void loadImageInDB(byte[] imageBytes, String productURL) {
        File file = new File(IMAGE_STORAGE_PATH + productURL);
        try (BufferedOutputStream fileWriter = new BufferedOutputStream(new FileOutputStream(file));
             BufferedInputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(Base64Decoder.decode(imageBytes, 0, imageBytes.length)))) {
            IOUtils.copy(inputStream, fileWriter);
        } catch (IOException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }
    public byte[] getImageFromDB(String productURL) throws IOException {
        File file = ResourceUtils.getFile(IMAGE_STORAGE_PATH + productURL);
        return  Files.readAllBytes(file.toPath());
    }
    public void removeImageFromFileSystem(String productURL) throws IOException {
        Files.delete(Paths.get(IMAGE_STORAGE_PATH+productURL));
    }
}
