import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.HashMap;


public class Formatter {
	
	private ArrayList<String> studentNames;
	private ArrayList<String> characteristics;
	private HashMap<String, HashMap<String, Integer>> results; // hashmap student -> [charac -> result(0/1)]
	private String path;
	private RepoIterator parser;
	private ArrayList<String> metrics;

	
	
	public Formatter(String path){
		this.path = path;
		parser = new RepoIterator();
		studentNames = new ArrayList<String>();
		characteristics = new ArrayList<String>();
		results = new HashMap<String, HashMap<String, Integer>>();
		metrics = new ArrayList<String>();
	}
	
	public Formatter(String path, RepoIterator iterator){
		this.path = path;
		parser = iterator;
		studentNames = new ArrayList<String>();
		characteristics = new ArrayList<String>();
		results = new HashMap<String, HashMap<String, Integer>>();
		metrics = new ArrayList<String>();	
	}
	
	public void parse() throws IOException{
		Student s;
		while(parser.hasNext())
			if((s = parser.next()) != null){
				studentNames.add(s.getBranchName());
				results.put(s.getBranchName(), new HashMap<String, Integer>());				
				compute(s);				
			}				
	}

		
	private void compute(Student student){
		for(String m : metrics)
			switch(m){
			case EQ : 
				computeEQ(student);
				break;
			case exoTriedAndFailed :
				computeExoTriedAndFailed(student);
				break;
			case exoTried :
				computeExoTried(student);
				break;
			}
		
		
		
		
	}

	//----------------------------------- EQ
	public static final String EQ = "EQ";
	private double tresholdEQ;
	public void setEQ(double tresh){
		metrics.add(EQ);
		parser.setCollectError(true);
		parser.addCommitType(Event.Executed);
		tresholdEQ = tresh;
	}
	private void computeEQ(Student student) {
		if(!results.containsKey(student.getBranchName()))
			results.put(student.getBranchName(), new HashMap<String, Integer>());
	
		for(String exo : student.getTriedExoName()){
			if(!characteristics.contains(exo + " EQ"))
				characteristics.add(exo + " EQ");
			if(student.getEQ(exo) > tresholdEQ)
				results.get(student.getBranchName()).put(exo + " EQ", 1);
			else
				results.get(student.getBranchName()).put(exo + " EQ", 0);
		}
	}

	//-----------------------------------------------------------------Exo failed
	public static final String exoTriedAndFailed = "ExoTriedAndFailed";
	public void setExoTriedAndFailed(){
		metrics.add(exoTriedAndFailed);
		parser.addCommitType(Event.Executed);
	}
	private void computeExoTriedAndFailed(Student student) {
		if(!results.containsKey(student.getBranchName()))
				results.put(student.getBranchName(), new HashMap<String, Integer>());
		
		for(Event e : student.getEvents()){
			if(!characteristics.contains(e.getExoName() + " Failed"))
				characteristics.add(e.getExoName() + " Failed");
			if(!results.get(student.getBranchName()).containsKey(e.getExoName()+  " Failed"))
				results.get(student.getBranchName()).put(e.getExoName() + " Failed", 1);
			if(e.getResultCompil() != null && e.getResultCompil().equals(Event.Success))
				results.get(student.getBranchName()).replace(e.getExoName() + " Failed", 0);
		}
	}
	
	
	//------------------------------------------- Exo tried
	public static final String exoTried = "ExoTried";
	public void setExoTried(){
		metrics.add(exoTried);
		parser.addCommitType(Event.Executed);
	}
	private void computeExoTried(Student student){
		if(!results.containsKey(student.getBranchName()))
			results.put(student.getBranchName(), new HashMap<String, Integer>());
		for(String exo : student.getTriedExoName()){
			if(!characteristics.contains(exo + " Tried"))
				characteristics.add(exo + " Tried");
			results.get(student.getBranchName()).put(exo + " Tried", 1);
		}
	}
	
	
	
	
	
	
	
	public void writeFile() throws IOException{
		File file = new File(path);
		file.delete();
		file.createNewFile();

		FileWriter fw = new FileWriter(path, true);
		BufferedWriter output = new BufferedWriter(fw);
		
		output.write("[Relational Context]" +
		"\nDefault Name" +
		"\n[Binary Relation]" +
		"\nName_of_dataset");
		
		output.write("\n");
		boolean first = true;
		for(String name : studentNames){
			if(!first)
				output.write(" | ");
			output.write(name);
			first = false;
		}
		output.write("\n");
		
		first = true;
		for(String charac : characteristics){
			if(!first)
				output.write(" |");
			output.write(charac);
			first = false;
		}
		output.write("\n");

		for(String name : studentNames){
			for(String charac : characteristics){
				if(results.get(name).containsKey(charac)){
					if(results.get(name).get(charac) == 1)
						output.write("1 ");
					else
						output.write("0 ");
				}
				else{
					output.write("0 ");
				}
			}
			output.write("\n");
		}
			
		output.write("\n[END Relational Context]");

		output.flush();
		output.close();
		fw.close();

		
	}

	
	
	
}
