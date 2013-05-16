package com.liyzh.db2.federal;

public class GenerateFederalScript {
	public static void main(String[] args) {
		// String tables =
		// "T_BAS_LEAFZONE,DATADICT_CATEGORY,DATADICT_VALUE,T_LPB_UNITPERSONEL,ORGMODEL_USER,ORGMODEL_GROUP,ORGMODEL_JOB,ORGMODEL_ROLE,ORGMODEL_ROLETOGROUP,ORGMODEL_USERTOGROUP";
		String tables = "SECURITY_FUNCTION";
		String[] arr = tables.split(",");
		for (String ele : arr) {
			System.out.println(" CREATE NICKNAME FMS48." + ele
					+ " FOR FMS48.fms." + ele + ";");
		}
		// System.out.println(arr.length);
	}
}
