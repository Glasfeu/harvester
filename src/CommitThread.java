//package git.browse;

import java.io.IOException;
import java.io.PrintStream;



import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;

import plm.core.lang.ProgrammingLanguage;
import plm.core.model.Game;


public class CommitThread extends Thread {

	private Event commit;
	private PrintStream ps;
	private int nbCo;
	private Student student;

	public CommitThread(Event commit, PrintStream ps, int nbCo, Student student) {
		this.commit = commit;
		this.ps = ps;
		this.nbCo = nbCo;
		this.student = student;
	}

	@Override
	public void run() {
		synchronized(this) {
			//System.out.println("Thread start ("+commit.codeLink+")");
			if(commit.getCommitType().equals(Event.Failed)) { //commit.isValid()
				nbCo++;
				ps.println(nbCo + " --> " + commit.getIdCommit() + " : "); //commit.codeLink
				ps.println("------------------");

				System.out.println(nbCo);//+ " --> " + commit.codeLink + " : ");
				//System.out.println(GitUtils.getSource(commit));
				System.out.println("------------------");

				ProgrammingLanguage lang1 = Game.JAVA;
				switch(commit.getExoLang().toLowerCase()) {
				case "scala": 
					lang1 = Game.SCALA;
					break;
				case "python":
					lang1 = Game.PYTHON;
					break;
				}
				final ProgrammingLanguage lang = lang1;

				String lessonID = "lessons."+commit.getExoName().split(".lessons.")[0];

				String exoID = commit.getExoName();

				if(HarvesterPIDR.lessonsName.contains(lessonID)) {
					String code;
					try {
						code = commit.getCode();
						test.execCode(commit, student.getBranchName(), code, lang, lessonID, exoID);
						Thread.sleep(50);
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			//System.out.println("Thread stop ("+commit.codeLink+")");
			notify();
		}
	}
	
	public int getNbCo() {
		return nbCo;
	}

}
