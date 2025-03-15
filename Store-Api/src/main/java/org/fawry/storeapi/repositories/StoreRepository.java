package org.fawry.storeapi.repositories;

import org.fawry.storeapi.dtos.StoreDTO;
import org.fawry.storeapi.dtos.StoreResponseDTO;
import org.fawry.storeapi.entities.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StoreRepository extends CrudRepository<Store, Long> {

    @Query(value = """
        SELECT s FROM Store s 
        WHERE ST_Distance_Sphere(s.location, ST_MakePoint(:longitude, :latitude)) <= :radius
    """)
    Page<StoreResponseDTO> findNearestStores(double longitude, double latitude, double radius, Pageable pageable);
    Store findStoreByName(String name);
    Store findStoreById(Long id);
}
