import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.*; 
import java.io.*;

public class DatosMedias {
	
	int contador;
	ArrayList<Double> suma_medidas;
	ArrayList<Double> media_medidas;
	
	public String print_medias() {
		String Salida_Medias;
		Salida_Medias="";
        for( int i = 0 ; i < media_medidas.size() ; i++ ){
        	
        	Salida_Medias=Salida_Medias+media_medidas.get(i).toString()+"--";
      	
      	}
        return Salida_Medias;
		
	}
	
	public String print_SUMAS() {
		String Salida_SUMAS;
		Salida_SUMAS="";
        for( int i = 0 ; i < suma_medidas.size() ; i++ ){
        	
        	Salida_SUMAS=Salida_SUMAS+suma_medidas.get(i).toString()+"--";
      	
      	}
        return Salida_SUMAS;
		
	}
	
	
	public int getContador() {
		return contador;
	}
	public void setContador(int contador) {
		this.contador = contador;
	}
	public ArrayList<Double> getSuma_medidas() {
		return suma_medidas;
	}
	public void setSuma_medidas(ArrayList<Double> suma_medidas) {
		this.suma_medidas = new ArrayList<Double>(suma_medidas);
	}
	public ArrayList<Double> getMedia_medidas() {
		return media_medidas;
	}
	public void setMedia_medidas(ArrayList<Double> media_medidas) {
		this.media_medidas =new ArrayList<Double>(media_medidas);
	}

}
