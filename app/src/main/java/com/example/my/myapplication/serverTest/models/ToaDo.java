package com.example.my.myapplication.serverTest.models;

import java.io.Serializable;

public class ToaDo implements Serializable {
	private ViTri viTri;

	public ToaDo(ViTri viTri) {
		super();
		this.viTri = viTri;
	}

	public ViTri getViTri() {
		return viTri;
	}

	public void setViTri(ViTri viTri) {
		this.viTri = viTri;
	}

}
