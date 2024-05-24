package com.sds.foodfit.domain;

import lombok.Data;

@Data
public class RecipDB {
	public int RCP_SEQ;				// 레시피DB 번호
	public String RCP_NM;			// 음식명
	public int INFO_WGT;			// 1인분
	public String RCP_PARTS_DTLS;	// 재료들	

}
