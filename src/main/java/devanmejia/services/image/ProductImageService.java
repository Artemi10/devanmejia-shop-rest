package devanmejia.services.image;

import com.mysql.cj.util.Base64Decoder;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SocketUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class ProductImageService {
    @Value(value="classpath:static")
    Resource resource;

    public void loadImageInDB(byte[] imageBytes, String productURL) {
        File file = new File(getStoragePath() + productURL);
        try (BufferedOutputStream fileWriter = new BufferedOutputStream(new FileOutputStream(file));
             BufferedInputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(Base64Decoder.decode(imageBytes, 0, imageBytes.length)))) {
            IOUtils.copy(inputStream, fileWriter);
        } catch (IOException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }
    public byte[] getImageFromDB(String productURL) throws IOException {
        File file = ResourceUtils.getFile(getStoragePath() + productURL);
        return  Files.readAllBytes(file.toPath());
    }
    public void removeImageFromFileSystem(String productURL) throws IOException {
        Files.delete(Paths.get(getStoragePath() + productURL));
    }

    public String getStoragePath(){
        try {
            ClassPathResource classPathResource = new ClassPathResource("static/product-images/.");
            return classPathResource.getFile().getAbsolutePath() + "/";
        } catch (IOException e) {
           throw new IllegalArgumentException(e);
        }
    }
}
