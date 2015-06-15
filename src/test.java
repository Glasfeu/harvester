
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JFrame;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;	
import org.eclipse.jgit.lib.Ref;

import plm.core.lang.ProgrammingLanguage;
import plm.core.model.Game;
import plm.core.model.lesson.Exercise;
import plm.core.model.lesson.Exercise.WorldKind;
import plm.universe.World;




public class test {


	public static void main(String[] args) throws InvalidRemoteException, TransportException, GitAPIException, IOException {

		String path = "/home/Herve/coron-0.8/sample/test1.rcf";
		
		ArrayList<String> exoValid = new ArrayList<String>();
		exoValid.add("welcome.lessons.welcome.instructions.Instructions");
		exoValid.add("welcome.lessons.welcome.instructions.InstructionsDrawG");
		exoValid.add("welcome.lessons.welcome.bdr.BDR");
		exoValid.add("welcome.lessons.welcome.variables.RunHalf");
		exoValid.add("welcome.lessons.welcome.variables.RunFour");
		exoValid.add("welcome.lessons.welcome.variables.Variables");
		exoValid.add("welcome.lessons.welcome.loopwhile.WhileMoria");
		exoValid.add("welcome.lessons.welcome.loopwhile.BaggleSeeker");
		exoValid.add("welcome.lessons.welcome.loopwhile.LoopWhile");
		exoValid.add("welcome.lessons.welcome.conditions.Conditions");
		exoValid.add("welcome.lessons.welcome.loopdowhile.LoopDoWhile");
		exoValid.add("welcome.lessons.welcome.loopfor.LoopCourseForest");
		exoValid.add("welcome.lessons.welcome.loopfor.LoopCourse");
		exoValid.add("welcome.lessons.welcome.loopfor.LoopStairs");
		exoValid.add("welcome.lessons.welcome.loopfor.LoopFor");
		
//		LocalRepository.fetch();
		RepoIterator ite = new RepoIterator();
		//ite.setExoName(exoValid);
		//ite.setExoNameExact(exoValid);
		//ite.setExoName(exoValid);
		//ite.addValidBranch("refs/remotes/origin/PLM50abddcacea0b9000787dbdf8300bb5460a6e446");
		ite.setCollectCode(true);
		ite.setCollectError(true);
		
		ArrayList<Student> students = new ArrayList<Student>();
		while(ite.hasNext()){
			Student temp = ite.next();
			if(temp != null)
				students.add(temp);
		}
		
		try {
			getCode(students);
		} catch (InterruptedException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		
		
		
		
		/*
		Formatter format = new Formatter(path, ite);
		format.setEQ(0.5);
		format.setExoTriedAndFailed();
		//format.setExoTried();
		format.parse();
		format.writeFile();
		*/
/*		
		int i = 0;
		for(Student s : students)
			if(s.getTriedExoName().size()>0)
				i++;
			
		System.out.println(i + "student have tried at least 1 exo");	
			
	*/	
			
		
		
		
/*
		ArrayList<String> listExo = new ArrayList<String>();
		ArrayList<HashMap<String, Integer>> listHash = new ArrayList<>();
		for(Student s : students){
			HashMap<String, Integer> temp = s.getHashMapExoNameResult();
			if(temp != null){
				listHash.add(temp);
			for(String name : temp.keySet())
				if(!listExo.contains(name))
					listExo.add(name);
			}
		}
		
		
		File file = new File("/home/Herve/coron-0.8/sample/test1.rcf");
		file.delete();
		file.createNewFile();
		
		ecrire("[Relational Context]" +
		"\nDefault Name" +
		"\n[Binary Relation]" +
		"\nName_of_dataset");
		
		ecrire("\no1");
		for(int i = 1 ; i < listHash.size(); i++)
			ecrire("| o" + (i +1) );

		ecrire("\n");
		
		Boolean first = true;
		for(String name : listExo){
			if(name != null && name != "null" && ! name.isEmpty())
				if(first == true){
					ecrire(name);//.substring(24)
					first = false;
				}else 
					ecrire(" | " + name);
		}
		
		int i = 0;
		System.out.println("\nParse " + listHash.size() + " students");
		for(HashMap<String, Integer> hashNameResult : listHash){
			i++;
			if (i % 150 == 0)
				System.out.println(". "+i+"/"+listHash.size());
			else
				System.out.print(".");
				
			ecrire("\n");
			for(String name : listExo)
				if(name != null && name != "null" && ! name.isEmpty()){
				if(hashNameResult.containsKey(name) && hashNameResult.get(name) == 1)
					ecrire(" 1");
				else
					ecrire(" 0");			
				}
			}
		ecrire("\n[END Relational Context]");
		
		System.out.println("\n Done");
		
	*/	
//			s.computeSessions();
	/*		if( s.getBranchName().equals("refs/remotes/origin/PLMe882a0102d1c6af90b0532cc658ff98771f95050"))
				s.printSession();
		}
		*/	/*for( Event e : s.getEvents()){
			//	System.out.println(e.getExoName());
				System.out.println(e.getCommitType());
				System.out.println(e.getCommitTime().get(Calendar.DATE) + "/" +e.getCommitTime().get(Calendar.MONTH) + "/" + e.getCommitTime().get(Calendar.YEAR) );
				
			}*/
	}

		

	public static void execCode(Event commit, String branchName, String code, ProgrammingLanguage lang, String lessonID, String exoID) throws FileNotFoundException, InterruptedException {
		MockLogHandler log = new MockLogHandler();
		Game g = new Game(log, new JFrame().getLocale());
		g.getProgressSpyListeners().clear();
		g.removeSessionKit();
		g.setBatchExecution();
		
		g.setProgramingLanguage(lang);
		g.switchLesson(lessonID, true);
		g.getCurrentLesson().setCurrentExercise(exoID);
		g.setLocale(new Locale("en"));
		
		Exercise exo = (Exercise) g.getCurrentLesson().getCurrentExercise();
		exo.getSourceFile(lang, 0).setBody(code);
		g.startExerciseExecution();
		
		Vector<World> currentWorlds = exo.getWorlds(WorldKind.CURRENT);
		BufferedWriter bw = null;
		for(int i=0; i<currentWorlds.size(); i++) {
			World currentWorld = currentWorlds.get(i);
			World answerWorld = exo.getAnswerOfWorld(i);
			if (!currentWorld.winning(answerWorld)) {
				String error = answerWorld.diffTo(currentWorld);
				System.out.println(error);
				try {
					File f = new File("logsDir/"+exoID);
					f.mkdirs();
					File dest = new File("logsDir/"+exoID+"/"+branchName+"_"+commit.getIdCommit() + ".log");
					dest.createNewFile();
					bw = new BufferedWriter(new FileWriter("logsDir/"+exoID+"/"+branchName+"_"+commit.getIdCommit() + ".log", true));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					if(error!=null) {
						bw.write(error);
					} else {
						bw.write("null");
					}
					bw.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*if(diffs.get(exoID) == null) {
					diffs.put(exoID, new HashMap<String,Vector<String>>());
				}
				if(diffs.get(exoID).get(error) == null) {
					diffs.get(exoID).put(error, new Vector<String>());
					diffs.get(exoID).get(error).add(branchName);
				}
				else {
					if(!diffs.get(exoID).get(error).contains(branchName)) {
						diffs.get(exoID).get(error).add(branchName);
					}
				}*/
			}
		}
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean rescanCache(String exoName, String branchName, String commitName) {
		File dir = new File("logsDir/"+exoName);
		File[] files = dir.listFiles();
		if(files==null)
			return false;
		for(int i = 0;i<files.length;i++) {
			//System.out.println(files[i].getName());
			if(files[i].getName().equals(branchName+"_"+commitName+".log")) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static void getCode(Collection<Student> students) throws IOException, GitAPIException, InterruptedException {
		PrintStream ps = new PrintStream("codes.txt");
		int ok = 0;
		
		for(Student student : students) {
			//Thread.sleep(2000);
			System.out.println("Entre dans la branche "+ok+" - "+student.getBranchName());
			if(ok<=8) {
			//	ps.println(ok + " --> " + student.getBranchName());
				ok++;
			//	ArrayList<Event> commits = GitUtils.computeCommits(student.name.substring(3));
			//	Collections.sort(commits);
				ArrayList<Event> commits = (ArrayList) student.getEvents();
				int nbCo = 0;
				System.out.println("Nombre de commits dans la branche : "+commits.size());
				int nbThreadMax = 8;
				int nbThread = 1;
				for(int i = nbThreadMax;i>0;i--){
					if(commits.size()%i==0){
						nbThread = i;
						break;
					}
				}
				for(int i = 0;i<commits.size();i+=nbThread) {
					/*int l = 0;
					if((commits.size()-i)/nbThread==0) {
						l=nbThread+i-commits.size();
					}*/
					CommitThread[] cts = new CommitThread[nbThread];
					for(int j = 0;j<nbThread;j++) {
						cts[j] = new CommitThread(commits.get(i+j), ps, nbCo, student);
						//System.out.println("ok");
						if(rescanCache(commits.get(i+j).getExoName(), student.getBranchName(), "hey" )) {//commits.get(i+j).rev.getName()
							//System.out.println("Déjà là !");
							continue;
						}
						try {
						cts[j].run();
						}
						catch(IndexOutOfBoundsException e) {
							System.out.println("plop "+j+" - "+cts.length);
							System.out.println("-->" + "...");// commits.get(i+j).rev.getName()
						}
					}
					for(int j = 0;j<nbThread;j++) {
						synchronized(cts[j]) {
							if(cts[j].isAlive())	
								cts[j].wait(30000);
							if(cts[j].isAlive()) {
								cts[j].stop();
							}
						}
					}
				}
			} else {
				break;
			}
		}
		//getStats(ps);
		ps.close();
	}
	
	
	
	
}
