
package crud;

import modelo.Persona;
import modelo.Querys;
import modelo.Conexion;
import vista.VentanaCrud;
import controlador.ControladorPersona;


public class CRUD 
{

    
    public static void main(String[] args) 
    {
        VentanaCrud ventana = new VentanaCrud();
        Persona persona = new Persona();
        Querys querys = new Querys();
        ControladorPersona controlador = new ControladorPersona(ventana, persona, querys);
        
        controlador.iniciar();
        ventana.setVisible(true);
        
        
        /*VentanaCrud ventana = new VentanaCrud();
        ventana.setVisible(true);
        Conexion cx = new Conexion();
        cx.conectar();*/
        
        /*StringBuilder sb = new StringBuilder("Hola mundo");
        //sb.delete(3, 7);
        sb.append(", los amo");
        sb.append(".");
        System.out.println("Despues: "+sb.toString());*/
    }
    
}
