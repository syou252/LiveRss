package com.example.liverss;

public class Item {
	private CharSequence mTitle;
	private CharSequence mLink;
	private CharSequence mDate;

	 public Item(){
	 mTitle = "";
	 mLink="";
	 mDate="";
	 }
	 
	 public CharSequence getLink(){
		 return mLink;
	 }

	 public void setLink(CharSequence link){
		 mLink = link;
	 }

	 public CharSequence getTitle(){
		 return mTitle;
	 }

	 public void setTitle(CharSequence title){
		 mTitle = title;
	 }

	 public CharSequence getDate(){
		 return mDate;
	 }

	 public void setDate(CharSequence date){
		 mDate = date;
	 }
}
