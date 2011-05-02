package propiedades;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jun 19, 2008
 */
@Entity
@Table(name = "t_propiedades")
@Proxy(lazy = false)
public class Propiedades {

  private int id;
  private Calendar cal;
  private Date date1;
  private byte[] img;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Calendar getCal() {
    return cal;
  }

  public void setCal(Calendar cal) {
    this.cal = cal;
  }

  public Date getDate1() {
    return date1;
  }

  @Temporal(TemporalType.TIME)
  public void setDate1(Date date1) {
    this.date1 = date1;
  }

  @Lob
  @Column(length = 70000)
  public byte[] getImg() {
    return img;
  }

  public void setImg(byte[] img) {
    this.img = img;
  }
}