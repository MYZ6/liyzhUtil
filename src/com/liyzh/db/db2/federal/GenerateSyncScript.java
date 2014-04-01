package com.liyzh.db.db2.federal;

public class GenerateSyncScript {
	public static void main(String[] args) {
		// String tables =
		// "BUSINESS_OPINION,BUSINESS_WORKITEM,WFMS_BUSINESS_VAR,"
		// +
		// "WFMS_BUSINESS_VAR_OVER,WFMS_INST_ACTIVITY,WFMS_INST_ACTIVITY_OVER,"
		// + "WFMS_INST_ACTVAR,WFMS_INST_ACTVAR_OVER,WFMS_INST_PROCESS,"
		// + "WFMS_INST_PROCESS_OVER,WFMS_INST_PROVAR,WFMS_INST_PROVAR_OVER,"
		// +
		// "WFMS_INST_TRANSITION,WFMS_INST_TRANSITION_OVER,WFMS_TERMINATE,WFMS_WORKITEM,WFMS_WORKITEM_OVER";
		// String tables = "WFMS_INST_ACTIVITY,WFMS_INST_ACTIVITY_OVER,"
		// + "WFMS_INST_PROCESS,"
		// + "WFMS_INST_PROCESS_OVER,WFMS_INST_PROVAR,WFMS_INST_PROVAR_OVER,"
		// +
		// "WFMS_INST_TRANSITION,WFMS_INST_TRANSITION_OVER,WFMS_TERMINATE,WFMS_WORKITEM,WFMS_WORKITEM_OVER";
		// String tables =
		// "T_BAS_LEAFZONE,DATADICT_CATEGORY,DATADICT_VALUE,T_LPB_UNITPERSONEL,ORGMODEL_USER,ORGMODEL_GROUP,ORGMODEL_JOB,ORGMODEL_ROLE,ORGMODEL_ROLETOGROUP,ORGMODEL_USERTOGROUP";
		String tables = "SECURITY_FUNCTION";
		String[] arr = tables.split(",");
		for (String ele : arr) {
			// System.out.println(" CREATE NICKNAME FMS48." + ele
			// + " FOR FMS48.fms." + ele + "");
			String s1 = "select count(*) from " + ele + " with ur;";
			System.out.println(s1);
		}
		System.out.println();
		for (String ele : arr) {
			// System.out.println(" CREATE NICKNAME FMS48." + ele
			// + " FOR FMS48.fms." + ele + "");
			String s2 = "delete from fms." + ele + ";";
			System.out.println(s2);
		}
		System.out.println();
		for (String ele : arr) {
			// System.out.println(" CREATE NICKNAME FMS48." + ele
			// + " FOR FMS48.fms." + ele + "");
			String s3 = "insert into fms." + ele + "\n"
					+ "select * from fms48." + ele + " with ur;";
			System.out.println(s3);
		}
		// System.out.println(arr.length);
	}
}
