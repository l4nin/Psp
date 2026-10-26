import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

//Cambiar el directorio de ejecución de nuestro proceso
public class Main {
    public static void main(String[] args) {
        //Capturar directorios del sistema
        String userHome = System.getProperty("user.home");
        String userDir = System.getProperty("user.dir");
        //Sistema operativo
        String os = System.getProperty("os.name");
        //Pantalla
        System.out.println("Directorio del usuario logado: "+userHome);
        System.out.println("Directorio de trabajo: "+userDir);
        System.out.println("SO: "+os);
        try{
            String command = "ls -l";
            ProcessBuilder pBuilder = new ProcessBuilder(command.split("\\s"));
            pBuilder.directory(new File(userHome));
            //Creo un nuevo proceso
            Process process = pBuilder.start();
            //Stream del proceso
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            reader.lines().forEach(System.out::println);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}