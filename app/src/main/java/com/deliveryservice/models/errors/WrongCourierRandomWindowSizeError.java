package com.deliveryservice.models.errors;

public class WrongCourierRandomWindowSizeError extends RuntimeException {
    public WrongCourierRandomWindowSizeError(
        int courierRandomWindowStartInSeconds,
        int courierRandomWindowEndInSeconds
    ) {
        super(
            String.format(
                "Window start time %d cannot be bigger then end time %d",
                courierRandomWindowStartInSeconds,
                courierRandomWindowEndInSeconds
            )
        );
    }
}
