// DeliveryRepository.java
package org.example.repository;

import org.example.Delivery; // Import your Delivery entity
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

}