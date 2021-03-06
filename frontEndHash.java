
import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class frontEndHash <KeyType, ValueType> {
  public static HashTableMap frontHash = new HashTableMap();
  private static File file = new File("data1.txt");
  public static DataLoader dataLoad = new DataLoader(file, frontHash); 
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Type L to lookup a student's record.");
    System.out.println("Type A to add a new student into the records.");
    System.out.println("Type R to remove a student from the records.");
    String s = "L";
    while(!s.equals("Q")) {
      try {
        s = sc.next();
        if(s.equalsIgnoreCase("L")) {
          lookUp();
        }
        else if(s.equalsIgnoreCase("A")) {
          addStudent();
        }
        else {
          Scanner removeScanner = new Scanner(System.in);
          System.out.println("Enter name of student to remove");
          String removeStudent = removeScanner.nextLine();
         if(frontHash.remove(removeStudent) == null) {
           throw new NoSuchElementException();
         }
        }
      }
      catch(IllegalArgumentException e1) {
        System.out.println("Make sure to type in what is expected.");
      }
      catch(NoSuchElementException e){
        System.out.println("No such student was found.");
      }
      finally{
        System.out.println("Type Q to quit. L to look up another student. A to add another student. R to remove another student.");
      }
      }


  }
  public static void lookUp() {
    Scanner lookupScanner1 = new Scanner(System.in);
    Scanner lookupScanner2 = new Scanner(System.in);
    System.out.println("Enter name of student");
    String lookupName = lookupScanner1.nextLine();
    System.out.println("Enter Y for graduation year.");
    System.out.println("Enter M for major.");
    System.out.println("Enter I for ID");
    String lookupInfo = lookupScanner2.next();
   Student returnStudent = (Student)frontHash.get(lookupName);        
   if(lookupInfo.equalsIgnoreCase("Y")) {
     System.out.println(returnStudent.getGradYear());
   }
   if(lookupInfo.equalsIgnoreCase("M")) {
     System.out.println(returnStudent.getMajor());
   }
   if(lookupInfo.equalsIgnoreCase("I")) {
     System.out.println(returnStudent.getID());
   }
  }
  public static void addStudent() {
    Scanner addScanner1 = new Scanner(System.in);
    Scanner addScanner2 = new Scanner(System.in);
    Scanner addScanner3 = new Scanner(System.in);
    Scanner addScanner4 = new Scanner(System.in);
    System.out.println("Enter name of student");
    String addName = addScanner1.nextLine();
    System.out.println("Enter student ID.");
    System.out.println("Example: 1234567891");
    Long addID = addScanner2.nextLong();
    System.out.println("Enter student's major");
    String addMajor = addScanner3.nextLine();
    System.out.println("Enter student's graduation year");
    Integer addGrad = addScanner4.nextInt();
    Student newStudent = new Student(addID, addMajor, addGrad);
    frontHash.put(addName, newStudent);
  }

}
