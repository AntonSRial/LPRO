import java.util.ArrayList;
import java.util.HashMap;

public class Resultados {

	 HashMap<Integer, DatosMedias> resultado_medias_mes = new HashMap<Integer, DatosMedias>();
	 HashMap<Integer, DatosMedias> resultado_medias_semana = new HashMap<Integer, DatosMedias>();
	 HashMap<Integer, DatosMedias> resultado_medias_ano = new HashMap<Integer, DatosMedias>();
	 HashMap<Integer, DatosMedias> resultado_medias_hora = new HashMap<Integer, DatosMedias>();
	 ArrayList<String> resultado_avisos = new ArrayList<String>();
	 ArrayList<String> resultado_conclusion = new ArrayList<String>();
	 
	 
	public HashMap<Integer, DatosMedias> getResultado_medias_mes() {
		return resultado_medias_mes;
	}
	public void setResultado_medias_mes(HashMap<Integer, DatosMedias> resultado_medias_mes) {
		this.resultado_medias_mes = resultado_medias_mes;
	}
	public HashMap<Integer, DatosMedias> getResultado_medias_semana() {
		return resultado_medias_semana;
	}
	public void setResultado_medias_semana(HashMap<Integer, DatosMedias> resultado_medias_semana) {
		this.resultado_medias_semana = resultado_medias_semana;
	}
	public HashMap<Integer, DatosMedias> getResultado_medias_ano() {
		return resultado_medias_ano;
	}
	public void setResultado_medias_ano(HashMap<Integer, DatosMedias> resultado_medias_ano) {
		this.resultado_medias_ano = resultado_medias_ano;
	}
	public HashMap<Integer, DatosMedias> getResultado_medias_hora() {
		return resultado_medias_hora;
	}
	public void setResultado_medias_hora(HashMap<Integer, DatosMedias> resultado_medias_hora) {
		this.resultado_medias_hora = resultado_medias_hora;
	}
	public ArrayList<String> getResultado_avisos() {
		return resultado_avisos;
	}
	public void setResultado_avisos(ArrayList<String> resultado_avisos) {
		this.resultado_avisos = new ArrayList<String>(resultado_avisos);
	}
	public ArrayList<String> getResultado_conclusion() {
		return resultado_conclusion;
	}
	public void setResultado_conclusion(ArrayList<String> resultado_conclusion) {
		this.resultado_conclusion = resultado_conclusion;
	}
	 
	 
	 
}
