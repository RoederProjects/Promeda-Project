package model.singleton;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Run {
    
	public Run() throws IOException, InterruptedException {
		
		//String destFolder="//SVR-APP-11/EC/Promeda.local-working-dir";
		String destFolder="D:\\Benutzer\\Projekte\\workspace@Eclipse-Java-2018-09\\Promeda-Project\\Promeda";
        /*
        *  Location where the Nodejs Project is Present
        */
        System.out.println(destFolder);

        String cmdPrompt="cmd";
        String path="/c";
        String npm = isWindows() ? "npm.cmd" : "npm";
        String npmUpdate= npm + " run jpegtran";

        File jsFile=new File(destFolder);
        List<String> updateCommand=new ArrayList<String>();
        updateCommand.add(cmdPrompt);
        updateCommand.add(path);
        //updateCommand.add("gulp");
        updateCommand.add(npmUpdate);
        //updateCommand.add("jpegtran");
        runExecution(updateCommand,jsFile);

    }
	
	public static void compressImage(File imageFile, File workingDir) throws IOException, InterruptedException {
		      
		        String cmdPrompt="cmd";
		        String path="/c";
		        //String npm = isWindows() ? "npm.cmd" : "npm";
		        String npmUpdate= "jpegoptim --strip-all --all-progressive --max=88 " +imageFile.getPath();
		     
		        List<String> updateCommand=new ArrayList<String>();
		        updateCommand.add(cmdPrompt);
		        updateCommand.add(path);
		        updateCommand.add(npmUpdate);
		        runExecution(updateCommand,workingDir);
	}
	
	public static void compressImage(File imageFile, File workingDir, String compressionCommand) throws IOException, InterruptedException {
	      
        String cmdPrompt="cmd";
        String path="/c";
        //String npm = isWindows() ? "npm.cmd" : "npm";
        //String compressionCommandFull = compressionCommand + " " +imageFile.getPath();
     
        List<String> commandList=new ArrayList<String>();
        commandList.add(cmdPrompt);
        commandList.add(path);
        commandList.add(compressionCommand + " " +imageFile.getPath());
        runExecution(commandList,workingDir);
}
		
    public static void runExecution(List<String> command, File workingDir) throws IOException, InterruptedException {

        ProcessBuilder executeProcess=new ProcessBuilder(command);
        executeProcess.directory(workingDir);
        Process resultExecution=executeProcess.start();
        BufferedReader br=new BufferedReader(new InputStreamReader(resultExecution.getInputStream()));
        StringBuffer sb=new StringBuffer();

        String line;
        while((line=br.readLine())!=null) {
            sb.append(line+System.getProperty("line.separator"));
            System.out.println(sb.toString());
        }
        br.close();
        int resultStatust=resultExecution.waitFor();
        System.out.println("Result of Execution"+(resultStatust==0?"\tSuccess":"\tFailure"));
    }
    
    static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

}