package org.fawry.storeapi.repositories;

import org.fawry.storeapi.dtos.StoreDTO;
import org.fawry.storeapi.dtos.StoreResponseDTO;
import org.fawry.storeapi.entities.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends CrudRepository<Store, Long> {

    @Query(value = """
    SELECT s.id, s.name, s.address,
           ST_Distance_Sphere(s.location, ST_GeomFromText(CONCAT('POINT(', :longitude, ' ', :latitude, ')'), 4326)) AS distance_meters
    FROM store s
    WHERE ST_Distance_Sphere(s.location, ST_GeomFromText(CONCAT('POINT(', :longitude, ' ', :latitude, ')'), 4326)) <= :radius
    ORDER BY distance_meters ASC
    """, nativeQuery = true)
    Page<Object[]> findNearestStores(@Param("longitude") double longitude,
                                     @Param("latitude") double latitude,
                                     @Param("radius") double radius,
                                     Pageable pageable);

    @Query(value = """
    SELECT s.id, s.name, s.address,
           ST_Distance_Sphere(s.location, ST_GeomFromText(CONCAT('POINT(', :longitude, ' ', :latitude, ')'), 4326)) AS distance_meters
    FROM store s
    JOIN stock st ON s.id = st.store_id
    WHERE st.product_id = :productId
    AND ST_Distance_Sphere(s.location, ST_GeomFromText(CONCAT('POINT(', :longitude, ' ', :latitude, ')'), 4326)) <= :radius
    ORDER BY distance_meters ASC
    """, nativeQuery = true)
    Page<Object[]> findNearestStoresWithProduct(@Param("productId") Long productId,
                                                @Param("longitude") double longitude,
                                                @Param("latitude") double latitude,
                                                @Param("radius") double radius,
                                                Pageable pageable);




    Store findStoreByName(String name);
    Store findStoreById(Long id);
}
