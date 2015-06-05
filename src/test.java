
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;	
import org.eclipse.jgit.lib.Ref;




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
		ite.addValidBranch("refs/remotes/origin/PLM50abddcacea0b9000787dbdf8300bb5460a6e446");
		
		
		
		
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

		
	
	
}
