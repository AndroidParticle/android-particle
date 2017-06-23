package com.example.my.myapplication.serverTest.models;

import com.example.my.myapplication.IOT.server.models.Account;

import java.util.Date;

public class Admin extends Account {
	public boolean isAdmin;

	public Admin(String tenDN, String matKhau, String hoTen, boolean gioiTinh, Date ngaySinh, String email, String sdt,
			String hinhAnh, String gioiThieu, boolean isAdmin) {
//		super(tenDN, matKhau, hoTen, gioiTinh, ngaySinh, email, sdt, hinhAnh, gioiThieu);
		super();
		this.isAdmin = isAdmin;
	}

}
