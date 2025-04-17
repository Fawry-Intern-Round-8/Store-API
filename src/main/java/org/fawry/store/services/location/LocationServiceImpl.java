package org.fawry.store.services.location;


import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl {
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public Point createPoint(double longitude, double latitude) {
        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(4326);
        return point;
    }
}
