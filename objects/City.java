package objects;

public class City{
	
	private String name;
	private int x;
	private int y;
	private Float temp;
	
	public City(String name, int x, int y, Float temp) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.temp = temp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Float getTemp() {
		return temp;
	}

	public void setTemp(Float temp) {
		this.temp = temp;
	}

	
	
}
