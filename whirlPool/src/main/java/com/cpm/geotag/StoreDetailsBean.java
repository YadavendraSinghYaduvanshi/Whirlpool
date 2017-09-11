package com.cpm.geotag;

public class StoreDetailsBean {

	public String storeaddress;
	public String Storeid;
	public String Storename;
	public String cityid;
	public String status;
	public String latitude;
	public String longitude;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	

	public String getStoreid() {
		return Storeid;
	}

	public void setStoreid(String storeid) {
		Storeid = storeid;
	}

	public String getStorename() {
		return Storename;
	}

	public void setStorename(String storename) {
		Storename = storename;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getStoreAddress() {
		return storeaddress;
	}

	public void setStoreAddress(String storeaddress) {
		this.storeaddress = storeaddress;
	}

}
