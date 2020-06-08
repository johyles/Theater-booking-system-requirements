package entity;

public class Theater {
	private Integer id;
	private String name;
	private Integer rows;
	private Integer columns;
	public Theater(Integer id, String name, Integer rows, Integer columns) {
		super();
		this.id = id;
		this.name = name;
		this.rows = rows;
		this.columns = columns;
	}
	
	public Theater() {
	}

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

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getColumns() {
		return columns;
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		return  name ;
	}
	
}
