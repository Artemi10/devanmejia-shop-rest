package devanmejia.services.storage;


import devanmejia.transfer.image.DownloadedImage;
import devanmejia.transfer.image.ImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class ImageStorageImpl implements ImageStorage {
    @Autowired
    @Qualifier("apiRestTemplate")
    private RestTemplate restTemplate;
    @Value("${file.storage.api.url}")
    private String storageAPI;

    @Override
    public String downloadProductImage(String productName) {
        try{
            ResponseEntity<String> responseEntity = restTemplate
                    .getForEntity(storageAPI + "/file/" + productName, String.class);
            System.out.println(responseEntity.getStatusCodeValue());
            if (responseEntity.getStatusCodeValue() == 200){
                return responseEntity.getBody();
            }
            throw new IllegalArgumentException("Can not load download for " + productName);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Exc "+ e.getMessage());
            throw new IllegalArgumentException("Can not load download for " + productName);
        }
    }

    @Override
    public DownloadedImage[] downloadAllProductImage() {
        try{
            ResponseEntity<DownloadedImage[]> responseEntity = restTemplate
                    .getForEntity(storageAPI + "/files", DownloadedImage[].class);
            if (responseEntity.getStatusCodeValue() == 200){
                return responseEntity.getBody();
            }
            throw new IllegalArgumentException("Can not download images.");
        }
        catch (Exception e){
            throw new IllegalArgumentException("Can not download images.");
        }
    }

    @Override
    public void uploadProductImage(String productName, byte[] content) {
        ImageDTO imageDTO = ImageDTO.builder()
                .name(productName)
                .content(content).build();
        try{
            restTemplate.postForEntity(storageAPI + "/file", imageDTO, Object.class);
        }
        catch (Exception e){
            throw new IllegalArgumentException(String.format("Can not upload image for %s.", productName));
        }
    }

    @Override
    public void deleteProductImage(String productName) {
        try{
            HttpEntity<Object> httpEntity = new HttpEntity<>(new HttpHeaders());
            restTemplate.exchange(storageAPI + "/file/" + productName, HttpMethod.DELETE, httpEntity, Object.class);
        }
        catch (Exception e){
            throw new IllegalArgumentException("Can not delete image for " + productName + " " + e.getMessage());
        }
    }
}
