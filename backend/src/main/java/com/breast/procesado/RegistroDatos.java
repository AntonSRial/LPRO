import java.io.*;
import java.time.LocalTime;
import java.util.*; 

public class RegistroDatos {

	int ID;
	Date Fecha;
	LocalTime Hora;
	ArrayList<Double> Medidas;
	
	
	public String print_medidas() {
		String Salida;
		Salida="";
        for( int i = 0 ; i < Medidas.size() ; i++ ){
        	
      	Salida=Salida+Medidas.get(i).toString()+"--";
      	
      	}
        return Salida;
		
	}

	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public Date getFecha() {
		return Fecha;
	}
	public void setFecha(Date fecha) {
		Fecha = fecha;
	}
	public LocalTime getHora() {
		return Hora;
	}
	public void setHora(LocalTime hora) {
		Hora = hora;
	}
	public ArrayList<Double> getMedidas() {
		return Medidas;
	}
	public void setMedidas(ArrayList<Double> medidas) {
		Medidas = medidas;
	}
	
}
