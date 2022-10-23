package infinite.oyoproject;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Booking")
public class Booking {
     
	@Id
	@Column(name="BookId")
	private String bookId;
	
	@Column(name="RoomId")
	private String roomId;
	
	@Column(name="CustName")
	private String custName;
	
	@Column(name="City")
	private String city;
	
	@Column(name="BookDate")
	private Date bookDate;
	
	@Column(name="ChkInDate")
	private Date chDate;
	
	@Column(name="ChkOutDate")
	private Date choutDate;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getBookDate() {
		return bookDate;
	}

	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}

	public Date getChDate() {
		return chDate;
	}

	public void setChDate(Date chDate) {
		this.chDate = chDate;
	}

	public Date getChoutDate() {
		return choutDate;
	}

	public void setChoutDate(Date choutDate) {
		this.choutDate = choutDate;
	}
	
	
	
	
}
