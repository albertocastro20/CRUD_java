
package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Querys extends Conexion {
    
    PreparedStatement ps;
    ResultSet rs;
    Connection cx = null;
    
    public boolean insertar(Persona persona)
    {
        cx = conectar();
        
        try 
        {
            ps = cx.prepareStatement("INSERT INTO persona (clave, nombre, celular, fechaN, genero) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, persona.getClave());
            ps.setString(2, persona.getNombre());
            ps.setString(3, persona.getCelular());
            ps.setDate(4, persona.getFecha());
            ps.setString(5, persona.getGenero());
            
            int resultado = ps.executeUpdate();
            
            if(resultado > 0)
            {
                return true;
            }
            
            else
            {
                return false;
            }
        } 
        catch (Exception ex)
        {
            System.err.println("Error: "+ex);
            return false;
            
        }finally
        {
            try {
                cx.close();
            } catch (Exception ex) {
                System.err.println("Error: "+ex);
            }
        }
        
        
    }
    
    public Persona buscar(String clave)
    {
        cx = conectar();
        Persona persona = null;
        
        String fecha;
        
        
        try {
            ps = cx.prepareStatement("SELECT * FROM persona WHERE clave = ?");
            ps.setString(1, clave);
            
            rs = ps.executeQuery();
            
            if(rs.next())
            {
                persona = new Persona();
                
                
                persona.setClave(rs.getString("clave"));
                persona.setNombre(rs.getString("nombre"));
                persona.setCelular(rs.getString("celular"));
                persona.setFecha(rs.getDate("fechaN"));
                persona.setGenero(rs.getString("genero"));
            }
            
        } catch (SQLException ex) {
            System.err.println("Error "+ex);
        }
        
        
        return persona;
        
    }
    
    public boolean actualizar(String clave, Persona personaNueva)
    {
        cx = conectar();
        
        Persona personaActual = new Persona();
        personaActual = buscar(clave);
        
        String queryActualizar;
        StringBuilder sb = new StringBuilder("UPDATE persona SET ");
        
        ArrayList<String> parametros = new ArrayList<>();
        
        if(!personaActual.getClave().equals(personaNueva.getClave()))
        {
            sb.append("clave = ?, ");
            parametros.add(personaNueva.getClave());
        }
        
        
        if(!personaActual.getNombre().equals(personaNueva.getNombre()))
        {
            sb.append("nombre = ?, ");
            parametros.add(personaNueva.getNombre());
        }
        
        if(!personaActual.getCelular().equals(personaNueva.getCelular()))
        {
            sb.append("celular = ?, ");
            parametros.add(personaNueva.getCelular());
        }
        
        
        //Verificamos si las fechas coinciden
        Date fechaActual = personaActual.getFecha();
        Date fechaNueva = personaNueva.getFecha();
        
        String fechaAS = fechaActual.toString();
        String fechaNS = fechaNueva.toString();
        
        if(!fechaAS.equals(fechaNS))
        {
            sb.append("fechaN = ?, ");
            parametros.add(fechaNS);
        }
        
        if(!personaActual.getGenero().equals(personaNueva.getGenero()))
        {
            sb.append("genero = ?, ");
            parametros.add(personaNueva.getGenero());
        }
        
        sb.delete(sb.length()-2, sb.length());
        sb.append(" WHERE clave = ?");
        
        queryActualizar = sb.toString();
        for(int i = 0; i<parametros.size(); i++)
        {
            System.out.println(parametros.get(i));
        }
        
        System.out.println("Query: "+queryActualizar);
        
        try {
            ps = cx.prepareStatement(queryActualizar);
            for(int i = 0; i<parametros.size(); i++)
            {
                ps.setObject(i+1, parametros.get(i));
            }
            ps.setString(parametros.size()+1, personaActual.getClave());
            
            int resultado = ps.executeUpdate();
            
            if(resultado > 0)
            {
                return true;
            }
            
            else
            {
                return false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Querys.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    public boolean borrar(String clave)
    {
        cx = conectar();
        try {
            ps = cx.prepareStatement("DELETE FROM persona WHERE clave = ?");
            ps.setString(1, clave);
            
            int resultado = ps.executeUpdate();
            
            if(resultado > 0)
            {
                return true;
            }
            
            else
            {
                return false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Querys.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally
        {
            try {
                cx.close();
            } catch (Exception ex) {
                System.err.println("Error: "+ex);
            }
        }
       
        
    }
    
}
