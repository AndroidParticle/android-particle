package com.example.my.myapplication.serverTest.models;

import java.io.Serializable;
import java.util.Date;

public class BaiViet implements Serializable {
	private int id;
	private String ten;
	private String urlBaiViet;
	private Date ngayDang;
	private String noiDung;
	private ViTri viTri;
	private String anhBia;
	private DanhGia danhGia;

	public BaiViet(int id, String ten, String urlBaiViet, Date ngayDang, String noiDung, ViTri viTri, String anhBia,
			DanhGia danhGia) {
		super();
		this.id = id;
		this.ten = ten;
		this.urlBaiViet = urlBaiViet;
		this.ngayDang = ngayDang;
		this.noiDung = noiDung;
		this.viTri = viTri;
		this.anhBia = anhBia;
		this.danhGia = danhGia;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getUrlBaiViet() {
		return urlBaiViet;
	}

	public void setUrlBaiViet(String urlBaiViet) {
		this.urlBaiViet = urlBaiViet;
	}

	public Date getNgayDang() {
		return ngayDang;
	}

	public void setNgayDang(Date ngayDang) {
		this.ngayDang = ngayDang;
	}

	public String getNoiDung() {
		return noiDung;
	}

	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}

	public ViTri getViTri() {
		return viTri;
	}

	public void setViTri(ViTri viTri) {
		this.viTri = viTri;
	}

	public String getAnhBia() {
		return anhBia;
	}

	public void setAnhBia(String anhBia) {
		this.anhBia = anhBia;
	}

	public DanhGia getDanhGia() {
		return danhGia;
	}

	public void setDanhGia(DanhGia danhGia) {
		this.danhGia = danhGia;
	}

}
