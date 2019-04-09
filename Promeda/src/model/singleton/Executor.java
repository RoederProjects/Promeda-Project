package model.singleton;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;

public class Executor {
    
	public Executor() throws IOException, InterruptedException {
		
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
        System.out.println("Result of Execution"+(resultStatust==0?"\tFailure":"\tSuccess"));
    }
    
    public static void exec(File imageFile, String cmd) {
    	// Initialize a HashMap for variable substitution in command string 
    	Map<String, File> map = new HashMap<String, File>();
    	map.put("file", imageFile);
    	
    	// Initialize a valid CommandLine object by parsing the command string
    	CommandLine cmdLine = CommandLine.parse(cmd, map);
    	
    	DefaultExecutor executor = new DefaultExecutor();
    	executor.setExitValue(1);
    	
    	// Initialize a Watchdog for Execution (if process run away, execution time out after 60 seconds)
    	ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
    	executor.setWatchdog(watchdog);
    	
    	// Run execution
    	try  {
			executor.execute(cmdLine);
		} catch (ExecuteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void exec(File imageFile) {
    	Map<String, File> map = new HashMap<String, File>();
    	map.put("file", imageFile);
    	
    	/*CommandLine cmdLine = new CommandLine("codec\\jpegoptim\\win32\\jpegoptim.exe");
    	cmdLine.addArgument("--strip-all");
    	cmdLine.addArgument("--all-progressive");
    	cmdLine.addArgument("--max=88");
    	cmdLine.addArgument("${file}");
    	cmdLine.setSubstitutionMap(map);*/
    	
    	String cmd = "codec\\jpegtran\\win\\x64\\jpegtran.exe -copy none -optimize -progressive -outfile ${file} ${file}";
    	CommandLine cmdLine = CommandLine.parse(cmd, map);
    	
    	/*CommandLine cmdLine = new CommandLine("codec\\jpegtran\\win\\x64\\jpegtran.exe");
    	cmdLine.addArgument("-copy none");
    	cmdLine.addArgument("-optimize");
    	cmdLine.addArgument("-progressive");
    	cmdLine.addArgument("-outfile " + "\"${file}\"");
    	cmdLine.addArgument("${file}");
    	cmdLine.setSubstitutionMap(map);*/
    	
    	System.out.println(cmdLine.getExecutable() + " ### " + cmdLine.toString());
    	DefaultExecutor executor = new DefaultExecutor();
    	executor.setExitValue(1);
    	ExecuteWatchdog watchdog = new ExecuteWatchdog(30000);
    	executor.setWatchdog(watchdog);
    	try {
			int exitValue = executor.execute(cmdLine);
		} catch (ExecuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//\Promeda>cd "codec\jpegoptim\win32" | jpegoptim.exe --strip-all --all-progressive --max=88 "D:\Benutzer\Projekte\workspace@Eclipse-Java-2018-09\Promeda-Project\Promeda\media-archive\dist\2200px\89635G.jpg"
    }
    
    static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

}