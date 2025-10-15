package com.shyam_chindham.zomato_clone.services;

import org.locationtech.jts.geom.Point;

public interface DistanceService {
    double calculateDistance(Point src, Point dest);
}
