package com.example.project.service;

import com.example.project.model.service.BookingExcursionServiceModel;
import com.example.project.model.service.BookingHotelServiceModel;

public interface BookingHotelService {
    void createBooking(BookingHotelServiceModel bookingHotelServiceModel);
    boolean checkIfBookingIsOlderThan1Year(Long id);
    void deleteBookingsIfOlderThan1Year();
}
