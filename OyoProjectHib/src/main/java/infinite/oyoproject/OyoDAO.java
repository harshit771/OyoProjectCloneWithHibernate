package infinite.oyoproject;


import java.sql.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;



public class OyoDAO {

	SessionFactory sFactory;
	Session session;
	public java.util.Date conversqltDate(java.sql.Date dt) {
		java.util.Date utilDate=new Date(dt.getTime());
		return utilDate;
	}
	
	public int days(Date chDate,Date chOutDate) {
		Booking booking=new Booking();
		
		java.util.Date checkInDate=conversqltDate(chDate);
		java.util.Date checkOutDate=conversqltDate(chOutDate);
		int days=(int)((checkOutDate.getTime()-checkInDate.getTime())/(1000*60*60*24));
		days++;
		return days;
	}
	
	public String bookIdGen() {
		sFactory=SessionHelper.getConnection();
		session=sFactory.openSession();
		Criteria cr=session.createCriteria(Booking.class);
		List<Booking> bookList=cr.list();
		if(bookList.size()==0) {
			return "B001";
		}
		int id=Integer.parseInt(bookList.get(bookList.size() - 1).getBookId().substring(1));
		String bid=String.format("B%03d", ++id);
		return bid;
	}
	
	public String idGenerate() {
		sFactory=SessionHelper.getConnection();
		session=sFactory.openSession();
		Criteria cr=session.createCriteria(Room.class);
		List<Room> roomList=cr.list();
		if(roomList.size()==0) {
			return "R001";
		}
		int id=Integer.parseInt(roomList.get(roomList.size() - 1).getRoomId().substring(1));
		String rid=String.format("R%03d", ++id);
		return rid;
	}
	
	public Room searchById(String roomId) {
		sFactory = SessionHelper.getConnection();
  		Session session = sFactory.openSession(); 
  		Criteria cr = session.createCriteria(Room.class);
  		cr.add(Restrictions.eq("roomId", roomId));
  		List<Room> roomList = cr.list();
  		return roomList.get(0);
	}
	
	public Booking searhByBookId(String bookId) {
		sFactory = SessionHelper.getConnection();
  		Session session = sFactory.openSession(); 
  		Criteria cr = session.createCriteria(Booking.class);
  		cr.add(Restrictions.eq("bookId", bookId));
  		List<Booking> bookList = cr.list();
  		return bookList.get(0);
	}
	
	public int billAmt(String bookId,int days) {
		Booking booking=searhByBookId(bookId);
		String roomId=booking.getRoomId();
		Room room=searchById(roomId);
		int cost= room.getCostperday();
		int amt=(days*cost)+((days*cost)*18)/100;
		
		return amt;
	}
	public List<Room> showRooms(){
			sFactory=SessionHelper.getConnection();
			session=sFactory.openSession();
			Criteria cr=session.createCriteria(Room.class);
			cr.add(Restrictions.eq("status", Status.AVAILABLE));
			Projection projection=Projections.property("roomId");
			 cr.setProjection(projection);
			List<Room> roomList=cr.list();
			return roomList;
				
	}
	
	public List<Booking> showBookings(){
		sFactory=SessionHelper.getConnection();
		session=sFactory.openSession();
		Criteria cr=session.createCriteria(Booking.class);
		Projection projection=Projections.property("roomId");
		cr.setProjection(projection);
		List<Booking> roomList=cr.list();
		return roomList;
	}
	public List<Room> showBookRooms(){
		sFactory=SessionHelper.getConnection();
		session=sFactory.openSession();
		Criteria cr=session.createCriteria(Room.class);
		cr.add(Restrictions.eq("status", Status.BOOKED));
		Projection projection=Projections.property("roomId");
	
		 cr.setProjection(projection);
		List<Room> roomList=cr.list();
		return roomList;
			
}
	public Date convertDate(java.util.Date dt) {
		java.sql.Date sqlDate=new Date(dt.getTime());
		return sqlDate;
	}
	
	
	
	public String addRoom(Room room) {
		sFactory=SessionHelper.getConnection();
		session=sFactory.openSession();
		String roomId=idGenerate();
		Transaction tr=session.beginTransaction();
		room.setRoomId(roomId);
		room.setStatus(Status.AVAILABLE);
		session.save(room);
		tr.commit();
		return "Room Added...";
		
   }
	
	public String bookRoom(Booking book) {
		sFactory=SessionHelper.getConnection();
		session=sFactory.openSession();
		String bookId=bookIdGen();
		
		book.setBookId(bookId);
		java.util.Date date=new java.util.Date();
		java.sql.Date bookDate=new Date(date.getTime());
		
		book.setBookDate(bookDate);
		Transaction tr=session.beginTransaction();
		session.save(book);
		tr.commit();
		session.close();
		
		Room room=searchById(book.getRoomId());
		room.setStatus(Status.BOOKED);
		
		session=sFactory.openSession();
		tr=session.beginTransaction();
		session.update(room);
		tr.commit();
		session.close();
		
		return "Room booked...";
		
	}
	
	public String billingRoom(String bookId) {
		sFactory=SessionHelper.getConnection();
		session=sFactory.openSession();
		Criteria cr=session.createCriteria(Room.class);
		cr.add(Restrictions.eq("status", Status.BOOKED));
		Projection projection=Projections.property("roomId");
		Billing billing=new Billing();
		Booking booking=searhByBookId(bookId);
		billing.setBookId(booking.getBookId());
		billing.setRoomId(booking.getRoomId());
		int noOfDays=days(booking.getChDate(), booking.getChoutDate());
	    billing.setNoOfDays(noOfDays);
	    int billAmt=billAmt(bookId, noOfDays);
	    billing.setBillAmt(billAmt);
	   
		Transaction tr=session.beginTransaction();
		session.save(billing);
		tr.commit();
		session.close();
		
		Room room=searchById(billing.getRoomId());
		room.setStatus(Status.AVAILABLE);
		
		session=sFactory.openSession();
		tr=session.beginTransaction();
		session.update(room);
		tr.commit();
		session.close();
		
		
		return "Billing successfully and Your Bill is "+billAmt;
	}
}
