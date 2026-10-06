package com.hotel.shmrs.filehandling;

import com.hotel.shmrs.config.HotelConfiguration;
import com.hotel.shmrs.model.entity.Booking;

import java.io.*;

public class HotelFileManager {

    // 1. Character Streams: Plain-text Receipt/Folio Writer
    public static void generateFolioReceipt(String filePath, Booking booking) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("====================================================="); writer.newLine();
            writer.write("           OFFICIAL HOTEL INVOICE & FOLIO            "); writer.newLine();
            writer.write("          " + HotelConfiguration.getInstance().getPropertyDetails()); writer.newLine();
            writer.write("====================================================="); writer.newLine();
            writer.write("Booking ID   : " + booking.getBookingId()); writer.newLine();
            writer.write("Guest Name   : " + booking.getCustomer().getName() + " (" + booking.getCustomer().getPassport() + ")"); writer.newLine();
            writer.write("Room Assigned: #" + booking.getRoom().getRoomNumber() + " [" + booking.getRoom().getRoomType().getDisplayName() + "]"); writer.newLine();
            writer.write("Stay Length  : " + booking.getNights() + " Nights"); writer.newLine();
            writer.write("Payment Mode : " + booking.getPaymentMode()); writer.newLine();
            writer.write("Add-on Extras: " + booking.getCustomer().getOptedServices()); writer.newLine();
            writer.write("Grand Total  : ₹" + booking.getTotalCost()); writer.newLine();
            writer.write("=====================================================");
        }
    }

    // 2. Byte Streams & Object Serialization
    public static void exportBookingSnapshot(String filePath, Booking booking) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(booking);
        }
    }

    public static Booking importBookingSnapshot(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Booking) ois.readObject();
        }
    }

    // 3. RandomAccessFile: Direct Pointer Jump Access
    public static void initializeLedgerRecord(String filePath, int roomNumber, boolean isOccupied, double recordedBalance) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "rw")) {
            // Structure: Room# (int, 4B) + Occupied (boolean, 1B) + Balance (double, 8B) = 13B per record
            raf.writeInt(roomNumber);
            raf.writeBoolean(isOccupied);
            raf.writeDouble(recordedBalance);
        }
    }

    public static void updateLedgerOccupancyDirect(String filePath, int slotIndex, boolean occupied) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "rw")) {
            long slotSize = 13;
            long targetOffset = (slotIndex * slotSize) + 4; // Skip 4-byte int
            raf.seek(targetOffset);
            raf.writeBoolean(occupied);
        }
    }

    public static boolean readLedgerOccupancyDirect(String filePath, int slotIndex) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
            long slotSize = 13;
            long targetOffset = (slotIndex * slotSize) + 4;
            raf.seek(targetOffset);
            return raf.readBoolean();
        }
    }
}