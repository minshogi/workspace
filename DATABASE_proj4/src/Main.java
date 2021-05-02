
import java.awt.print.Book;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class Main {

	private final static String[] MENU = new String[]{
		"사서",
		"이용자",
		"도서",
		"이벤트",
		"보관소",
		"대출",
		"종료"
	};
	private final static String[] MENU_lib = new String[]{
		"사서 등록",
		"사서 탈퇴",
		"정보 업데이트",
		"정보 확인",
		"뒤로"
	};
	private final static String[] MENU_usr = new String[]{
		"회원가입",
		"회원 탈퇴",
		"정보 업데이트",
		"정보 확인",
		"뒤로"
	};
	private final static String[] MENU_book = new String[]{
		"도서 등록",
		"도서 검색",
		"도서 삭제",
		"뒤로"
	};
	private final static String[] MENU_event = new String[]{
		"새로운 이벤트 등록",
		"이벤트 목록",
		"이벤트 참가",
		"뒤로"
	};
	private final static String[] MENU_stor = new String[]{
		"보관소 등록",
		"보관소 목록",
		"뒤로"
	};
	private final static String[] MENU_bur = new String[]{
		"대출",
		"반납",
		"대출 정보 확인",
		"뒤로"
	};
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Library library;
		try {
			library = new Library();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No connection to Database");
			return;
		}

		library.start();
		
		boolean loopflag = true;
		while(loopflag){
			for(int i=0; i<MENU.length; i++)
			{
				System.out.println((i+1)+". " + MENU[i]);
			}
			int menu;
			do
			{
				menu = sc.nextInt();
				
			}while(menu<1 || menu>MENU.length);
			
			int menu_sel;
			switch(menu)
			{
			//사서
			case 1:
				for(int j=0; j<MENU_lib.length; j++)
				{
					System.out.println((j+1)+". "+MENU_lib[j]);
				}
				do
				{
					menu_sel = sc.nextInt();
				}while(menu_sel<1 || menu_sel>MENU_lib.length);
				
				switch(menu_sel)
				{
				case 1:	//사서등록
					Librarian libr = new Librarian();
					System.out.println("Input FName : ");
					libr.FName = sc.next();
					System.out.println("Input LName : ");
					libr.LName = sc.next();
					System.out.println("Input phone number : ");
					libr.Phone = sc.next();
					
					
					
					
					library.insertLib(libr);
					break;
				case 2:	//사서 탈퇴
					System.out.println("사서 목록");
					ArrayList<Librarian> list2 = library.listLib();
					for(Librarian lib : list2)
					{
						System.out.println("ID : "+lib.ID);
					}
					System.out.println("탈퇴할 ID를 입력하세요.");
					long eid = sc.nextLong();
					boolean is_in = false;
					for(Librarian lib : list2)
					{
						if(eid == lib.ID)
						{
							is_in = true;
						}
					}
					
					if(!is_in)
					{
						System.out.println("wrong input");
						break;
					}
					
					library.Drop_Lib(eid);
					break;
					
				case 3:	//정보 업데이트
				case 4:	//정보 확인
					ArrayList<Librarian> list4 = library.listLib();
					for(Librarian lib : list4)
					{
						System.out.println("ID : "+lib.ID);
						System.out.println("Fname : "+lib.FName);
						System.out.println("Lname : "+lib.LName);
						System.out.println("Super_ID : "+lib.Super_ID);
						System.out.println("Phone number : "+lib.Phone);
						System.out.println("Start_Date : "+lib.Start_Date);
						System.out.println();
					}
					break;
				case 5:	//뒤로
					break;
					
				default :	//잘못된 입력은 종료함
					loopflag = false;
					break;
				}
				break;
			
			//이용자
			case 2:
				for(int j=0; j<MENU_usr.length; j++)
				{
					System.out.println((j+1)+". "+MENU_usr[j]);
				}
				do
				{
					menu_sel = sc.nextInt();
				}while(menu_sel<1 || menu_sel>MENU_usr.length);
				
				switch(menu_sel)
				{
				
				case 1:	//회원가입

					User usr = new User();
					System.out.println("Input FName : ");
					usr.FName = sc.next();
					System.out.println("Input LName : ");
					usr.LName = sc.next();
					System.out.println("Input Phone number : ");
					usr.Phone = sc.next();
					
					library.insertUser(usr);
					break;
				case 2:	//회원탈퇴
					
					System.out.println("유저 리스트");
					ArrayList<User> list = library.listUser();
					for(User usr2 : list)
					{
						System.out.println("ID : "+usr2.ID);
					}
					System.out.println("탈퇴할 ID를 입력하세요.");
					long eid = sc.nextLong();
					boolean is_in = false;
					for(User usr2 : list)
					{
						if(usr2.ID == eid)
						{
							is_in = true;
						}
					}
					if(!is_in)
					{
						System.out.println("wrong input");
						break;
					}
					library.Drop_Usr(eid);
					break;
				case 3: //정보 업데이트
				case 4:	//정보 확인
					ArrayList<User> list4 = library.listUser();
					
					for(User usr3 : list4)
					{
						System.out.println("ID : "+usr3.ID);
						System.out.println("Fname : "+usr3.FName);
						System.out.println("Lname : "+usr3.LName);
						System.out.println("phone number : "+usr3.Phone);
						System.out.println();
					}
				case 5:	//뒤로
					break;
					default : 
						loopflag = false;
						break;
				}
				break;
				
			//도서
			case 3:
				for(int j=0; j<MENU_book.length; j++)
				{
					System.out.println((j+1)+". "+MENU_book[j]);
				}
				do
				{
					menu_sel = sc.nextInt();
				}while(menu_sel<1 || menu_sel>MENU_book.length);
				
				switch(menu_sel)
				{
				
				case 1:	//도서 등록
					BOOK book = new BOOK();
					
					System.out.println("Input title : ");
					book.Name = sc.next();
					
					System.out.println("저장소 목록 : ");
					ArrayList<Storage> list = library.listStor();
					for(Storage stor : list)
					{
						System.out.println(stor.Name);
					}
					System.out.println();
					System.out.println("Select Storage (Storage name) : ");
					book.Storage_Name = sc.next();
					boolean is_in=false;
					for(Storage stor : list)
					{
						if(stor.Name.equals(book.Storage_Name))
						{
							is_in = true;
						}
					}
					if(!is_in)
					{
						System.out.println("wrong input");
						break;
					}
					
					
					library.insertBOOK(book);
					break;
				case 2:	//도서 검색
					System.out.println("키워드를 입력하세요.");
					String key = sc.next();
					
					ArrayList<BOOK> list2 = library.FIND_BOOK(key);
					for(BOOK book2 : list2)
					{
						System.out.println("ID : "+book2.ID);
						System.out.println("Name : "+book2.Name);
						System.out.println("Day of Purchase : "+book2.DofPurchase);
						System.out.println("Storage name : "+book2.Storage_Name);
						System.out.println();
					}
					break;
				case 3: //도서 삭제
					System.out.println("도서 목록");
					ArrayList<BOOK> list3 = library.listBOOK();
					for(BOOK book3 : list3)
					{
						System.out.println("ID : "+book3.ID);
						System.out.println("Title : "+book3.Name);
					}
					
					System.out.println("삭제할 도서 ID를 입력하세요");
					long eid = sc.nextLong();
					boolean is_in3 = false;
					for(BOOK book3 : list3)
					{
						if(book3.ID == eid)
						{
							is_in3 = true;
						}
					}
					if(!is_in3)
					{
						System.out.println("wrong input");
						break;
					}
					library.Drop_Book(eid);
					break;
				case 4:	//뒤로
					break;
					default : 
						loopflag = false;
						break;
				}
				break;
			//이벤트
			case 4:
				for(int j=0; j<MENU_event.length; j++)
				{
					System.out.println((j+1)+". "+MENU_event[j]);
				}
				do
				{
					menu_sel = sc.nextInt();
				}while(menu_sel<1 || menu_sel>MENU_event.length);
				
				switch(menu_sel)
				{
				
				case 1:	//새로운 이벤트 등록
					Event evt = new Event();
					
					System.out.println("Input event Start Date(yyyyMMdd) : ");
					evt.Start_Date = sc.next();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Date da = new Date();
					try {
						da = sdf.parse(evt.Start_Date);
					} catch (ParseException e) {
						System.out.println("wrong input.");
						break;
					}
					
					System.out.println("Input event End Date(yyyyMMdd) : ");
					evt.End_Date = sc.next();
					try {
						da = sdf.parse(evt.End_Date);
					} catch (ParseException e) {
						System.out.println("wrong input.");
						break;
					}
					
					ArrayList<Storage> list = library.listStor();
					
					for(Storage stor : list)
					{
						System.out.println(stor.Name);
					}
					System.out.println();
					
					System.out.println("select host storage(Name)");
					evt.Host= sc.next();
					boolean is_in=false;
					for(Storage stor : list)
					{
						if(stor.Name.equals(evt.Host))
						{
							is_in = true;
						}
					}
					
					if(!is_in)
					{
						System.out.println("wrong input");
						break;
					}
					
					
					library.insertEvent(evt);
					break;
				case 2:	//이벤트 목록
					ArrayList<Event> list2 = library.listEvent();
					
					for(Event eve : list2)
					{
						System.out.println("ID : "+eve.ID);
						System.out.println("Start Date : "+eve.Start_Date);
						System.out.println("End Date : "+eve.End_Date);
						System.out.println("Host : "+eve.Host);
					}
					break;
				case 3:	//이벤트 참가
					System.out.println("이벤트 목록");
					ArrayList<Event> list3 = library.listEvent();
					
					for(Event eve : list3)
					{
						System.out.println("ID : "+eve.ID);
					}

					System.out.println("참여할 이벤트 ID를 입력하세요");
					long eid = sc.nextLong();
					boolean is_in3 = false;
					for(Event book3 : list3)
					{
						if(book3.ID == eid)
						{
							is_in3 = true;
						}
					}
					if(!is_in3)
					{
						System.out.println("wrong input");
						break;
					}
					
					System.out.println("유저 목록");
					ArrayList<User> listUsr = library.listUser();
					
					for(User usr : listUsr)
					{
						System.out.println("ID : "+usr.ID);
					}

					System.out.println("유저 ID를 입력하세요");
					long eidus = sc.nextLong();
					boolean is_inus = false;
					for(User usr : listUsr)
					{
						if(usr.ID == eidus)
						{
							is_inus = true;
						}
					}
					if(!is_inus)
					{
						System.out.println("wrong input");
						break;
					}

					library.Make_EP(eidus, eid);
					break;
				case 4:	//뒤로
					break;
					default : 
						loopflag = false;
						break;
				}
				break;
			//보관소
			case 5:
				for(int j=0; j<MENU_stor.length; j++)
				{
					System.out.println((j+1)+". "+MENU_stor[j]);
				}
				do
				{
					menu_sel = sc.nextInt();
				}while(menu_sel<1 || menu_sel>MENU_stor.length);
				
				switch(menu_sel)
				{
				
				case 1:	//보관소 등록

					Storage stor = new Storage();
					System.out.println("Input Name : ");
					stor.Name = sc.next();
					
					ArrayList<Librarian> list = library.listLib();
					System.out.println("사서 ID 목록 : ");
					for(Librarian libr : list)
					{
						System.out.println(libr.ID);
					}
					System.out.println("Input Manager ID : ");
					stor.Mgr_ID = sc.nextLong();
					boolean is_in = false;
					for(Librarian libr : list)
					{
						if(libr.ID == stor.Mgr_ID)
						{
							is_in = true;
						}
					}
					
					if(!is_in)
					{
						System.out.println("wrong input");
						break;
					}
					
					library.insertStor(stor);
					break;
				case 2:	//보관소 목록
					ArrayList<Storage> list2 = library.listStor();
					for(Storage stor2 : list2)
					{
						System.out.println("Name : "+stor2.Name);
						System.out.println("Manager ID : "+ stor2.Mgr_ID);
						System.out.println();
					}
				case 3:	//뒤로
					break;
					default : 
						loopflag = false;
						break;
				}
				break;
				
			//대출
			case 6:
				for(int j=0; j<MENU_bur.length; j++)
				{
					System.out.println((j+1)+". "+MENU_bur[j]);
				}
				do
				{
					menu_sel = sc.nextInt();
				}while(menu_sel<1 || menu_sel>MENU_bur.length);
				
				System.out.println("미완성");
				/*
				switch(menu_sel)
				{
				
				case 1:	//대출
					ArrayList<BOOK> listbook = library.listBOOK();
					ArrayList<BBU> listbbu = library.listBBU();
					ArrayList<User> listusr = library.listUser();
					
					System.out.println("대출 가능한 책 목록");
					for(BOOK book : listbook)
					{
						boolean is_in = false;
						for(BBU bbu : listbbu){
							
							if(bbu.BOO_ID != book.ID)
							{
								is_in=true;
							}
						}
						if(!is_in)
						{
							System.out.println("ID : "+book.ID);
							System.out.println("Title : "+book.Name);
						}
					}
					

					System.out.println("책 ID를 입력하세요");
					long eidbook = sc.nextLong();
					boolean is_inbook = false;
					for(BOOK book : listbook)
					{
						if(book.ID == eidbook)
						{
							is_inbook = true;
						}
					}
					if(!is_inbook)
					{
						System.out.println("wrong input");
						break;
					}
					
					System.out.println("유저 목록");
					for(User usr : listusr)
					{
						
						System.out.println("ID : "+usr.ID);
						
					}
					

					System.out.println("유저 ID를 입력하세요");
					long eidusr = sc.nextLong();
					boolean is_inusr = false;
					for(User usr : listusr)
					{
						if(usr.ID == eidusr)
						{
							is_inusr = true;
						}
					}
					if(!is_inusr)
					{
						System.out.println("wrong input");
						break;
					}
					
					
				case 2:	//반납
				case 3:	//대출 정보 확인
				case 4:	//뒤로
					break;
					default : 
						loopflag = false;
						break;
				}
				break;
				*/
			//종료
			case 7:
				loopflag = false;
				break;
			
			default:
				loopflag = false;
				break;
				
			}
		}
		sc.close();
		
	}
	

}
