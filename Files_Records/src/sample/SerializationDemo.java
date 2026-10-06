package sample;

import java.io.*;

class DriverProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final String driverId;
    private final String name;
    private transient String sessionSecurityToken; // 'transient' skips serialization

    public DriverProfile(String driverId, String name, String token) {
        this.driverId = driverId;
        this.name = name;
        this.sessionSecurityToken = token;
    }

    @Override
    public String toString() {
        return "DriverProfile [ID=" + driverId + ", Name=" + name + ", Token=" + sessionSecurityToken + "]";
    }
}

public class SerializationDemo {
    public static void main(String[] args) {
        String serFile = "driver_state.ser";
        DriverProfile driver = new DriverProfile("DRV-901", "Kishore Kumar", "SECRET_TOKEN_XYZ");

        // 1. Serialize
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serFile))) {
            oos.writeObject(driver);
            System.out.println("Serialized object: " + driver);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 2. Deserialize
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serFile))) {
            DriverProfile restored = (DriverProfile) ois.readObject();
            System.out.println("Restored object  : " + restored);
            // Note: sessionSecurityToken will restore as null because it was marked transient
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}