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
import org.springframework.util.StreamUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class ProductImageService {
    @Value(value="classpath:static/product-images/")
    private Resource resource;

    public void loadImageInDB(byte[] imageBytes, String productURL) throws IOException {
        Path path = null;
        try {
            path = Paths.get(this.getClass().getResource(".").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.err.println(path.getParent());
        System.err.println(path.getParent().getParent());
        File newFile = new File("target/devamejiaSpringBootProject-1.0-SNAPSHOT.jar/BOOT-INF/classes/static/product-images/" + productURL);
        System.out.println(newFile.getAbsolutePath());
        if (newFile.createNewFile()) {
            try (BufferedOutputStream fileWriter = new BufferedOutputStream(new FileOutputStream(newFile));
                 BufferedInputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(Base64Decoder.decode(imageBytes, 0, imageBytes.length)))) {
                IOUtils.copy(inputStream, fileWriter);
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(e.getMessage());
            }
        } else {
            throw new IOException("File already exists");
        }

    }

    public byte[] getImageFromDB(String productURL) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("static/product-images/" + productURL);
        return StreamUtils.copyToByteArray(is);
    }

    public void removeImageFromFileSystem(String productURL) throws IOException {
        Files.delete(Paths.get(resource.getFile() + productURL));
    }

}
