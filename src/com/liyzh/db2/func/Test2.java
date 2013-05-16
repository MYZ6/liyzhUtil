//package com.liyzh.db2.func;
//
//import java.util.Enumeration;
//import java.util.Properties;
//
//public class Test2 extends UDF {
//	Enumeration propertyNames;
//	Properties properties;
//
//	public void dump(String property, String value) throws Exception {
//		// int callType = getCallType();
//		// switch (callType) {
//		// case SQLUDF_TF_FIRST:
//		// break;
//		// case SQLUDF_TF_OPEN:
//		// properties = System.getProperties();
//		// propertyNames = properties.propertyNames();
//		// break;
//		// case SQLUDF_TF_FETCH:
//		// if (propertyNames.hasMoreElements()) {
//		// property = (String) propertyNames.nextElement();
//		// value = properties.getProperty(property);
//		// set(1, property);
//		// set(2, value);
//		// } else {
//		// setSQLstate("02000");
//		// }
//		// break;
//		// case SQLUDF_TF_CLOSE:
//		// break;
//		// case SQLUDF_TF_FINAL:
//		// break;
//		// default:
//		// throw new Exception("UNEXPECT call type of " + callType);
//		// }
//	}
// }