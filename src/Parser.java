import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;


public class Parser {
	
	private String exoName, partialExoName; // collect event only with exoName or with exoName starting with partial exoName
	private ArrayList<String> commitType; //collect event only with a type in commitType
	private Calendar dateMin, dateMax; // collect event only after and/or before dateMin/DateMax
	private boolean collectCode = false, collectError = false; //get the code of the commit ?
	
	public Parser(){}
	
	/**
	 * @param branchs Branchs of the repository to browse
	 * @return Collection of students
	 * @throws IOException
	 * Option must be set before (type of commit to gather, gather code or not,...)
	 */
	public Collection<Student> parse() throws IOException{
		Collection<Ref> branchs = LocalRepository.getBranchs();
		
		Collection<Student> students = new ArrayList<>();
		
		System.out.println("Parse "+branchs.size()+" branchs. This may take a few seconds.");
		int i = 0;
		for(Ref branch : branchs){ // browse branch to create student
	//		System.out.println("\n__________________________________\nélève : " + branch.getName());
			i++;
			if (i % 150 == 0)
				System.out.println(". "+i+"/"+branchs.size());
			else
				System.out.print(".");
			
			Student student = new Student(branch.getName());
			
			Collection<RevCommit> commits = LocalRepository.getCommits(branch.getName());
			Boolean unhandledBeta = false ;
			for(RevCommit commit : commits){ //browse all commit of the student			
				Event temp = new Event(commit);
				if(testConstruct(temp))
					continue;
				
				temp.setGlobalInfo();
				if(temp.getUsesUnhandledBeta()){ // no need to parse this student any further
					unhandledBeta = true;
					break;
				}
					
				if(testGlobalInfo(temp))
					continue;				
				
				if(collectCode && temp.getCommitType().equals(Event.Executed))
					temp.setCode();
				if(collectError && temp.getCommitType().equals(Event.Executed) && temp.getResultCompil().equals(Event.CompilError)){
					temp.setError();
					if(temp.getError() == null)
						break;
				}
				student.addEvent(temp);			
			}
			if(!unhandledBeta)
				if(student.getEvents().size()>0)
					students.add(student);
		}
		
		return students;
	}
	
	
	private Boolean testConstruct(Event e){ // test if the commit is conform to the option after the construction of the event
		if(dateMin != null && dateMin.after(e.getCommitTime()))
			return true;
		if(dateMax != null && dateMax.before(e.getCommitTime()))
			return true;		
		
		return false;
	}
	
	private Boolean testGlobalInfo(Event e){// test if the commit is conform to the option after setting general data
		if(exoName != null && e.getExoName() != null && !exoName.equals(e.getExoName()))
			return true;
		if(partialExoName != null && e.getExoName() != null
				&&( e.getExoName().length()<=partialExoName.length() 
				|| !partialExoName.equals(e.getExoName().substring(0, partialExoName.length()))))
				return true;
		if( commitType != null && !commitType.contains(e.getCommitType()))
			return true;
		
			
		return false;
	}

	
	public String getExoName() {
		return exoName;
	}



	/**
	 * @param exoName
	 * Set to collect only event of exercise "exoName"
	 */
	public void setExoName(String exoName) {
		this.exoName = exoName;
	}


	public ArrayList<String> getCommitType() {
		return commitType;
	}

	/**
	 * @param commitType
	 * Set to collect only event with a type in commitType
	 */
	public void setCommitType(ArrayList<String> commitType) {
		this.commitType = commitType;
	}

	public Calendar getDateMin() {
		return dateMin;
	}

	/**
	 * @param dateMin
	 * Set to collect only event after dateMin 
	 */
	public void setDateMin(Calendar dateMin) {
		this.dateMin = dateMin;
	}

	public Calendar getDateMax() {
		return dateMax;
	}

	/**
	 * @param dateMax
	 * Set to collect only event before dateMax
	 */
	public void setDateMax(Calendar dateMax) {
		this.dateMax = dateMax;
	}

	public String getPartialExoName() {
		return partialExoName;
	}

	/**
	 * @param partialExoName
	 * Set to collect only event of exercise with a name starting by partialExoname
	 */
	public void setPartialExoName(String partialExoName) {
		this.partialExoName = partialExoName;
	}

	public boolean isCollectCode() {
		return collectCode;
	}

	
	/**
	 * @param collectCode
	 * Set to true to set the "code" attribute of event collected
	 */
	public void setCollectCode(boolean collectCode) {
		this.collectCode = collectCode;
	}

	public void setCollectError(boolean collectError) {
		this.collectError = collectError;
	}

	
	
	
}
