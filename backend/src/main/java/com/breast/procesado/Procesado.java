
import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.*;

/**
 * 
 * Clase principal donde se lee el fichero con la función LeerFichero(), se
 * procesan los datos ProcesarDatos() y se generan ficheros GenerarFicheros().
 *  
 * Todos los resultados se guardan en la clase Resultados.java, para que así sea
 * más fácil de manejar, que es lo que se devuelve en el main.
 * 
 * @author Silvia Abal Fernández
 * 
 * @version 07/04/2020
 *
 */

public class Procesado {

	static File archivo = null;
	static FileReader fr = null;
	static BufferedReader br = null;

	static String linea = null;
	static ArrayList<RegistroDatos> datos = new ArrayList<RegistroDatos>();
	static ArrayList<RegistroDatos> datos_finales = new ArrayList<RegistroDatos>();

	static String[] campos = null;
	static ArrayList<Double> campo_medidas_ecuaciones = new ArrayList<Double>();
	static String[] str_fecha = null;
	static String[] str_medidas = null;

	static ArrayList<Double> ec_temperaturas = new ArrayList<Double>();
	static ArrayList<Double> ec_Rt = new ArrayList<Double>();
	static ArrayList<Double> ec_Vm = new ArrayList<Double>();

	static ArrayList<Double> ec_Temp_valores_convertidos = new ArrayList<Double>();

	static String ec_valores_array;
	static String[] ec_valores = null;
	static Double ec_valor;

	static ArrayList<RegistroAvisos> avisos_temperatura = new ArrayList<RegistroAvisos>();

	static HashMap<Integer, DatosMedias> HashMap_medias_mes = new HashMap<Integer, DatosMedias>();
	static HashMap<Integer, DatosMedias> HashMap_medias_semana = new HashMap<Integer, DatosMedias>();
	static HashMap<Integer, DatosMedias> HashMap_medias_ano = new HashMap<Integer, DatosMedias>();
	static HashMap<Integer, DatosMedias> HashMap_medias_hora = new HashMap<Integer, DatosMedias>();

	static ArrayList<String> avisos = new ArrayList<String>();
	static ArrayList<String> conclusion = new ArrayList<String>();

