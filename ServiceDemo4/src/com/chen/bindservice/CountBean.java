package com.chen.bindservice;

import android.os.Parcel;
import android.os.Parcelable;

public class CountBean implements Parcelable{
	public int count;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public CountBean(){
		
	}
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.count);
		
	}
	
	public static final Parcelable.Creator<CountBean> CREATOR =  new Creator<CountBean>(){

		@Override
		public CountBean createFromParcel(Parcel source) {
			CountBean bean = new CountBean();
			bean.count = source.readInt();
			
			return bean;
		}

		@Override
		public CountBean[] newArray(int size) {
			// TODO Auto-generated method stub
			return new CountBean[size];
		}
		
	};
}
