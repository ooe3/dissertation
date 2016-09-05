package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import gui.*;
import main.*;
import other.Course;
import other.CourseDegree;
import other.CourseResult;
import other.Degree;
import other.Student;
import other.Users;
//The listener class for ViewResult class
public class ViewListener implements ActionListener{
	ViewResult vr;
	Users us;
	Queries q = Queries.getQueries();//Queries object created to get access to the methods
	MainQueries m = MainQueries.getMain();//MainQueries object created to access methods in the MainQueries class
	AddQueries aq = AddQueries.getMain();
	List<Degree> dg;//Create a list containing degree objects
	List<Course> cdg;
	List<Student> stt;
	List<CourseResult> getCourses;
	List<CourseDegree> getInfo;
	public ViewListener(ViewResult v){
		vr = v;//initialized to listen to buttons
		us = q.getUser();//getUser method called to get current user
		dg = m.getList();//initializes list by calling getList method to get all Degrees under the Admin's school
		cdg = m.getCourseList();
		stt = q.getStudents();
		getCourses = aq.getInfo();
		getInfo = m.getCourseDegreeList();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("LogOut")){//listener for the logout menu item
			/*
			 * New StartGUI frame created and shown
			 * ViewResult frame disposed
			 */
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			StartGUI sg = new StartGUI();
			sg.setVisible(true);
			vr.dispose();
		}else if(e.getActionCommand().equals("Refresh")){//listener for the refresh menu item
			/*
			 * New ViewResult frame created and shown
			 * current viewResult frame disposed
			 */
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			ViewResult vrt = new ViewResult();
			vrt.setVisible(true);
			vr.dispose();
		}else if(e.getActionCommand().equals("Home Menu")){//listener for the home menu menu item
			/*
			 * New Main frame created and shown
			 * ViewResult frame disposed
			 */
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			Main mn = new Main();
			mn.setVisible(true);
			vr.dispose();
		}else if(e.getActionCommand().equals("View")){//listener for the view student results menu item
			vr.setVisible(true);

		}else if(e.getActionCommand().equals("Add")){//listener for the add result menu item
			stt.removeAll(stt);
			getCourses.removeAll(getCourses);
			AdminAdd ad = new AdminAdd();
			ad.setVisible(true);
			vr.dispose();
		}else if(e.getActionCommand().equals("Add Student")){
			dg.removeAll(dg);
			getInfo.removeAll(getInfo);
			CreateStudent cs = new CreateStudent();
			cs.setVisible(true);
			vr.dispose();
		}else{
			stt.removeAll(stt);
			ViewStudents vs = new ViewStudents();
			vs.setVisible(true);
			vr.dispose();
		}

	}

}
