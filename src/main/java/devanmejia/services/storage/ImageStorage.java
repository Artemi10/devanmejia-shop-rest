package devanmejia.services.storage;

import devanmejia.transfer.image.DownloadedImage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public interface ImageStorage {
    String downloadProductImage(String productName);
    DownloadedImage[] downloadAllProductImage();
    void uploadProductImage(String productName, byte[] content);
    void deleteProductImage(String productName);
}
