import java.time.LocalTime;
import java.util.Date;

public class RegistroAvisos {

	int ID_sensor;
	Date Fecha;
	LocalTime Hora;
	Double Medidas;
	
	
	
	public int getID_sensor() {
		return ID_sensor;
	}
	public void setID_sensor(int iD_sensor) {
		ID_sensor = iD_sensor;
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
	public Double getMedidas() {
		return Medidas;
	}
	public void setMedidas(Double medidas) {
		Medidas = medidas;
	}
	
	
}
