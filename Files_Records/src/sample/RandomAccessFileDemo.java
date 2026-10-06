package sample;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileDemo {
    public static void main(String[] args) {
        String file = "ledger.dat";

        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            // Write structured fixed-width records
            raf.writeUTF("TXN_1001");
            raf.writeDouble(450.50);
            
            long secondRecordPointer = raf.getFilePointer(); // Save offset
            raf.writeUTF("TXN_1002");
            raf.writeDouble(1200.75);

            // Seek directly to record 2 without reading record 1
            raf.seek(secondRecordPointer);
            System.out.println("Jumped directly to offset " + secondRecordPointer);
            System.out.println("Txn ID  : " + raf.readUTF());
            System.out.println("Amount  : Rs." + raf.readDouble());

            // Rewind to beginning
            raf.seek(0);
            System.out.println("Rewound to 0 -> Txn ID: " + raf.readUTF());
        } catch (IOException e) {
            System.err.println("I/O failure: " + e.getMessage());
        }
    }
}