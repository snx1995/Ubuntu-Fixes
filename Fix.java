import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Fix{
	public static void main(String[] args){
		FixQuestion[] fq;   
		System.out.println("########################################");
		if(args.length>0){
			switch(args[0]){
				case "-f":
					System.out.println(findQuestion(Integer.valueOf(args[1])));
					System.out.println("========================================");
					break;
				case "-k":
					fq = findQuestionByKey(args[1]);
					for(int i=0;i<fq.length;i++){
						System.out.println(fq[i]);
						System.out.println("========================================");
					}
					break;
				case "-n":
					outputQuestionsInRange(Integer.valueOf(args[1]),Integer.valueOf(args[2]));
					break;
				case "-t":
					System.out.println(getChangeTime());
					break;
				case "-a":
					outputAllQuestions();
					break;
				case "-help":
					System.out.println();
					System.out.println("-f <questionIndex> : output index question;");
					System.out.println("-k <key> : find all fixes with this keyword;");
					System.out.println("-t : output last updata time;");
					System.out.println("-a : output all fixes;");
					System.out.println("-help : this help;");
					System.out.println();
					break;
				default:
					System.out.println("No such order! -help for more information.");
					break;

			}
		}
		else System.out.println("Please input options, -help for more information.");
	}
	public static FixQuestion findQuestion(int key){
		FixQuestion fq = null;
		try{
			FileReader fr = new FileReader("ubuntuFix.txt");
			BufferedReader br = new BufferedReader(fr);
			for(int i=0;i<key;i++){
				fq = new FixQuestion();
				fq.readFromFile(br);
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return fq;
	}

	public static FixQuestion[] findQuestionByKey(String key){
		FixQuestion[] fq = {};
		try{
			FileReader fr = new FileReader("ubuntuFix.txt");
			BufferedReader br = new BufferedReader(fr);
			FixQuestion tmp = null;
			int n = Integer.valueOf(br.readLine().substring(0,3));
			int j=0;
			for(int i=0;i<n;i++){
				tmp = new FixQuestion();
				tmp.readFromFile(br);
				if(tmp.matchKey(key)){
					fq=Arrays.copyOf(fq,fq.length+1);
					fq[j++]=tmp;
				}
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		return fq;
	}
	public static String getChangeTime(){
		String updateTime = null;
		try{
			FileReader fr = new FileReader("ubuntuFix.txt");
			BufferedReader br = new BufferedReader(fr);
			updateTime = br.readLine();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		return updateTime.substring(4,updateTime.length());
	}
	public static void outputQuestionsInRange(int m, int n){
		try{
			FileReader fr = new FileReader("ubuntuFix.txt");
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			FixQuestion fq = new FixQuestion();
			if(Integer.valueOf(line.substring(0,3))<m || Integer.valueOf(line.substring(0,3))<n)
				System.out.println("Index out of range!!!" + " Max : " + Integer.valueOf(line.substring(0,3)));
			else{
				for(int i=1;i<=n;i++){
					fq.readFromFile(br);
					if(i>=m){
						System.out.println(fq);
						System.out.println("========================================");
					}
				}
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static void outputAllQuestions(){
		String line;
		try{
		FileReader fr = new FileReader("ubuntuFix.txt");
		BufferedReader br = new BufferedReader(fr);
		line = br.readLine();
		while((line = br.readLine())!=null){
			if(line.equals("END")) System.out.println("========================================");
			else System.out.println(line);
		}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}

class FixQuestion{
	private String question;
	private String answer;

	public FixQuestion(String Q, String A){
		question = Q;
		answer = A;
	}

	public FixQuestion(){
		question = "";
		answer = "";
	}

	public String toString(){
		return question + '\n' + answer;
	}

	public FixQuestion readFromFile(BufferedReader br){
		try{
			String line = br.readLine();
			String ans="";
			while(!line.equals("END")){
				if(line.charAt(0)=='Q') question=line;
				else if(line.charAt(0)=='A' || line.charAt(0)=='#') ans = ans + line + '\n';
				line = br.readLine();
			}
			answer = ans.substring(0,ans.length()-1);
		}catch(IOException e){
			e.printStackTrace();
		}
		return this;
	}

	public boolean matchKey(String key){
		if(question.indexOf(key)!=-1) return true;
		else return false;
	}
}