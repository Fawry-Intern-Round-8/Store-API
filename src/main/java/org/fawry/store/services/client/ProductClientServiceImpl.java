package org.fawry.store.services.client;

import org.fawry.store.services.stock.StockService;
import org.fawry.storeapi.dtos.product.ProductDTO;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductClientServiceImpl implements ProductClientService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String PRODUCT_API_BASE_URL = "http://localhost:8084/api/products";

    private final StockService stockService;

    public ProductClientServiceImpl(@Lazy StockService stockService) {
        this.stockService = stockService;
    }

    @Override
    public boolean checkProductExists(Long productId) {
        String url = PRODUCT_API_BASE_URL + "/" + productId;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<ProductDTO> getAllProductsWithQuantity() {
        ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(
                PRODUCT_API_BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<ProductDTO> productDTOs = response.getBody();
        if (productDTOs.isEmpty()) {
            return List.of();
        }

        return productDTOs.stream()
                .peek(dto -> dto.setQuantity(getProductQuantitySafe(dto.getId())))
                .collect(Collectors.toList());
    }

    private int getProductQuantitySafe(Long productId) {
        try {
            return stockService.getTotalProductQuantity(productId).intValue();
        } catch (Exception e) {
            return 0;
        }
    }
}