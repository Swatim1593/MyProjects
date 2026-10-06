package sample;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteStreamDemo {
	public static void main(String[]args) {
		String filename= "driver_avatar.dat";
		try(FileOutputStream fos=new FileOutputStream(filename)){
			byte[]binaryData= {
					0x4A,0x41,0x56,0x41,100,120
			};
			fos.write(binaryData);
			System.out.println("Binary data written successfully");
		} catch(IOException e) {
			System.out.println("Write error: "+e.getMessage());
		}
		try(FileInputStream fis=new FileInputStream(filename)){
			int byteRead;
			System.out.println("Byte read from disk");
			while((byteRead=fis.read())!=-1) {
				System.out.println(byteRead+" ");
			}
			System.out.println();
		}catch(IOException e) {
			System.out.println("Read error :"+e.getMessage());;
		}
	}

}
