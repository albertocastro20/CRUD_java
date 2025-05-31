
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Persona;
import modelo.Querys;
import vista.VentanaCrud;

public class ControladorPersona implements ActionListener{
    private VentanaCrud ventana;
    private Persona persona;
    private Querys querys;
    
    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

    public ControladorPersona(VentanaCrud ventana, Persona persona, Querys querys) {
        this.ventana = ventana;
        this.persona = persona;
        this.querys = querys;
        
        ventana.botonInsertar.addActionListener(this);
        ventana.botonLimpiar.addActionListener(this);
        ventana.botonBuscar.addActionListener(this);
        ventana.botonActualizar.addActionListener(this);
        ventana.botonEliminar.addActionListener(this);
        
    }
    
    public void iniciar()
    {
        ventana.setTitle("CRUD");
        ventana.setLocationRelativeTo(null);
        ventana.CampoIdBuscar.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        if(ae.getSource() == ventana.botonBuscar)
        {
            persona = querys.buscar(ventana.campoClaveBuscar.getText());
            
            if(persona != null)
            {
                ventana.campoClave.setText(persona.getClave());
                ventana.campoNombre.setText(persona.getNombre());
                ventana.campoCelular.setText(persona.getCelular());
                
                
                Date fecha = persona.getFecha();
                String fechaS = fecha.toString();
                
                ventana.campoFecha.setText(fechaS);
                
                
                if(persona.getGenero().equals("Masculino"))
                {
                    int combo = 1;
                    ventana.comboGenero.setSelectedIndex(combo);
                }
                
                else
                {
                   
                    ventana.comboGenero.setSelectedIndex(2);
                    
                }
      
            }
            
            else
            {
                JOptionPane.showMessageDialog(null, "Persona no encontrada");
            }
        }
        
        if(ae.getSource() == ventana.botonInsertar)
        {
            java.sql.Date mFechaSql = null;
            
            try {
                java.util.Date mFecha = formato.parse(ventana.campoFecha.getText());
                
                mFechaSql = new java.sql.Date(mFecha.getTime());
            } catch (ParseException ex) {
                System.err.println("Error: "+ex);
            }
            
            persona.setClave(ventana.campoClave.getText());
            persona.setNombre(ventana.campoNombre.getText());
            persona.setCelular(ventana.campoCelular.getText());
            persona.setFecha(mFechaSql);
            persona.setGenero(ventana.comboGenero.getSelectedItem().toString());
            
            if(querys.insertar(persona))
            {
                JOptionPane.showMessageDialog(null, "Registrado Correctamente.");
               ventana.limpiarCampos();
            }
            
            else
            {
                JOptionPane.showMessageDialog(null, "Error al realizar el registro.");
            }
        }
        
        
        if(ae.getSource() == ventana.botonLimpiar)
        {
            ventana.limpiarCampos();
        }
        
        if (ae.getSource() == ventana.botonActualizar)
        {
            //Obtenemos los datos de la persona
            java.sql.Date mFechaSql = null;
            
            try {
                java.util.Date mFecha = formato.parse(ventana.campoFecha.getText());
                
                mFechaSql = new java.sql.Date(mFecha.getTime());
            } catch (ParseException ex) {
                System.err.println("Error: "+ex);
            }
            
            persona.setClave(ventana.campoClave.getText());
            persona.setNombre(ventana.campoNombre.getText());
            persona.setCelular(ventana.campoCelular.getText());
            persona.setFecha(mFechaSql);
            persona.setGenero(ventana.comboGenero.getSelectedItem().toString());
            
            if(querys.actualizar(ventana.campoClaveBuscar.getText() , persona))
            {
                JOptionPane.showMessageDialog(null, "Actualizado correctamente");
                ventana.limpiarCampos();
            }
            
            else
            {
                JOptionPane.showMessageDialog(null, "Error al actualizar");
            }
            
        }
        
        if(ae.getSource() == ventana.botonEliminar)
        {
            System.out.println("boton presionado");
            String clave = ventana.campoClave.getText();
            
            if(querys.borrar(clave))
            {
                JOptionPane.showMessageDialog(null, "Eliminado correctamente");
                ventana.limpiarCampos();
            }
            
            else
            {
                JOptionPane.showMessageDialog(null, "Error al eliminar registro");
            }
           
        }
        
        
        
    }
    
    
    
    
}
