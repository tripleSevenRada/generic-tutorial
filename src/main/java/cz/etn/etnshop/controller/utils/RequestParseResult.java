package cz.etn.etnshop.controller.utils;

public class RequestParseResult {

	private Integer id = null;
	private String name = null;
	private int serial1 = -1;
	private int serial2 = -1;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSerial1() {
		return serial1;
	}
	public void setSerial1(int serial1) {
		this.serial1 = serial1;
	}
	public int getSerial2() {
		return serial2;
	}
	public void setSerial2(int serial2) {
		this.serial2 = serial2;
	}
	public String toString() {
		return id + " | " + name + " | " + serial1 + " | " + serial2;
	}
}
