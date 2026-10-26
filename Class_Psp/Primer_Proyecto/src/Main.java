//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class Main {
    public static void main(String[] args){
        try {
            //Prepara un nuevo proceso
            //Process process = new ProcessBuilder("ls", "-l").start();
            ProcessBuilder pBuilder = new ProcessBuilder("ls", "-l");
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