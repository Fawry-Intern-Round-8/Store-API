package org.fawry.storeapi.services.client;


import org.fawry.storeapi.dtos.product.ProductDTO;

import java.util.List;

public interface ProductClientService {
    boolean checkProductExists(Long productId);
    List<ProductDTO> getAllProductsWithQuantity();
}
