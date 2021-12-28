package Cajero;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



public class Cajero  {
	static String nombre=null;
	   static String DNI=null;
	   static String numeroDias;
	   
	public static void eleccion() throws IOException {
	System.out.println("¿Qué desea realizar?");
	System.out.println("1.Alquiler de habitación");
	System.out.println("2.Perdida de tarjeta");
	System.out.println("3.Datos de la tarjeta");
	System.out.println("4.Simulación con errores");
	System.out.println("5.checkout");
	System.out.print("Seleccione el numero correspondiente a la acción a realizar:");
	String opcion = new BufferedReader(new InputStreamReader(System.in)).readLine();
	;
	String mensaje;
	switch(Integer.parseInt (opcion)) {
	case 1:
		//crea los datos
		System.out.print("Escriba su nombre:");
		nombre=new BufferedReader(new InputStreamReader(System.in)).readLine();
		System.out.print("Escriba su DNI:");
		DNI=new BufferedReader(new InputStreamReader(System.in)).readLine();
		System.out.print("Seleccione el numero de dias que se alojara con nosotros: ");
		numeroDias =new BufferedReader(new InputStreamReader(System.in)).readLine();
		mensaje="1-"+nombre+" "+DNI+" "+numeroDias;
		crearSocket(mensaje);
		break;
	case 2:
		 //crea los datos
		System.out.print("Escriba su nombre (como lo escribio en el registro):");
		nombre=new BufferedReader(new InputStreamReader(System.in)).readLine();
		System.out.print("Escriba su DNI(como lo escribio en el registro):");
		DNI=new BufferedReader(new InputStreamReader(System.in)).readLine();
		mensaje="2-"+nombre+" "+DNI;	
		crearSocket(mensaje);
		break;
	case 3:
		//crea los datos
		
		System.out.print("Escriba su DNI(como lo escribio en el registro):");
		DNI=new BufferedReader(new InputStreamReader(System.in)).readLine();
		mensaje="2-"+DNI;	
		crearSocket(mensaje);
		break;
	case 4://leerlo tranquilamente
		break;
	case 5://liberar ip 
		break;
	default:
		
	}
	
}
	 
public static void crearSocket(String mensaje) throws IOException{
	InetAddress serverAddress = InetAddress.getByName("localhost");
	int serverPort = 3000;
	byte[] bytesToSend = mensaje.getBytes();
	DatagramSocket socket = new DatagramSocket();
	DatagramPacket sendPacket = new DatagramPacket(bytesToSend,bytesToSend.length,serverAddress, serverPort);
	socket.send(sendPacket);
	//DatagramPacket receivePacket=new DatagramPacket(new byte[bytesToSend.length], bytesToSend.length);
	//socket.receive(receivePacket); // Podria no llegar nunca el datagrama de ECO
	//System.out.println("ECO:"+ new String(receivePacket.getData()));
	socket.close();
}
public static void main(String[] args) throws IOException {
	while(true){
		eleccion();
	}
	
}	
}
