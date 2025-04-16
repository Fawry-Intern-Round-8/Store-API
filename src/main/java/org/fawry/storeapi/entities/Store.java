package org.fawry.storeapi.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column (name = "name", nullable = false)
    private String name;
    @Column (name = "address", nullable = false)
    private String address;

    @Column(name= "location", columnDefinition = "POINT SRID 4326", nullable = false)
    private Point location;



}
