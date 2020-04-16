import java.io.IOException;

public class RunMatlab {
	public static void main(String args[]) throws IOException, InterruptedException{
		
		String opcion = "semanas";

		switch (opcion) {
		
	    case "meses":
	    	try {
				String [] cmd = {"C:\\Program Files\\MATLAB\\R2019b\\bin\\matlab.exe","-nodisplay","-nodesktop","-r","run('C:\\Users\\cande\\Documents\\MATLAB\\Breast\\MonthGraphs.m');"};
				Runtime.getRuntime().exec(cmd).waitFor();
			}
			catch (IOException ioe) {
				System.out.println(ioe);
			}
			break;
			
	    case "anos":
	    	try {
				String [] cmd = {"C:\\Program Files\\MATLAB\\R2019b\\bin\\matlab.exe","-nodisplay","-nodesktop","-r","run('C:\\Users\\cande\\Documents\\MATLAB\\Breast\\YearGraphs.m');"};
				Runtime.getRuntime().exec(cmd).waitFor();
			}
			catch (IOException ioe) {
				System.out.println(ioe);
			}
	        break;

	    case "semanas":
	    	try {
				String [] cmd = {"C:\\Program Files\\MATLAB\\R2019b\\bin\\matlab.exe","-nodisplay","-nodesktop","-r","run('C:\\Users\\cande\\Documents\\MATLAB\\Breast\\WeekGraphs.m');"};
				Runtime.getRuntime().exec(cmd).waitFor();
			}
			catch (IOException ioe) {
				System.out.println(ioe);
			}
	        break;
	        
	    default:
        System.out.println("Elija una opción correcta");
	        break;
	}
	}

}