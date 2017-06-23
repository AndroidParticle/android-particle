package com.example.my.myapplication.serverTest.models;

import java.io.Serializable;

public class DanhGia implements Serializable {
	private int like;
	private int share;
	private int comment;

	public DanhGia(int like, int share, int comment) {
		super();
		this.like = like;
		this.share = share;
		this.comment = comment;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public int getShare() {
		return share;
	}

	public void setShare(int share) {
		this.share = share;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

}
