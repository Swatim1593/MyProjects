package com.dispatch.event;

import com.dispatch.enums.PaymentMode;
import com.dispatch.enums.RideType;
import com.dispatch.model.Location;
import java.time.LocalDateTime;

public class RideRequestEvent {
    private final String rideId;
    private final String riderId;
    private final Location pickup;
    private final Location dropoff;
    private final RideType rideType;
    private final PaymentMode paymentMode;
    private final boolean needsChildSeat;
    private final boolean needsExtraLuggage;
    private final LocalDateTime timestamp;

    public RideRequestEvent(String rideId, String riderId, Location pickup, Location dropoff, 
                            RideType rideType, PaymentMode paymentMode, 
                            boolean needsChildSeat, boolean needsExtraLuggage) {
        this.rideId = rideId;
        this.riderId = riderId;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.rideType = rideType;
        this.paymentMode = paymentMode;
        this.needsChildSeat = needsChildSeat;
        this.needsExtraLuggage = needsExtraLuggage;
        this.timestamp = LocalDateTime.now();
    }

    public String getRideId() { return rideId; }
    public String getRiderId() { return riderId; }
    public Location getPickup() { return pickup; }
    public Location getDropoff() { return dropoff; }
    public RideType getRideType() { return rideType; }
    public PaymentMode getPaymentMode() { return paymentMode; }
    public boolean isNeedsChildSeat() { return needsChildSeat; }
    public boolean isNeedsExtraLuggage() { return needsExtraLuggage; }
    public LocalDateTime getTimestamp() { return timestamp; }
}