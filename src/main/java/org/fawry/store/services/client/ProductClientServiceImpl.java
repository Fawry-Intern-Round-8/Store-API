package org.fawry.store.services.client;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductClientServiceImpl implements ProductClientService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String PRODUCT_API_BASE_URL = "http://localhost:8081/api/products/";

    public boolean checkProductExists(Long productId) {
        String url = PRODUCT_API_BASE_URL + productId;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
}
