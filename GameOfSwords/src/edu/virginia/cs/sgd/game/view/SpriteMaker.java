package edu.virginia.cs.sgd.game.view;

public class SpriteMaker {

	private int id;
	private String imgSource;
	
	public SpriteMaker(int id, String imgSource) {
		super();
		this.id = id;
		this.imgSource = imgSource;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImgSource() {
		return imgSource;
	}

	public void setImgSource(String imgSource) {
		this.imgSource = imgSource;
	}
	
}
