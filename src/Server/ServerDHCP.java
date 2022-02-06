package Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServerDHCP {
    static FileWriter fichero;
    static String ruta = "DHCP.txt";

    public  static void main(String[] aStrings) throws Exception {
        int puerto = 3050;
        DatagramSocket socket = new DatagramSocket(puerto);
        byte[] buffer = new byte[2048];
        DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);
        byte[] mandamensaje = new byte[2048];
        while (true) {
            socket.receive(datagram);
            System.out.println("Nueva peticion de servicio");
            //Inicio de una hebra para la peticion actual
            (new ServerUDPImpl(datagram)).start();
            String linea = new String(datagram.getData(), 0, datagram.getLength());
            String[] parts = linea.split("-");
            String part1 = parts[0]; // 123
            String part2 = parts[1]; // 654321
            switch(Integer.parseInt (part1)){
                case 1:
                    crearArchivo(part2);
                break;
                case 5:
                    eliminarFilas(part2);
                break;
            }
            int puertoCliente = datagram.getPort();
            InetAddress direccion = datagram.getAddress();
            //creo el datagrama
            DatagramPacket respuesta = new DatagramPacket(mandamensaje, mandamensaje.length, direccion, puertoCliente);
            //Envio la información
            System.out.println("Envio la informacion del cliente");
            socket.send(respuesta);
            //socket.close();      
        }
    }
    static public void crearArchivo(String linea) throws IOException {
        try {
            File archivo;
            BufferedWriter bw;
            PrintWriter pw;
            System.out.println(linea);
            archivo = new File (ruta);
            
            try {
                if(archivo.exists()){
                    System.out.println("El archivo existe");
                    bw = new BufferedWriter(new FileWriter(archivo, true));//el true es para que no pise el archivo
                    pw= new PrintWriter(bw);
                    System.out.println("creado bufered");
                    int numero = (int) Math.floor(Math.random()*254+1); 
                    pw.append(linea+"/150.255.255."+numero+"\n");
                    System.out.println("se ha escrito bien");
                    
                }else{
                    System.out.println("El archivo no existe");
                    bw = new BufferedWriter(new FileWriter(archivo));
                    System.out.println("El archivo ha sido creado");
                    pw= new PrintWriter(bw);
                    System.out.println("creado bufered");
                    int numero = (int) Math.floor(Math.random()*254+1); 
                    pw.append(linea+"/150.255.255."+numero+"\n");
                    System.out.println("se ha escrito bien");
                }
                bw.close();
                pw.close();
                System.out.println("Salgo del bucle");
            } catch (Exception e) {
                System.out.println("Ha sucedido un error al escribir los datos"+e);
            }
        } catch (Exception e) {
            System.out.println("Ha sucedido un error al crear el archivo"+e);
        }
    }
    static public void eliminarFilas(String cadena) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        List<String> lineas = new ArrayList<String>();
        String linea;
        while ((linea = br.readLine()) != null) {
            lineas.add(linea);
        }
        lineas = lineas.stream().filter(x -> !x.contains(cadena)).collect(Collectors.toList());
        FileWriter fw = new FileWriter(ruta);
        BufferedWriter bw = new BufferedWriter(fw);
        for (String linea2 : lineas) {
            bw.write(linea2);
            bw.newLine();
        }
        bw.close();
        System.out.println("Se ha eliminado la linea");
    }
}
