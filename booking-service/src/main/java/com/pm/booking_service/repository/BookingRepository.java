package com.pm.booking_service.repository;

import com.pm.booking_service.model.Booking;
import com.pm.booking_service.repository.projection.BookedSlotsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    @Query(
            value = "SELECT S.SLOT_ID FROM BOOKING B INNER JOIN SLOTS S ON B.SLOT_ID=S.SLOT_ID WHERE B.booking_date=cast(:date as DATE) AND B.STATUS='BOOKED'",
            nativeQuery = true)
    BookedSlotsProjection bookedSlots(LocalDate date);
}
