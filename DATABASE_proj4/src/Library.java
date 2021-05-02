import java.awt.print.Book;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.Date;

public class Library {
	//기본적으로 존재해야하는 테이블들
	public final static String[]	TABLE_NAMES = new String[]{
		"Librarian",
		"Storage",
		"Event",
		"E_participate",
		"User",
		"Book",
		"Burrow",
		"BBU",
	};
	//연결
	private Connection con;
	private Statement stat;
	public Library() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	}

	public boolean start()
	{
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testa","root","kwon02988");
			stat = con.createStatement();
		
			ResultSet rs = stat.executeQuery("SHOW TABLES;");
			int rs_count = 0;
			while(rs.next()) rs_count++;
			
			//테이블이 원래 있어야하는것보다 적은 경우 테이블을 생성한다.
			if(rs_count != TABLE_NAMES.length)
			{
				
				System.out.println("~Create Tables~");

				Scanner sc= new Scanner(new File("table"));
				sc.useDelimiter(";");
				
				String CommandLine;
				while(sc.hasNext())
				{
					 CommandLine = sc.next();
					 stat.executeUpdate(CommandLine);
				}
				sc.close();
			}
			return true;
		} catch (SQLException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	//오늘 날짜를 받아오는 함수
	public String today()
	{
		Date currentTime = new Date();
		String mTime = new SimpleDateFormat("yyyyMMdd").format ( currentTime );
		
		return mTime;
	}
	
	//각 테이블에 추가하는 함수들이다.
	public boolean insertLib(Librarian lib)
	{
		try {
			stat.executeUpdate("INSERT INTO Librarian VALUES(NULL,'"+lib.FName + "','"+lib.LName+"',NULL,'"+lib.Phone+"','"+today()+"')");
				return true;
	
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean insertStor(Storage stor)
	{
		try {
			stat.executeUpdate("INSERT INTO Storage VALUES( '"+stor.Name+"', "+stor.Mgr_ID+")");
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public boolean insertEvent(Event eve)
	{
		try {
			stat.executeUpdate("INSERT INTO Event VALUES(NULL, '"+eve.Start_Date+"', '"+eve.End_Date+"','"+eve.Host+"')");
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean insertUser(User usr)
	{
		try {
			stat.executeUpdate("INSERT INTO User VALUES(NULL, '"+usr.FName+"', '"+usr.LName+"', '"+usr.Phone+"')");
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean insertE_P(E_Participate epar)
	{
		try {
			stat.executeUpdate("INSERT INTO E_Participate VALUES( "+epar.Event_ID+", "+epar.Participant_ID+")");
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public boolean insertBOOK(BOOK book)
	{
		try {
			stat.executeUpdate("INSERT INTO BOOK VALUES(NULL, '"+book.Name+"', '"+today()+"','"+book.Storage_Name+"')");
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public boolean insertBurrow(Burrow bur)
	{
		try {
			stat.executeUpdate("INSERT INTO Burrow VALUES(NULL, "+bur.Due_date+", "+bur.Return_date+","+bur.Mgr_ID+")");
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean insertBBU(BBU bbu)
	{
		try {
			stat.executeUpdate("INSERT INTO BBU VALUES( "+bbu.BUR_ID+", "+bbu.BOO_ID+","+bbu.USR_ID+")");
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	
	//각 테이블의 투플을 받아오는 함수들이다.
	public ArrayList<Librarian> listLib()
	{
		ArrayList<Librarian> list = new ArrayList<Librarian>();

		try {
			ResultSet rs = stat.executeQuery("select * from Librarian;");
			while(rs.next())
			{
				Librarian lib = new Librarian();
				lib.ID = rs.getLong(1);
				lib.FName = rs.getString(2);
				lib.LName = rs.getString(3);
				lib.Super_ID = rs.getLong(4);
				lib.Phone = rs.getString(5);
				lib.Start_Date = rs.getString(6);
				list.add(lib);
			}
		} catch (SQLException e) {
			
		}
		return list;
	}

	public ArrayList<Storage> listStor()
	{
		ArrayList<Storage> list = new ArrayList<Storage>();

		try {
			ResultSet rs = stat.executeQuery("select * from Storage;");
			while(rs.next())
			{
				Storage stor = new Storage();
				stor.Name = rs.getString(1);
				stor.Mgr_ID = rs.getLong(2);
				list.add(stor);
			}
		} catch (SQLException e) {
			
		}
		return list;
	}
	
	public ArrayList<Event> listEvent()
	{
		ArrayList<Event> list = new ArrayList<Event>();

		try {
			ResultSet rs = stat.executeQuery("select * from Event;");
			while(rs.next())
			{
				Event stor = new Event();
				stor.ID = rs.getLong(1);
				stor.Start_Date = rs.getString(2);
				stor.End_Date= rs.getString(3);
				stor.Host = rs.getString(4);
				list.add(stor);
			}
		} catch (SQLException e) {
			
		}
		return list;
	}
	
	public ArrayList<User> listUser()
	{
		ArrayList<User> list = new ArrayList<User>();

		try {
			ResultSet rs = stat.executeQuery("select * from User;");
			while(rs.next())
			{
				User stor = new User();
				stor.ID = rs.getLong(1);
				stor.FName = rs.getString(2);
				stor.LName= rs.getString(3);
				stor.Phone = rs.getString(4);
				list.add(stor);
			}
		} catch (SQLException e) {
			
		}
		return list;
	}
	

	public ArrayList<E_Participate> listE_Participate()
	{
		ArrayList<E_Participate> list = new ArrayList<E_Participate>();

		try {
			ResultSet rs = stat.executeQuery("select * from E_Participate;");
			while(rs.next())
			{
				E_Participate stor = new E_Participate();
				stor.Event_ID = rs.getLong(1);
				stor.Participant_ID = rs.getLong(2);

				list.add(stor);
			}
		} catch (SQLException e) {
			
		}
		return list;
	}

	public ArrayList<BOOK> listBOOK()
	{
		ArrayList<BOOK> list = new ArrayList<BOOK>();

		try {
			ResultSet rs = stat.executeQuery("select * from BOOK;");
			while(rs.next())
			{
				BOOK stor = new BOOK();
				stor.ID = rs.getLong(1);
				stor.Name = rs.getString(2);
				stor.DofPurchase = rs.getString(3);
				stor.Storage_Name = rs.getString(4);

				
				list.add(stor);
			}
		} catch (SQLException e) {
			
		}
		return list;
	}
	
	public ArrayList<Burrow> listBurrow()
	{
		ArrayList<Burrow> list = new ArrayList<Burrow>();

		try {
			ResultSet rs = stat.executeQuery("select * from Burrow;");
			while(rs.next())
			{
				Burrow stor = new Burrow();
				stor.ID = rs.getLong(1);
				stor.Due_date = rs.getString(2);
				stor.Return_date = rs.getString(3);
				stor.Mgr_ID = rs.getLong(4);

				
				list.add(stor);
			}
		} catch (SQLException e) {
			
		}
		return list;
	}
	
	public ArrayList<BBU> listBBU()
	{
		ArrayList<BBU> list = new ArrayList<BBU>();

		try {
			ResultSet rs = stat.executeQuery("select * from BBU;");
			while(rs.next())
			{
				BBU stor = new BBU();
				stor.BUR_ID = rs.getLong(1);
				stor.BOO_ID = rs.getLong(2);
				stor.USR_ID = rs.getLong(3);

				
				list.add(stor);
			}
		} catch (SQLException e) {
			
		}
		return list;
	}
	
	public ArrayList<BOOK> FIND_BOOK(String key)
	{
		ArrayList<BOOK> list = new ArrayList<BOOK>();
		
		try {
			ResultSet rs = stat.executeQuery("select * from BOOK where Name like '%"+key+"%';");
			while(rs.next())
			{
				BOOK book = new BOOK();
				book.ID = rs.getLong(1);
				book.Name = rs.getString(2);
				book.DofPurchase = rs.getString(3);
				book.Storage_Name = rs.getString(4);
				
				list.add(book);
			}
		} catch (SQLException e) {
		}
		return list;
	}
	
	//삭제 함수들이다.
	public boolean Drop_Lib(long ID)
	{
		try {
			stat.executeUpdate("delete from librarian where ID = "+ID);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	public boolean Drop_Usr(long ID)
	{
		try {
			stat.executeUpdate("delete from User where ID = "+ID);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
	}
	public boolean Drop_Book(long ID)
	{
		try {
			stat.executeUpdate("delete from BOOK where ID = "+ID);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
	}
	
	
	//user ID와 Event ID를 이용해 Event에 참여하는 user를 추가하는 함수입니다.
	public boolean Make_EP(long user, long evt)
	{
		try {
			stat.executeUpdate("INSERT INTO E_Participate VALUES("+evt+","+user+")");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean Make_BBU(long user, long book)
	{
		try {
			stat.executeUpdate("INSERT INTO Burrow VALUES(NULL,'"+today()+"',NULL,"+1+")");
			//ResultSet rs = stat.executeQuery("select Return_Date From( BBU, Burrow Where BUR_ID=ID,))
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
