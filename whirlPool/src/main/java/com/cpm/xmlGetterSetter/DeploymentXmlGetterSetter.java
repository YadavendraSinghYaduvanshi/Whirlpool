package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class DeploymentXmlGetterSetter {
	
	String deployment_master_table;

	ArrayList<String> DEPLOY_STATUS_CD	=	new ArrayList<String>();
	ArrayList<String> DEPLOY_STATUS		=	new ArrayList<String>();
	ArrayList<String> WHIRLPOOL_SKU 	=	new ArrayList<String>();

	public String getDeployment_master_table() {
		return deployment_master_table;
	}

	public void setDeployment_master_table(String deployment_master_table) {
		this.deployment_master_table = deployment_master_table;
	}

	public ArrayList<String> getDEPLOY_STATUS_CD() {
		return DEPLOY_STATUS_CD;
	}

	public void setDEPLOY_STATUS_CD(String DEPLOY_STATUS_CD) {
		this.DEPLOY_STATUS_CD.add(DEPLOY_STATUS_CD);
	}

	public ArrayList<String> getDEPLOY_STATUS() {
		return DEPLOY_STATUS;
	}

	public void setDEPLOY_STATUS(String DEPLOY_STATUS) {
		this.DEPLOY_STATUS.add(DEPLOY_STATUS);
	}

	public ArrayList<String> getWHIRLPOOL_SKU() {
		return WHIRLPOOL_SKU;
	}

	public void setWHIRLPOOL_SKU(String WHIRLPOOL_SKU) {
		this.WHIRLPOOL_SKU.add(WHIRLPOOL_SKU);
	}
}
