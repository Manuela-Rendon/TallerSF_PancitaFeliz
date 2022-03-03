package Frontera;
import Contolador.GestorTienda;
import Entidades.Orden;
import Entidades.Producto;
import Entidades.TiendaCliente;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import javax.swing.*;

/**
 * Clase que se encarga de todos los dialogos e informacion que va a usar el usuario
 */
public class Menu {
    /**
     * Menu Principal de ejecucion del programa
     */
    GestorTienda gestorTienda;

    /**
     * Cuando se crea el objeto Menu en en main se carga la informacion de la tienda
     * @param gestorTienda
     */
    public Menu(GestorTienda gestorTienda) {
        this.gestorTienda = gestorTienda;
        gestorTienda.cargarProductos();
        gestorTienda.cargarTiendas();
    }

    public void menuInicial() {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n ¡¡BIENVENIDO A PANCITA FELIZ!! \n");
            System.out.println("1.Ver Productos");
            System.out.println("2.Ver Tiendas");
            System.out.println("Elija Una Opcion");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    mostrarProductos(gestorTienda.getProductos());
                    break;
                case 2:
                    mostrarTiendas(gestorTienda.getTiendas());
                    break;
                case 0:
                    break;
                default:
                    break;
            }
        } while (opcion != 0);
    }

    public static void mostrarProductos (ArrayList<Producto> productos) {
        for (Producto p: productos) {
            System.out.println(p.getCodigoProducto() +" - "+ p.getNombreProducto() +" - "+ p.getValorUnitario());
        }
    }

    public static void mostrarTiendas(ArrayList<TiendaCliente> tiendas){
        System.out.println("Seleccione una tienda para registrar una orden:");
        System.out.println("Lista de Tiendas vinculadas");
        int opcion;
        do {
            int valor = 1;
            for (TiendaCliente t: tiendas) {
                System.out.println(valor+")"+t.getCodigoTienda() +" - "+ t.getNombreTienda());
                valor++;
            }
            Scanner sc = new Scanner(System.in);
            opcion = sc.nextInt();
            if(opcion <= tiendas.size()){
                crearOrden(tiendas.get(opcion - 1));
                opcion = 0;
            }
        } while (opcion != 0);
    }

    public static void crearOrden (TiendaCliente tienda) {
        System.out.println("REGISTRO ORDEN PARA LA TIENDA: \n" +
                tienda.getCodigoTienda()+" "+ tienda.getNombreTienda());

        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese los valores Solicitados");

        System.out.print("Codigo de Vendedor: ");
        String codigoVendedor = sc.nextLine();

        System.out.print("Nombre Vendedor: ");
        String nombreVendedor = sc.nextLine();

        System.out.print("Fecha de la Venta: ");
        String fechaVenta = sc.next();

        Orden orden = new Orden(codigoVendedor,nombreVendedor, fechaVenta);
        String ruta = VentanaArchivo();
        System.out.println(ruta);

        System.out.println();

    }

    /**
     * Método para abrir una ventana desde los documentos para seleccionar los archivos de pedido
     */

    public static String VentanaArchivo () {
        Scanner entrada = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(fileChooser);
        try {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            File f = new File(ruta);
            entrada = new Scanner(f);
            while (entrada.hasNext()) {
                System.out.println(entrada.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("No se ha seleccionado ningún fichero");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (entrada != null) {
                entrada.close();
            }
        }
        return "";
    }

}


