package in.lali;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con=null;
	Statement st=null;
	public LoginServlet() {
		try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	con=DriverManager.getConnection("jdbc:mysql://localhost:3306/myseries?useSSL=false","root","root");
            st =  con.createStatement();	
		}
        catch(ClassNotFoundException e)
        {
        	System.out.println("type 4 driver not found");
        }
        catch(SQLException e)
        {
        	System.out.println(e.getMessage());
        }
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String username= request.getParameter("uname");
			String pwd=request.getParameter("pwd");
			ResultSet rs= st.executeQuery("select * from users where uname='"+username+"' and password='"+pwd+"' " );
			RequestDispatcher rd=null;
			if(rs.next())
			{
				rd=request.getRequestDispatcher("Home.html");
				rd.forward(request, response);
			}
			else {
				request.setAttribute("msg","Invalid Login please try again..!");
				   rd =request.getRequestDispatcher("/Login.jsp");
				   rd.forward(request, response);
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}

}