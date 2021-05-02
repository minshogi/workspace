
import java.awt.print.Book;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class Main {

	private final static String[] MENU = new String[]{
		"�缭",
		"�̿���",
		"����",
		"�̺�Ʈ",
		"������",
		"����",
		"����"
	};
	private final static String[] MENU_lib = new String[]{
		"�缭 ���",
		"�缭 Ż��",
		"���� ������Ʈ",
		"���� Ȯ��",
		"�ڷ�"
	};
	private final static String[] MENU_usr = new String[]{
		"ȸ������",
		"ȸ�� Ż��",
		"���� ������Ʈ",
		"���� Ȯ��",
		"�ڷ�"
	};
	private final static String[] MENU_book = new String[]{
		"���� ���",
		"���� �˻�",
		"���� ����",
		"�ڷ�"
	};
	private final static String[] MENU_event = new String[]{
		"���ο� �̺�Ʈ ���",
		"�̺�Ʈ ���",
		"�̺�Ʈ ����",
		"�ڷ�"
	};
	private final static String[] MENU_stor = new String[]{
		"������ ���",
		"������ ���",
		"�ڷ�"
	};
	private final static String[] MENU_bur = new String[]{
		"����",
		"�ݳ�",
		"���� ���� Ȯ��",
		"�ڷ�"
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
			//�缭
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
				case 1:	//�缭���
					Librarian libr = new Librarian();
					System.out.println("Input FName : ");
					libr.FName = sc.next();
					System.out.println("Input LName : ");
					libr.LName = sc.next();
					System.out.println("Input phone number : ");
					libr.Phone = sc.next();
					
					
					
					
					library.insertLib(libr);
					break;
				case 2:	//�缭 Ż��
					System.out.println("�缭 ���");
					ArrayList<Librarian> list2 = library.listLib();
					for(Librarian lib : list2)
					{
						System.out.println("ID : "+lib.ID);
					}
					System.out.println("Ż���� ID�� �Է��ϼ���.");
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
					
				case 3:	//���� ������Ʈ
				case 4:	//���� Ȯ��
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
				case 5:	//�ڷ�
					break;
					
				default :	//�߸��� �Է��� ������
					loopflag = false;
					break;
				}
				break;
			
			//�̿���
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
				
				case 1:	//ȸ������

					User usr = new User();
					System.out.println("Input FName : ");
					usr.FName = sc.next();
					System.out.println("Input LName : ");
					usr.LName = sc.next();
					System.out.println("Input Phone number : ");
					usr.Phone = sc.next();
					
					library.insertUser(usr);
					break;
				case 2:	//ȸ��Ż��
					
					System.out.println("���� ����Ʈ");
					ArrayList<User> list = library.listUser();
					for(User usr2 : list)
					{
						System.out.println("ID : "+usr2.ID);
					}
					System.out.println("Ż���� ID�� �Է��ϼ���.");
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
				case 3: //���� ������Ʈ
				case 4:	//���� Ȯ��
					ArrayList<User> list4 = library.listUser();
					
					for(User usr3 : list4)
					{
						System.out.println("ID : "+usr3.ID);
						System.out.println("Fname : "+usr3.FName);
						System.out.println("Lname : "+usr3.LName);
						System.out.println("phone number : "+usr3.Phone);
						System.out.println();
					}
				case 5:	//�ڷ�
					break;
					default : 
						loopflag = false;
						break;
				}
				break;
				
			//����
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
				
				case 1:	//���� ���
					BOOK book = new BOOK();
					
					System.out.println("Input title : ");
					book.Name = sc.next();
					
					System.out.println("����� ��� : ");
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
				case 2:	//���� �˻�
					System.out.println("Ű���带 �Է��ϼ���.");
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
				case 3: //���� ����
					System.out.println("���� ���");
					ArrayList<BOOK> list3 = library.listBOOK();
					for(BOOK book3 : list3)
					{
						System.out.println("ID : "+book3.ID);
						System.out.println("Title : "+book3.Name);
					}
					
					System.out.println("������ ���� ID�� �Է��ϼ���");
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
				case 4:	//�ڷ�
					break;
					default : 
						loopflag = false;
						break;
				}
				break;
			//�̺�Ʈ
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
				
				case 1:	//���ο� �̺�Ʈ ���
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
				case 2:	//�̺�Ʈ ���
					ArrayList<Event> list2 = library.listEvent();
					
					for(Event eve : list2)
					{
						System.out.println("ID : "+eve.ID);
						System.out.println("Start Date : "+eve.Start_Date);
						System.out.println("End Date : "+eve.End_Date);
						System.out.println("Host : "+eve.Host);
					}
					break;
				case 3:	//�̺�Ʈ ����
					System.out.println("�̺�Ʈ ���");
					ArrayList<Event> list3 = library.listEvent();
					
					for(Event eve : list3)
					{
						System.out.println("ID : "+eve.ID);
					}

					System.out.println("������ �̺�Ʈ ID�� �Է��ϼ���");
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
					
					System.out.println("���� ���");
					ArrayList<User> listUsr = library.listUser();
					
					for(User usr : listUsr)
					{
						System.out.println("ID : "+usr.ID);
					}

					System.out.println("���� ID�� �Է��ϼ���");
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
				case 4:	//�ڷ�
					break;
					default : 
						loopflag = false;
						break;
				}
				break;
			//������
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
				
				case 1:	//������ ���

					Storage stor = new Storage();
					System.out.println("Input Name : ");
					stor.Name = sc.next();
					
					ArrayList<Librarian> list = library.listLib();
					System.out.println("�缭 ID ��� : ");
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
				case 2:	//������ ���
					ArrayList<Storage> list2 = library.listStor();
					for(Storage stor2 : list2)
					{
						System.out.println("Name : "+stor2.Name);
						System.out.println("Manager ID : "+ stor2.Mgr_ID);
						System.out.println();
					}
				case 3:	//�ڷ�
					break;
					default : 
						loopflag = false;
						break;
				}
				break;
				
			//����
			case 6:
				for(int j=0; j<MENU_bur.length; j++)
				{
					System.out.println((j+1)+". "+MENU_bur[j]);
				}
				do
				{
					menu_sel = sc.nextInt();
				}while(menu_sel<1 || menu_sel>MENU_bur.length);
				
				System.out.println("�̿ϼ�");
				/*
				switch(menu_sel)
				{
				
				case 1:	//����
					ArrayList<BOOK> listbook = library.listBOOK();
					ArrayList<BBU> listbbu = library.listBBU();
					ArrayList<User> listusr = library.listUser();
					
					System.out.println("���� ������ å ���");
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
					

					System.out.println("å ID�� �Է��ϼ���");
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
					
					System.out.println("���� ���");
					for(User usr : listusr)
					{
						
						System.out.println("ID : "+usr.ID);
						
					}
					

					System.out.println("���� ID�� �Է��ϼ���");
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
					
					
				case 2:	//�ݳ�
				case 3:	//���� ���� Ȯ��
				case 4:	//�ڷ�
					break;
					default : 
						loopflag = false;
						break;
				}
				break;
				*/
			//����
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
