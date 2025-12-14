package com.pm.patient_service.service;

import com.pm.patient_service.client.DoctorServiceClient;
import com.pm.patient_service.contants.Constants;
import com.pm.patient_service.dto.BookingRequestDTO;
import com.pm.patient_service.dto.GeneralResponse;
import com.pm.patient_service.dto.NotificationDTO;
import com.pm.patient_service.dto.SlotRequestDTO;
import com.pm.patient_service.exception.GeneralException;
import com.pm.patient_service.kafka.KafkaProducer;
import com.pm.patient_service.model.Booking;
import com.pm.patient_service.model.Slot;
import com.pm.patient_service.repository.BookingRepository;
import com.pm.patient_service.repository.SlotRepository;
import com.pm.patient_service.repository.projection.BookedSlotsProjection;
import com.pm.patient_service.service.mapper.OperationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class OperationService {

    @Autowired
    private BookingRepository repository;

    @Autowired
    private OperationMapper operationMapper;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private DoctorServiceClient doctorServiceClient;

    @Autowired
    private RestTemplate restTemplate;

    public GeneralResponse addBooking(BookingRequestDTO bookingRequest) {
        GeneralResponse response=null;
        try{
            response=restTemplate.postForObject(Constants.BOOKING_SERVICE+"/booking", bookingRequest, GeneralResponse.class);
            return response;
        }
        catch(Exception e){
            log.info("[ EXCEPTION ]: {}", e.getMessage());
            throw new GeneralException("Error while booking the slot, try again after some time");
        }
    }

//    fixedRate -> fixed intervals won't wait for previous execution to finish might overlap
//    fixedDelay -> jobs will run in serial manner
//    initialDelay -> initial delay on start of the application
//    @Scheduled(cron = "*/60 * * * * *")
//    public void clearPastBookings(){
//        List<Booking> bookings=repository.findAll();
//        bookings.forEach(booking->{
//            log.info("[ BK ]: {}", booking.getSlotFrame());
//        });
//    }

//    This function should belong to doctor service
    public boolean addSlot(SlotRequestDTO slotRequest) {
        try{
            slotRepository.save(operationMapper.toSlotModel(slotRequest));
            return true;
        }
        catch(Exception e){
            log.info("[ EXCEPTION ]: {}", e.getMessage());
            return false;
        }
    }
}
