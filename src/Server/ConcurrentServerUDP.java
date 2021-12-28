package Server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ConcurrentServerUDP {
    static FileWriter fr;
    static int id=0;
    static String ruta ="E:\\ProyectoSocket\\src\\archivo.txt";
        public static void main(String[] args) throws IOException {
        DatagramSocket ds = null;
        try {
            InetAddress localAddr = InetAddress.getByName("localhost");
            int wellKnownPort = 3000; // Puerto conocido del servidor
            ds = new DatagramSocket(wellKnownPort, localAddr);
            byte[] buffer = new byte[2048];
            DatagramPacket datagram = new DatagramPacket(buffer,buffer.length);
            while (true) {
                ds.receive(datagram);
                System.out.println("Nueva peticion de servicio");
                //Inicio de una hebra para la peticion actual
                (new ServerUDPImpl(datagram)).start();   
                String linea = new String(datagram.getData(), 0, datagram.getLength());
                //System.out.println("Peticion de servicio procesada"+ linea);
                String[] parts = linea.split("-");
                String part1 = parts[0]; // 123
                String part2 = parts[1]; // 654321
                switch(Integer.parseInt (part1)){
                    case 1:
                     part2= part2+" "+id++;
                     escribirFichero(part2);
                     break;
                    case 2:
                    buscar(part2);
                }
            }
        } catch (IOException e) {
            System.err.println("Error E/S en: " + e.getMessage());}
        finally {
            if (ds != null)
                ds.close();
        }   
    }
        public static void escribirFichero(String linea) throws IOException {
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
                    pw.append(linea+"\n");
                    System.out.println("se ha escrito bien");
                    
                }else{
                    System.out.println("El archivo no existe");
                    bw = new BufferedWriter(new FileWriter(archivo));
                    System.out.println("El archivo ha sido creado");
                    pw= new PrintWriter(bw);
                    System.out.println("creado bufered");
                    pw.write(linea+"\n");
                    System.out.println("se ha escrito bien");
                }
                bw.close();

            } catch (Exception e) {
                
                System.out.println("Ha sucedido un error al escribir los datos"+e);
            }
        }     
        static public void buscar(String texto) throws FileNotFoundException{
            Scanner entrada;
            boolean contiene= false;
             //creamos un objeto File asociado al fichero seleccionado
             File f = new File(ruta);
             //creamos un Scanner para leer el fichero
             entrada = new Scanner(f);
            try {
                //mostramos el texto a buscar
                System.out.println("Texto a buscar: " + texto);
                while (entrada.hasNext()) { //mientras no se llegue al final del fichero
                    String linea = entrada.nextLine();  //se lee una línea
                    if (linea.contains(texto)) {   //si la línea contiene el texto buscado se muestra por pantalla         
                        System.out.println(linea);
                        contiene = true;
                    }
                }
                if(!contiene){ //si el archivo no contienen el texto se muestra un mensaje indicándolo
    
                    System.out.println(texto + " no se ha encontrado en el Cliente");
                }
            
            } catch (NullPointerException e) {
                System.out.println(e.toString() + "No ha seleccionado ningún archivo");
            } catch (Exception e) {
                System.out.println(e.toString());
            } finally {
                if (entrada != null) {
                    entrada.close();
                }
            }
            
        }
} 