	/**
	 * Lectura del fichero proveniente de la BDD BReAst, de la forma ID;Fecha;Hora;Valores tension de los 10 sensores;
	 */
	public static void LeerFichero() {

		try {
			/*
			 * Apertura del fichero y creacion de BufferedReader para poder hacer una
			 * lectura comoda (disponer del metodo readLine()).
			 */
			archivo = new File("DataSensors_13_35.txt");
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero

			while ((linea = br.readLine()) != null) {
				RegistroDatos reg = new RegistroDatos();
				campos = linea.split(";");
				reg.setID(Integer.parseInt(campos[0]));
				str_fecha = campos[1].split("/");
				// para manejar la fecha con Date tiene que tener el formato año-mes-dia
				reg.setFecha(Date.valueOf(str_fecha[2] + "-" + str_fecha[1] + "-" + str_fecha[0]));
				reg.setHora(LocalTime.parse(campos[2]));

				ArrayList<Double> campo_medidas = new ArrayList<Double>();

				str_medidas = campos[3].split(",");
				for (int i = 0; i < str_medidas.length; i++) {
					campo_medidas.add(Double.valueOf(str_medidas[i]));
					campo_medidas_ecuaciones.add(Double.valueOf(str_medidas[i]));
				}
				reg.setMedidas(campo_medidas);
				datos.add(reg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 *Función principal en la que se convierten los valores de tension de entrada de los sensores a sus correspondientes valores de temperatura en grados,
	 *para después realizar los calculos de medias por año, mes, semana y hora 
	 */

	public static void ProcesarDatos() {
		// Lo primero se convierten los datos de voltaje en temperatura
		Ecuaciones();
		// Se recorren los datos de temperatura para saber si hay que generar algún
		// aviso
		Avisos();
		// Finalemnte se calculan las medias
		for (int n_reg = 0; n_reg < datos_finales.size(); n_reg++) {
			Calculo_media_mes(datos_finales.get(n_reg));
			Calculo_media_semana(datos_finales.get(n_reg));
			Calculo_media_ano(datos_finales.get(n_reg));
			Calculo_media_hora(datos_finales.get(n_reg));
		}
	}

	/**
	 * En esta función se convierten los valores de tensión de entrada a sus valores
	 * de temperatura asociados
	 */
	public static void Ecuaciones() {
		// datos proporcionados por el DataSheet
		double A = -1.5573783E+01;
		double B = 5.0310600E+03;
		double C = -8.5956133E+04;
		double D = -8.8392667E+06;
		double minimo = 0.0;
		int contador;
		ArrayList<Double> valores_convertidos = new ArrayList<Double>();

		/*
		 * los sensores tienen una precision de 0.01 grados, por lo que de 0 a 50,
		 * tenemos 5000 valores de temperatura
		 */
		for (int i = 0; i < 5001; i++) {
			if (i == 0) {
				ec_temperaturas.add(273.15);
				ec_Rt.add((Math.exp(A + B / ec_temperaturas.get(i) + C / (Math.pow(ec_temperaturas.get(i), 2))
						+ D / (Math.pow(ec_temperaturas.get(i), 3)))) * 10000);
				ec_Vm.add(3.3 * ec_Rt.get(i) / (ec_Rt.get(i) + 10000));

			} else {
				ec_temperaturas.add((double) i / 100 + 273.15);
				ec_Rt.add((Math.exp(A + B / ec_temperaturas.get(i) + C / (Math.pow(ec_temperaturas.get(i), 2))
						+ D / (Math.pow(ec_temperaturas.get(i), 3)))) * 10000);
				ec_Vm.add(3.3 * ec_Rt.get(i) / (ec_Rt.get(i) + 10000));
			}
		}

		/*
		 * como se hace un add, los valores se meten en una misma fila, hay que recorrer
		 * de 10 en 10
		 */
		for (int i = 0; i < campo_medidas_ecuaciones.size(); i++) {
			ec_valor = campo_medidas_ecuaciones.get(i);
			minimo = Math.abs(ec_Vm.get(0) - ec_valor);
			for (int j = 1; j < ec_temperaturas.size(); j++) {
				minimo = Math.min(Math.abs(ec_Vm.get(j) - ec_valor), minimo);
			}
			valores_convertidos.add(minimo);
		}

		/*
		 * yo se cual es el minimo, pero me interesa saber DONDE esta Y DE AHI SACAR LAS
		 * TEMPERATURAS
		 */

		for (int i = 0; i < valores_convertidos.size(); i++) {
			ec_valor = campo_medidas_ecuaciones.get(i);
			contador = 1;
			for (int j = 0; j < ec_temperaturas.size(); j++) {
				if (j == 0) {
					minimo = Math.abs(ec_Vm.get(0) - ec_valor);
				} else {

					minimo = Math.min(Math.abs(ec_Vm.get(j) - ec_valor), minimo);

					if (minimo == valores_convertidos.get(i) && contador == 1) {
						ec_Temp_valores_convertidos.add(ec_temperaturas.get(j) - 273.15); // en grados mejor
						contador = 0;
					}
				}
			}
		}
		int posicion_array;
		contador = 0;
		int contador_final;
		for (int i = 0; i < datos.size(); i++) {
			RegistroDatos reg_ecuaciones = new RegistroDatos();
			ArrayList<Double> valores_convertidos_10 = new ArrayList<Double>();
			contador_final = 0;

			if (i == 0) {
				posicion_array = 0;
			} else {
				posicion_array = contador;
			}

			for (int j = posicion_array; j < ec_Temp_valores_convertidos.size(); j++) {

				if (contador_final < 10) {

					contador = contador + 1; // posicion siguiente
					valores_convertidos_10.add(ec_Temp_valores_convertidos.get(j));
					contador_final = contador_final + 1;
				}
			}
			int ID_ = datos.get(i).getID();
			reg_ecuaciones.setMedidas(valores_convertidos_10);
			reg_ecuaciones.setFecha(datos.get(i).getFecha());
			reg_ecuaciones.setHora(datos.get(i).getHora());
			reg_ecuaciones.setID(ID_);

			datos_finales.add(reg_ecuaciones);
		}
	}

	/**
	 * Se generan los avisos cuando una de las medidas de temperatura excede el
	 * límite de 38.2º, una vez hecho esto se sacan conclusiones por mes
	 */
	public static void Avisos() {

		ArrayList<Double> valores_temperatura = new ArrayList<Double>();
		Double valor_temperatura;

		int contador_sensor;
		for (int i = 0; i < datos_finales.size(); i++) {
			valores_temperatura = datos_finales.get(i).getMedidas();
			contador_sensor = 0;
			for (int j = 0; j < valores_temperatura.size(); j++) {
				valor_temperatura = valores_temperatura.get(j);
				contador_sensor = contador_sensor + 1;
				if ((valor_temperatura) > 38.2) {
					RegistroAvisos reg_avisos = new RegistroAvisos();
					reg_avisos.setMedidas(valor_temperatura);
					reg_avisos.setFecha(datos_finales.get(i).getFecha());
					reg_avisos.setHora(datos_finales.get(i).getHora());
					reg_avisos.setID_sensor(contador_sensor);
					avisos_temperatura.add(reg_avisos);
					avisos.add("Día: " + datos_finales.get(i).getFecha() + " Hora: " + datos_finales.get(i).getHora()
							+ " Sensor: " + contador_sensor + " Temperatura: " + valor_temperatura);
				}
			}
		}
		// ordeno los avisos por fecha
		Collections.sort(avisos);
		ArrayList<Integer> a_sensor = new ArrayList<Integer>();

		for (int j = 0; j < avisos_temperatura.size(); j++) {
			Date Fecha = (Date) avisos_temperatura.get(j).getFecha();

			Calendar calendar = Calendar.getInstance();
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			calendar.setMinimalDaysInFirstWeek(4);
			calendar.setTime(Fecha);

			int Ano = calendar.get(Calendar.YEAR);
			int Mes = calendar.get(Calendar.MONTH) + 1;
			int ID_sensor = avisos_temperatura.get(j).getID_sensor();

			int clave = Ano * 10000 + Mes * 100 + ID_sensor;

			a_sensor.add(clave);
		}

		Set<Integer> hashSet = new HashSet<Integer>(a_sensor);
		a_sensor.clear();
		a_sensor.addAll(hashSet);
		Collections.sort(a_sensor);

		int ano;
		int mes;
		int sensor;
		int clave = 0;
		String va = "";
		String vas = "";
		String s_mes;
		int varios = 0;
		for (int i = 0; i < a_sensor.size(); i++) {
			ano = a_sensor.get(i) / 10000;
			mes = (a_sensor.get(i) / 100) % 100;
			sensor = a_sensor.get(i) % 100;
			switch (mes) {
			case 1:
				s_mes = "Enero";
				break;
			case 2:
				s_mes = "Febrero";
				break;
			case 3:
				s_mes = "Marzo";
				break;
			case 4:
				s_mes = "Abril";
				break;
			case 5:
				s_mes = "Mayo";
				break;
			case 6:
				s_mes = "Junio";
				break;
			case 7:
				s_mes = "Julio";
				break;
			case 8:
				s_mes = "Agosto";
				break;
			case 9:
				s_mes = "Septiembre";
				break;
			case 10:
				s_mes = "Octubre";
				break;
			case 11:
				s_mes = "Noviembre";
				break;

			default:
				s_mes = "Diciembre";
				break;
			}

			if (clave != a_sensor.get(i) / 100) {
				if (clave != 0) {
					if (varios == 1) {
						va = va + "el sensor: " + vas;
					} else {
						va = va + "estos sensores: " + vas;
					}
					conclusion.add(va);
				}
				va = ("Durante el mes de " + s_mes + " del " + ano
						+ " se ha sobrepasado la temperatura límite establecida en ");
				vas = "" + sensor;
				varios = 1;
			} else {
				vas = (vas + " y " + sensor);
				varios = varios + 1;
			}
			clave = a_sensor.get(i) / 100;
		}
		if (clave != 0) {
			if (varios == 1) {
				va = va + "el sensor: " + vas;
			} else {
				va = va + "estos sensores: " + vas;
			}
			conclusion.add(va);
		}
	}

	/**
	 * Calculo de las medias de temperatura por mes
	 *  
	 * @param valores registro del arraylist datos_finales que es del tipo RegistroDatos
	 */

	public static void Calculo_media_mes(RegistroDatos valores) {
		Date Fecha_;
		Fecha_ = (Date) valores.getFecha();
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(4);
		calendar.setTime(Fecha_);
		
		 /*IMPORTANTE A TENER EN CUENTA, la clave de este hashmap es el año x 100 +
		 * numero del mes, para que así se tenga en cuenta el año, a la hora de sacar la
		 * clave del HashMap habrá que para obtener el año dividir x 100, el mes
		 * quedarnos con el resto %100
		 */
		int ano = calendar.get(Calendar.YEAR);
		int Mes = calendar.get(Calendar.MONTH) + 1;
		int Clave_Hashmap_Mes = ano * 100 + Mes;
		DatosMedias registro_medias = new DatosMedias();

		if (HashMap_medias_mes.containsKey(Clave_Hashmap_Mes)) {
			registro_medias = HashMap_medias_mes.get(Clave_Hashmap_Mes);
			registro_medias.setContador(registro_medias.getContador() + 1);
			int d_contador = registro_medias.getContador();
			double suma = 0;
			double media = 0;

			ArrayList<Double> a_Medidas = new ArrayList<Double>();
			ArrayList<Double> a_medias = new ArrayList<Double>();
			ArrayList<Double> a_sumas = new ArrayList<Double>();

			// valores sensores actuales
			a_Medidas = valores.getMedidas();
			// sumas de valores anteriores

			a_sumas = registro_medias.getSuma_medidas();
			a_medias = registro_medias.getMedia_medidas();

			for (int i = 0; i < a_Medidas.size(); i++) {
				suma = Double.sum(a_sumas.get(i).doubleValue(), a_Medidas.get(i).doubleValue());
				media = (Double) suma / d_contador;
				a_sumas.set(i, suma);
				a_medias.set(i, media);
			}

			registro_medias.setSuma_medidas(a_sumas);
			registro_medias.setMedia_medidas(a_medias);

			HashMap_medias_mes.replace(Clave_Hashmap_Mes, registro_medias);

		} else {
			registro_medias.setContador(1);

			registro_medias.setMedia_medidas(valores.getMedidas());
			registro_medias.setSuma_medidas(valores.getMedidas());
			HashMap_medias_mes.put(Clave_Hashmap_Mes, registro_medias);
		}

	}

	/**
	 * Calculo de las medias de temperatura por semana
	 *
	 * @param valores registro del arraylist datos_finales que es del tipo RegistroDato
	 */
	public static void Calculo_media_semana(RegistroDatos valores) {
		Date Fecha_;
		Fecha_ = (Date) valores.getFecha();

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(4);
		calendar.setTime(Fecha_);
		int Num_semana = calendar.get(Calendar.WEEK_OF_YEAR);
		int Ano_ = calendar.get(Calendar.YEAR) * 10000;
		int Mes_ = (calendar.get(Calendar.MONTH) + 1) * 100;
		
		/*
		 * IMPORTANTE A TENER EN CUENTA, la clave de este hashmap es el año x 10000 +
		 * numero del mes x 100 + numero de semana, para que así se tenga en cuenta
		 * tanto el año como el mes, a la hora de sacar la clave del HashMap habrá que
		 * para obtener el año dividir x 10000, el mes dividir x 100 y quedarnos con el
		 * resto %100, la semana %100
		 */

		int Clave_hashmap = Ano_ + Mes_ + Num_semana;

		DatosMedias registro_medias = new DatosMedias();

		if (HashMap_medias_semana.containsKey(Clave_hashmap)) {
			registro_medias = HashMap_medias_semana.get(Clave_hashmap);
			registro_medias.setContador(registro_medias.getContador() + 1);
			int d_contador = registro_medias.getContador();
			double suma = 0;
			double media = 0;

			ArrayList<Double> a_Medidas = new ArrayList<Double>();
			ArrayList<Double> a_medias = new ArrayList<Double>();
			ArrayList<Double> a_sumas = new ArrayList<Double>();

			// valores sensores actuales
			a_Medidas = valores.getMedidas();
			// sumas de valores anteriores

			a_sumas = registro_medias.getSuma_medidas();
			a_medias = registro_medias.getMedia_medidas();

			for (int i = 0; i < a_Medidas.size(); i++) {
				suma = Double.sum(a_sumas.get(i).doubleValue(), a_Medidas.get(i).doubleValue());
				media = (Double) suma / d_contador;
				a_sumas.set(i, suma);
				a_medias.set(i, media);
			}

			registro_medias.setSuma_medidas(a_sumas);
			registro_medias.setMedia_medidas(a_medias);

			HashMap_medias_semana.replace(Clave_hashmap, registro_medias);

		} else {
			registro_medias.setContador(1);

			registro_medias.setMedia_medidas(valores.getMedidas());
			registro_medias.setSuma_medidas(valores.getMedidas());
			HashMap_medias_semana.put(Clave_hashmap, registro_medias);
		}

	}

	/**
	 * Calculo de las medias de temperatura por año
	 * 
	 * @param valores registro del arraylist datos_finales que es del tipo RegistroDato
	 */
	public static void Calculo_media_ano(RegistroDatos valores) {
		Date Fecha_;
		Fecha_ = (Date) valores.getFecha();

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(4);
		calendar.setTime(Fecha_);

		int Ano_ = calendar.get(Calendar.YEAR);

		DatosMedias registro_medias = new DatosMedias();

		if (HashMap_medias_ano.containsKey(Ano_)) {
			registro_medias = HashMap_medias_ano.get(Ano_);
			registro_medias.setContador(registro_medias.getContador() + 1);
			int d_contador = registro_medias.getContador();
			double suma = 0;
			double media = 0;

			ArrayList<Double> a_Medidas = new ArrayList<Double>();
			ArrayList<Double> a_medias = new ArrayList<Double>();
			ArrayList<Double> a_sumas = new ArrayList<Double>();

			// valores sensores actuales
			a_Medidas = valores.getMedidas();
			// sumas de valores anteriores

			a_sumas = registro_medias.getSuma_medidas();
			a_medias = registro_medias.getMedia_medidas();

			for (int i = 0; i < a_Medidas.size(); i++) {
				suma = Double.sum(a_sumas.get(i).doubleValue(), a_Medidas.get(i).doubleValue());
				media = (Double) suma / d_contador;
				a_sumas.set(i, suma);
				a_medias.set(i, media);
			}

			registro_medias.setSuma_medidas(a_sumas);
			registro_medias.setMedia_medidas(a_medias);

			HashMap_medias_ano.replace(Ano_, registro_medias);

		} else {
			registro_medias.setContador(1);

			registro_medias.setMedia_medidas(valores.getMedidas());
			registro_medias.setSuma_medidas(valores.getMedidas());
			HashMap_medias_ano.put(Ano_, registro_medias);
		}

	}

	/**
	 * Calculo de las medias de temperatura por hora
	 * 
	 * @param valores registro del arraylist datos_finales que es del tipo RegistroDatos
	 */

	public static void Calculo_media_hora(RegistroDatos valores) {
		Date Fecha_;
		LocalTime Hora_Completa;
		Fecha_ = (Date) valores.getFecha();
		Hora_Completa = valores.getHora();
		int Hora;
		Hora = Hora_Completa.getHour();
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(4);
		calendar.setTime(Fecha_);
		
		/*
		 * IMPORTANTE A TENER EN CUENTA, la clave de este hashmap se hace de forma
		 * análoga a las anteriores, y para sacarla es igual que la de las semanas
		 */
		int Mes = calendar.get(Calendar.MONTH) + 1;
		int Ano_ = calendar.get(Calendar.YEAR);
		int Clave_Hashmap_Hora = Ano_ * 10000 + Mes * 100 + Hora;

		DatosMedias registro_medias = new DatosMedias();

		if (HashMap_medias_hora.containsKey(Clave_Hashmap_Hora)) {
			registro_medias = HashMap_medias_hora.get(Clave_Hashmap_Hora);
			registro_medias.setContador(registro_medias.getContador() + 1);
			int d_contador = registro_medias.getContador();
			double suma = 0;
			double media = 0;

			ArrayList<Double> a_Medidas = new ArrayList<Double>();
			ArrayList<Double> a_medias = new ArrayList<Double>();
			ArrayList<Double> a_sumas = new ArrayList<Double>();

			// valores sensores actuales
			a_Medidas = valores.getMedidas();
			// sumas de valores anteriores

			a_sumas = registro_medias.getSuma_medidas();
			a_medias = registro_medias.getMedia_medidas();

			for (int i = 0; i < a_Medidas.size(); i++) {
				suma = Double.sum(a_sumas.get(i).doubleValue(), a_Medidas.get(i).doubleValue());
				media = (Double) suma / d_contador;
				a_sumas.set(i, suma);
				a_medias.set(i, media);
			}

			registro_medias.setSuma_medidas(a_sumas);
			registro_medias.setMedia_medidas(a_medias);

			HashMap_medias_hora.replace(Clave_Hashmap_Hora, registro_medias);

		} else {
			registro_medias.setContador(1);

			registro_medias.setMedia_medidas(valores.getMedidas());
			registro_medias.setSuma_medidas(valores.getMedidas());
			HashMap_medias_hora.put(Clave_Hashmap_Hora, registro_medias);
		}

	}

	/**
	 * Generación de ficheros independientes con cada uno de los
 * 	resultados del calculo de medias, para que así se puedan generar
 * posteriormente las gráficas correspondientes en Matlab.
	 * 
	 */

	public static void GenerarFicheros() {
		FileWriter fichero_ano = null;
		PrintWriter pw = null;
		int ano_fichero;

		// ordenar datos
		List<Integer> keys = new ArrayList<Integer>(HashMap_medias_ano.keySet());
		Collections.sort(keys);

		List<Integer> keys_mes = new ArrayList<Integer>(HashMap_medias_mes.keySet());
		Collections.sort(keys_mes);

		List<Integer> keys_semana = new ArrayList<Integer>(HashMap_medias_semana.keySet());
		Collections.sort(keys_semana);

		List<Integer> keys_hora = new ArrayList<Integer>(HashMap_medias_hora.keySet());
		Collections.sort(keys_hora);

		String medias_ano_fichero;
		/////////////////////////// FICHERO DE MEDIAS POR AÑO
		/////////////////////////// ///////////////////////////
		try {
			fichero_ano = new FileWriter("Resultados_Medias_Ano.txt");
			pw = new PrintWriter(fichero_ano);

			for (int i = 0; i < keys.size(); i++) {
				int valor_key = (int) keys.get(i);
				for (Map.Entry<Integer, DatosMedias> valor_ano : HashMap_medias_ano.entrySet()) {
					if (valor_key == valor_ano.getKey()) {
						ano_fichero = valor_ano.getKey();
						medias_ano_fichero = valor_ano.getValue().getMedia_medidas().toString();
						medias_ano_fichero = medias_ano_fichero.replace("[", "").replace("]", "");
						pw.println(ano_fichero + ";" + medias_ano_fichero + ",;");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (null != fichero_ano)
					fichero_ano.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		/////////////////////////// FICHERO DE MEDIAS POR MES
		/////////////////////////// ///////////////////////////
		FileWriter fichero_mes = null;
		PrintWriter pw_mes = null;
		int ano_mes_fichero;
		int mes_fichero;
		String medias_mes_fichero;
		try {
			fichero_mes = new FileWriter("Resultados_Medias_Mes.txt");
			pw_mes = new PrintWriter(fichero_mes);

			for (int i = 0; i < keys_mes.size(); i++) {
				int valor_key = (int) keys_mes.get(i);
				for (Map.Entry<Integer, DatosMedias> valor_mes : HashMap_medias_mes.entrySet()) {
					if (valor_key == valor_mes.getKey()) {
						ano_mes_fichero = valor_mes.getKey() / 100;
						mes_fichero = valor_mes.getKey() % 100;
						medias_mes_fichero = valor_mes.getValue().getMedia_medidas().toString();
						medias_mes_fichero = medias_mes_fichero.replace("[", "").replace("]", "");
						pw_mes.println(ano_mes_fichero + ";" + mes_fichero + ";" + medias_mes_fichero + ",;");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero_mes)
					fichero_mes.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		/////////////////////////// FICHERO DE MEDIAS POR SEMANA
		/////////////////////////// ///////////////////////////
		FileWriter fichero_semana = null;
		PrintWriter pw_semana = null;
		int ano_semana_fichero;
		int mes_semana_fichero;
		int semana_fichero;
		String medias_semana_fichero;
		try {
			fichero_semana = new FileWriter("Resultados_Medias_Semana.txt");
			pw_semana = new PrintWriter(fichero_semana);

			for (int i = 0; i < keys_semana.size(); i++) {
				int valor_key = (int) keys_semana.get(i);
				for (Map.Entry<Integer, DatosMedias> valor_semana : HashMap_medias_semana.entrySet()) {
					if (valor_key == valor_semana.getKey()) {
						ano_semana_fichero = valor_semana.getKey() / 10000;
						mes_semana_fichero = (valor_semana.getKey() / 100) % 100;
						semana_fichero = valor_semana.getKey() % 100;
						medias_semana_fichero = valor_semana.getValue().getMedia_medidas().toString();
						medias_semana_fichero = medias_semana_fichero.replace("[", "").replace("]", "");

						pw_semana.println(ano_semana_fichero + ";" + mes_semana_fichero + ";" + semana_fichero + ";"
								+ medias_semana_fichero + ",;");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero_semana)
					fichero_semana.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			

		}
		/////////////////////////// FICHERO DE MEDIAS POR HORA
		/////////////////////////// ///////////////////////////
		FileWriter fichero_hora = null;
		PrintWriter pw_hora = null;
		int ano_hora_fichero;
		int mes_hora_fichero;
		int hora_fichero;
		String medias_hora_fichero;
		try {
			fichero_hora = new FileWriter("Resultados_Medias_Hora.txt");
			pw_hora = new PrintWriter(fichero_hora);

			for (int i = 0; i < keys_hora.size(); i++) {
				int valor_key = (int) keys_hora.get(i);
				for (Map.Entry<Integer, DatosMedias> valor_hora : HashMap_medias_hora.entrySet()) {
					if (valor_key == valor_hora.getKey()) {
						ano_hora_fichero = valor_hora.getKey() / 10000;
						mes_hora_fichero = (valor_hora.getKey() / 100) % 100;
						hora_fichero = valor_hora.getKey() % 100;
						medias_hora_fichero = valor_hora.getValue().getMedia_medidas().toString();
						medias_hora_fichero = medias_hora_fichero.replace("[", "").replace("]", "");

						pw_hora.println(ano_hora_fichero + ";" + mes_hora_fichero + ";" + hora_fichero + ";"
								+ medias_hora_fichero + ",;");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero_hora)
					fichero_hora.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
/////////////////////////// FICHERO DE TODAS LAS MEDIDAS CONVERTIDAS
/////////////////////////// ///////////////////////////
FileWriter fichero_medidas = null;
PrintWriter pw_medidas = null;

String medidas_fichero;
try {
fichero_medidas = new FileWriter("Resultados_Medidas.txt");
pw_medidas = new PrintWriter(fichero_medidas);



for(int i=0;i<datos_finales.size();i++) {
medidas_fichero=datos_finales.get(i).getMedidas().toString();
medidas_fichero=medidas_fichero.replace("[", "").replace("]", "");

pw_medidas.println(datos_finales.get(i).getID() +" "+ datos_finales.get(i).getFecha() +" "+ datos_finales.get(i).getHora() +"  "+ medidas_fichero);


}
} catch (Exception e) {
e.printStackTrace();
} finally {
try {
if (null != fichero_medidas)
fichero_medidas.close();
} catch (Exception e2) {
e2.printStackTrace();
}
}

///////////////////////////// FICHERO AVISOS
///////////////////////////// ///////////////////////////
FileWriter fichero_avisos = null;
PrintWriter pw_avisos = null;


try {
fichero_avisos = new FileWriter("Resultados_Avisos.txt");
pw_avisos = new PrintWriter(fichero_avisos);



for(int i=0;i<avisos.size();i++) {


	pw_avisos.println(avisos.get(i));
}

} catch (Exception e) {
e.printStackTrace();
} finally {
try {
if (null != fichero_avisos)
	fichero_avisos.close();
} catch (Exception e2) {
e2.printStackTrace();
}
}

/////////////////////////////// FICHERO CONCLUSIONES
/////////////////////////////// ///////////////////////////
//FileWriter fichero_CONCLUSIONES = null;
//PrintWriter pw_CONCLUSIONES = null;
//
//
//try {
//	fichero_CONCLUSIONES = new FileWriter("Resultados_Conclusiones.txt");
//pw_CONCLUSIONES = new PrintWriter(fichero_CONCLUSIONES);
//
//
//for (int j=0; j<conclusion.size();j++) {
//	pw_CONCLUSIONES.println(conclusion.get(j));
//}
//
//} catch (Exception e) {
//e.printStackTrace();
//} finally {
//try {
//if (null != fichero_CONCLUSIONES)
//	fichero_CONCLUSIONES.close();
//} catch (Exception e2) {
//e2.printStackTrace();
//}
//}
	}

	/**
	 * Visualización de los resultados en la consola
	 */
	public static void VisualizarResultados() {

		if (avisos_temperatura.isEmpty()) {
			System.out.println("No se han registrado temperaturas que sobrepasan el límite establecido de 38.2º");

		} else {

			System.out.println("");
			System.out.println("-----------------------AVISOS-----------------------");
			System.out.println("");
			System.out.println(
					"Se han registrado las siguientes temperaturas que sobrepasan el límite establecido de 38.2º: ");
			System.out.println("");
			for (int i = 0; i < avisos.size(); i++) {
				System.out.println(avisos.get(i));
			}
		}

		System.out.println("");
		System.out.println("-----------------------CONCLUSIONES-----------------------");
		System.out.println("");
		for (int i = 0; i < conclusion.size(); i++) {
			System.out.println(conclusion.get(i));
		}

		// ordenar datos
		List<Integer> keys = new ArrayList<Integer>(HashMap_medias_ano.keySet());
		Collections.sort(keys);

		List<Integer> keys_mes = new ArrayList<Integer>(HashMap_medias_mes.keySet());
		Collections.sort(keys_mes);

		List<Integer> keys_semana = new ArrayList<Integer>(HashMap_medias_semana.keySet());
		Collections.sort(keys_semana);

		List<Integer> keys_hora = new ArrayList<Integer>(HashMap_medias_hora.keySet());
		Collections.sort(keys_hora);

		System.out.println(" ");
		System.out.println(" ");
		System.out.println("**********************************************************************************");
		System.out.println("********************************* MEDIAS POR AÑO *********************************");
		System.out.println("**********************************************************************************");
		System.out.println(" ");
		for (int i = 0; i < keys.size(); i++) {
			int valor_key = (int) keys.get(i);
			for (Map.Entry<Integer, DatosMedias> valor_ano : HashMap_medias_ano.entrySet()) {
				if (valor_key == valor_ano.getKey()) {
					System.out.println(" ");
					System.out.println("------ AÑO " + valor_key + " ------- ");
					System.out.println(" Número de medidas en el año : " + valor_ano.getValue().getContador());
					System.out.println(" Medias: " + valor_ano.getValue().getMedia_medidas());

				}
			}
		}
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("**********************************************************************************");
		System.out.println("********************************* MEDIAS POR MES *********************************");
		System.out.println("**********************************************************************************");
		int valor_ano_anterior = 0;
		int valor_mes_anterior = 0;
		for (int i = 0; i < keys_mes.size(); i++) {
			int valor_key = (int) keys_mes.get(i);
			int valor_key_ano = valor_key / 100;
			int valor_key_mes = valor_key % 100;
			for (Map.Entry<Integer, DatosMedias> valor_mes : HashMap_medias_mes.entrySet()) {
				if (valor_key == valor_mes.getKey()) {
					if (valor_ano_anterior != valor_key_ano) {

						System.out.println(" ");
						System.out.println("------------------------------------ AÑO " + valor_key_ano
								+ " ------------------------------------");
					}
					valor_ano_anterior = valor_mes.getKey() / 100;
					if (valor_mes_anterior != valor_key_mes) {
						System.out.println(" ");
						System.out.println("------------ MES " + valor_key_mes + " ------------- ");
					}
					valor_mes_anterior = valor_mes.getKey() % 100;

					System.out.println(" Medias: " + valor_mes.getValue().getMedia_medidas());

				}
			}
		}

		System.out.println(" ");
		System.out.println(" ");
		System.out.println("*************************************************************************************");
		System.out.println("********************************* MEDIAS POR SEMANA *********************************");
		System.out.println("*************************************************************************************");

		valor_ano_anterior = 0;
		valor_mes_anterior = 0;

		for (int i = 0; i < keys_semana.size(); i++) {
			int valor_key = (int) keys_semana.get(i);
			int valor_key_ano = valor_key / 10000;
			int valor_key_mes = (valor_key / 100) % 100;

			for (Map.Entry<Integer, DatosMedias> valor_semana : HashMap_medias_semana.entrySet()) {
				if (valor_key == valor_semana.getKey()) {
					if (valor_ano_anterior != valor_key_ano) {

						System.out.println(" ");
						System.out.println("------------------------------------ AÑO " + valor_key_ano
								+ " ------------------------------------");
					}
					valor_ano_anterior = valor_semana.getKey() / 10000;
					if (valor_mes_anterior != valor_key_mes) {
						System.out.println(" ");
						System.out.println("------------ MES " + valor_key_mes + " ------------- ");
						System.out.println(" Medias: ");
					}
					valor_mes_anterior = (valor_semana.getKey() / 100) % 100;

					System.out.println(" Semana " + valor_semana.getKey() % 100 + ": "
							+ valor_semana.getValue().getMedia_medidas());

				}
			}
		}

		System.out.println(" ");
		System.out.println(" ");
		System.out.println("***********************************************************************************");
		System.out.println("********************************* MEDIAS POR HORA *********************************");
		System.out.println("***********************************************************************************");

		valor_ano_anterior = 0;
		valor_mes_anterior = 0;

		for (int i = 0; i < keys_hora.size(); i++) {
			int valor_key = (int) keys_hora.get(i);
			int valor_key_ano = valor_key / 10000;
			int valor_key_mes = (valor_key / 100) % 100;

			for (Map.Entry<Integer, DatosMedias> valor_hora : HashMap_medias_hora.entrySet()) {
				if (valor_key == valor_hora.getKey()) {
					if (valor_ano_anterior != valor_key_ano) {
						System.out.println(" ");
						System.out.println("------------------------------------ AÑO " + valor_key_ano
								+ " ------------------------------------");
					}
					valor_ano_anterior = valor_hora.getKey() / 10000;
					if (valor_mes_anterior != valor_key_mes) {
						System.out.println(" ");
						System.out.println("------------ MES " + valor_key_mes + " ------------- ");
						System.out.println(" Medias: ");
					}
					valor_mes_anterior = (valor_hora.getKey() / 100) % 100;

					System.out.println(
							" Hora " + valor_hora.getKey() % 100 + ": " + valor_hora.getValue().getMedia_medidas());
				
				}
			}
		}
		System.out.println("-----DATOS-----");
		for(int i=0;i<datos_finales.size();i++) {
			System.out.println(datos_finales.get(i).getID() +" "+ datos_finales.get(i).getFecha() +" "+ datos_finales.get(i).getHora() +" "+ datos_finales.get(i).getMedidas());
		}

	}
	
	public static void main(String[] arg) throws IOException {

		LeerFichero();

		ProcesarDatos();

		GenerarFicheros();

		VisualizarResultados();

		// GUARDO TODOS LOS RESULTADOS EN LA CLASE RESULTADOS
		Resultados resultados = new Resultados();
		resultados.setResultado_avisos(avisos);
		resultados.setResultado_conclusion(conclusion);
		resultados.setResultado_medias_ano(HashMap_medias_ano);
		resultados.setResultado_medias_hora(HashMap_medias_hora);
		resultados.setResultado_medias_mes(HashMap_medias_mes);
		resultados.setResultado_medias_semana(HashMap_medias_semana);

	}
}
