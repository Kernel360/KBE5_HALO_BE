package com.kernel.reservation.repository;


import com.kernel.reservation.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerReservationRepository extends JpaRepository<Reservation, Long>, CustomManagerReservationRepository {
}
