package edu.pdx.cs410J.michdo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the Student class.  In addition to the JUnit annotations,
 * they also make use of the <a href="http://hamcrest.org/JavaHamcrest/">hamcrest</a>
 * matchers for more readable assertion statements.
 */
public class StudentTest
{

  @Test
  void studentNamedPatIsNamedPat() {
    String name = "Pat";
    var pat = new Student(name, new ArrayList<>(), 0.0, "Doesn't matter");
    assertThat(pat.getName(), equalTo(name));
  }

  @Test
  void allStudentsSaysThisClassIsTooMuchWork(){
    Student student = new Student("name", new ArrayList<>(),0.0,"no need");
    assertThat(student.says(),equalTo("This class is too much work"));
  }

  @Test
  @Disabled
  void daveStudentSaysWhatIsExpected() {
    ArrayList<String > classes = new ArrayList<>();
    classes.add("Algorithms");
    classes.add("Operating Systems");
    classes.add("Java");
    Student dave = new Student("Dave", classes, 3.64, "male");

    //act (when)
    String daveString = dave.toString();

    assertThat(daveString, equalTo("Dave has a GPA of 3.64"));
  }

  @Test
  void nullArgumentsThrowsNullPointerException(){
    assertThrows(NullPointerException.class, ()-> new Student(null, null, 3.64, null));
  }

  @Test
  void nullClassesArgumentsThrowsNullPointerException(){
    assertThrows(NullPointerException.class, ()-> new Student("Dave", null, 3.64, null));
  }

  @Test
  void nullGenderArgumentsThrowsNullPointerException(){
    assertThrows(NullPointerException.class, ()-> new Student("Dave", new ArrayList<>(), 3.64, null));
  }

  /*
  @Test
  void studentCanTakeZeroClasses() {
    Student dave = new Student("dave",new ArrayList<>(),2.1,"Female");
    String daveString = dave.toString();
    assertThat(daveString,containsString("is taking 0 classes"));
  }

  @Test
  void studentCanTakeZeroClasses() {
    Student dave = new Student("dave",new ArrayList<>(),2.1,"Female");
    String daveString = dave.toString();
    assertThat(daveString,containsString("is taking 0 classes"));
  }
   */

}
