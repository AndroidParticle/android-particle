package com.example.my.myapplication.serverTest.models;

import com.example.my.myapplication.IOT.server.models.Account;

import java.io.Serializable;
import java.util.List;

public class BoSuuTap implements Serializable {
	private String tenBST;
	private List<BaiViet> dsDD;
	private Account taiKhoan;

	public BoSuuTap(String tenBST, List<BaiViet> dsDD, Account taiKhoan) {
		super();
		this.tenBST = tenBST;
		this.dsDD = dsDD;
		this.taiKhoan = taiKhoan;
	}

	public String getTenBST() {
		return tenBST;
	}

	public void setTenBST(String tenBST) {
		this.tenBST = tenBST;
	}

	public List<BaiViet> getDsDD() {
		return dsDD;
	}

	public void setDsDD(List<BaiViet> dsDD) {
		this.dsDD = dsDD;
	}

	public Account getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(Account taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

}
