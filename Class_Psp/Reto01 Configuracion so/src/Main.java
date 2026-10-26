import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        //Sistema operativo
        String os = System.getProperty("os.name");
        String command = "";
        String directory = "";
        //Crear el comando dependiendo del SO
        if(os.toLowerCase().startsWith("linux")){
            command = "sh -c ls -l";
            directory = "/tmp";
        }else{
            command = "cmd /c dir";
            directory = "c:/temp";
        }
        try{
            ProcessBuilder pBuilder = new ProcessBuilder(command.split("\\s"));
            pBuilder.directory(new File(directory));
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