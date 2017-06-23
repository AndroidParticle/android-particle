package com.example.my.myapplication.serverTest.models;

import java.io.Serializable;

public class ViTri implements Serializable {
	private String kinhDo;
	private String viDo;

	public ViTri(String kinhDo, String viDo) {
		super();
		this.kinhDo = kinhDo;
		this.viDo = viDo;
	}

	public String getKinhDo() {
		return kinhDo;
	}

	public void setKinhDo(String kinhDo) {
		this.kinhDo = kinhDo;
	}

	public String getViDo() {
		return viDo;
	}

	public void setViDo(String viDo) {
		this.viDo = viDo;
	}

}
