package com.shyam_chindham.zomato_clone.config;

import com.shyam_chindham.zomato_clone.dtos.PointDto;
import com.shyam_chindham.zomato_clone.utils.GeometryUtil;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.typeMap(PointDto.class, Point.class).setConverter(mappingContext -> {
            PointDto pointDto = mappingContext.getSource();
            return GeometryUtil.createPoint(pointDto);
        });

        mapper.typeMap(Point.class, PointDto.class).setConverter(mappingContext -> {
            Point point = mappingContext.getSource();
            Double[] coordinates = {
                    point.getX(),point.getY()
            };
            return new PointDto(coordinates);
        });
        return mapper;
    }
}
