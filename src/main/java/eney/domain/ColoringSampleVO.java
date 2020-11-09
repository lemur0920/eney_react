package eney.domain;

public class ColoringSampleVO {
	private int id;
	private String title;
	private String script;
	private String gubun;	
	private String category;
	private String cat_file;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCat_file() {
		return cat_file;
	}
	public void setCat_file(String cat_file) {
		this.cat_file = cat_file;
	}
	@Override
	public String toString() {
		return "ColoringSampleVO [id=" + id + ", title=" + title + ", script="
				+ script + ", gubun=" + gubun + ", category=" + category
				+ ", cat_file=" + cat_file + "]";
	}
}
