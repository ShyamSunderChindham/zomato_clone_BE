package com.shyam_chindham.zomato_clone.repositories;

import com.shyam_chindham.zomato_clone.entities.ConfirmedDelivery;
import com.shyam_chindham.zomato_clone.entities.DeliveryPartner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmedDeliveryRepository extends JpaRepository<ConfirmedDelivery,Long> {
    Page<ConfirmedDelivery> findByDeliveryPartner(DeliveryPartner deliveryPartner, PageRequest pageRequest);
}
