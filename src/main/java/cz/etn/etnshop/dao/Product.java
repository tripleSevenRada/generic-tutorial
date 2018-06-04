package cz.etn.etnshop.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.function.IntSupplier;

@Entity
@Table(name = "product")
public class Product implements Serializable, IntSupplier {

	// IntSupplier - to je jen tutorial cviceni, ne design choice...

	public Product(String name, int serial1, int serial2) {
		this.name = name;
		this.serial1 = serial1;
		this.serial2 = serial2;
	}

	public Product() {
	}

	private static final long serialVersionUID = -2739622030641073946L;

	private int id;
	@NotNull
	private String name;
    @Min(value = 1, message = "Serial 1 must not be less than 1")
    @Max(value = 100, message = "Serial 1 Must not be greater than 100")
	private int serial1;
    @Min(value = 1, message = "Serial 2 must not be less than 1")
    @Max(value = 100, message = "Serial 2 Must not be greater than 100")
	private int serial2;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "serialISSN1")
	public int getSerial1() {
		return serial1;
	}

	public void setSerial1(int serial1) {
		this.serial1 = serial1;
	}

	@Column(name = "serialISSN2")
	public int getSerial2() {
		return serial2;
	}

	public void setSerial2(int serial2) {
		this.serial2 = serial2;
	}

	@Transient
	public String getSerialISSN() {
		return String.valueOf(getSerial1()) + "-" + String.valueOf(getSerial2());
	}

	/*
	 * Tutorial practice purposes here, questionable design of course.
	 * 
	 * @see java.util.function.IntSupplier#getAsInt()
	 */
	@Override
	@Transient
	public int getAsInt() {
		return name.length();
	}
	
	@Override
	public String toString() {
		return this.name + " | " + this.id + " | " + this.serial1 + " | " + this.serial2;
	}
	
}
