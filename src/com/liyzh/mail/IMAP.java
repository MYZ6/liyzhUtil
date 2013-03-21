package com.liyzh.mail;

import java.util.Properties;

import javax.mail.Session;

public class IMAP {
	public static void main(String[] args) {
		Properties props = System.getProperties();
		// props.put("mail.smtp.host", "imap.);
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true");
		MailAuthenticator authenticator = new MailAuthenticator("xiaogw",
				"123456");
		Session session = Session.getInstance(props, authenticator);

		// try {
		// // 构造MimeMessage 并设定基本的值
		// MimeMessage msg = new MimeMessage(session);
		// msg.setFrom(new InternetAddress(from, fromUser));
		// msg.setFrom(new InternetAddress(from));
		// String[] toList = StringUtil.spiltEmails(to);
		// InternetAddress[] address = new InternetAddress[toList.length];
		// for (int j = 0; j < toList.length; j++) {
		// address[j] = new InternetAddress(toList[j]);
		// }
		// // InternetAddress[] address = { new InternetAddress(to) };
		// msg.setRecipients(Message.RecipientType.TO, address);
		// // 设置抄送人地址
		// if (FrameworkUtil.convertToNull(this.cc) != null) {
		// String[] ccList = StringUtil.spiltEmails(cc);
		// InternetAddress[] ccAddress = new InternetAddress[ccList.length];
		// for (int k = 0; k < ccList.length; k++) {
		// ccAddress[k] = new InternetAddress(ccList[k]);
		// }
		// msg.setRecipients(Message.RecipientType.CC, ccAddress);
		// }
		// // 设置密送人地址
		// if (FrameworkUtil.convertToNull(this.bcc) != null) {
		// String[] bccList = StringUtil.spiltEmails(bcc);
		// InternetAddress[] bccAddress = new InternetAddress[bccList.length];
		// for (int l = 0; l < bccList.length; l++) {
		// bccAddress[l] = new InternetAddress(bccList[l]);
		// }
		// msg.setRecipients(Message.RecipientType.BCC, bccAddress);
		// }
		// subject = transferChinese(subject);
		// // msg.setSubject(subject);
		// // 测试新的构造正文标题方法 add by 8.10
		// msg.setSubject(MimeUtility.encodeText(subject, "utf-8 ", "B"));
		// // 构造Multipart
		// Multipart mp = new MimeMultipart();
		//
		// // 向Multipart添加正文
		// MimeBodyPart mbpContent = new MimeBodyPart();
		// mbpContent.setHeader("Content-Type", "text/html;charset=utf-8");
		// // content = transferChinese(content);
		// // mbpContent.setText(content);
		// mbpContent.setContent(content, "text/html;charset=utf-8");
		// // 向MimeMessage添加（Multipart代表正文）
		// mp.addBodyPart(mbpContent);
		//
		// // 向Multipart添加附件
		// // Enumeration efile = file.elements();
		// if (files != null) {
		// for (int i = 0; i < files.length; i++) {
		// File attachmentFile = files[i];
		// MimeBodyPart mbpFile = new MimeBodyPart();
		// FileDataSource fds = new FileDataSource(attachmentFile);
		// mbpFile.setDataHandler(new DataHandler(fds));
		// mbpFile.setFileName((MimeUtility
		// .encodeText(attachmentFileFileName[i])));
		// // 向MimeMessage添加（Multipart代表附件）
		// mp.addBodyPart(mbpFile);
		// }
		// }
		// if (appendFiles != null) {
		// for (int i = 0; i < appendFiles.length; i++) {
		// File attachmentFile = appendFiles[i];
		// MimeBodyPart mbpFile = new MimeBodyPart();
		// FileDataSource fds = new FileDataSource(attachmentFile);
		// mbpFile.setDataHandler(new DataHandler(fds));
		// mbpFile.setFileName((MimeUtility
		// .encodeText(appendFilesNames[i])));
		// Object cid = cidMap.get(appendFilesNames[i]);
		// if (cid != null) {
		// mbpFile.setHeader("Content-ID", cid.toString());
		// }
		// mp.addBodyPart(mbpFile);
		// }
		// }
		// List fckImages = (List) cidMap.get("fckImages");
		// if (fckImages != null && !fckImages.isEmpty()) {
		// for (Object obj : fckImages) {
		// File file = new File(Framework.getWebappRoot(),
		// obj.toString());
		// if (file.exists()) {
		// System.out.println(file);
		//
		// MimeBodyPart mbpFile = new MimeBodyPart();
		// FileDataSource fds = new FileDataSource(file);
		// mbpFile.setDataHandler(new DataHandler(fds));
		// mbpFile.setFileName((MimeUtility.encodeText(file
		// .getName())));
		// Object cid = cidMap.get(file.getName());
		// if (cid != null) {
		// mbpFile.setHeader("Content-ID",
		// "<" + cid.toString() + ">");
		// mp.addBodyPart(mbpFile);
		// }
		// }
		// }
		// }
		// // file.removeAllElements();
		// // 向Multipart添加MimeMessage
		// // msg.setContent(mp);
		// // 测试新的构造正文方法 add by 8.10
		// // msg.setContent(mp, "text/html;charset=gb2312 ");
		// msg.setContent(mp);
		// msg.setSentDate(new Date());
		// // 发送邮件
		// Transport.send(msg);
		// // 发送
		//
		// } catch (MessagingException mex) {
		// // give the EventQueue enough time to fire its events
		// try {
		// Thread.sleep(5);
		// } catch (InterruptedException e) {
		// }
		//
		// System.out.println("Sending failed with exception:");
		// mex.printStackTrace();
		// System.out.println();
		// Exception ex = mex;
		// do {
		// if (ex instanceof SendFailedException) {
		// SendFailedException sfex = (SendFailedException) ex;
		// Address[] invalid = sfex.getInvalidAddresses();
		// if (invalid != null) {
		// System.out.println("    ** Invalid Addresses");
		// for (int i = 0; i < invalid.length; i++)
		// System.out.println("         " + invalid[i]);
		// }
		// Address[] validUnsent = sfex.getValidUnsentAddresses();
		// if (validUnsent != null) {
		// System.out.println("    ** ValidUnsent Addresses");
		// for (int i = 0; i < validUnsent.length; i++)
		// System.out.println("         " + validUnsent[i]);
		// }
		// Address[] validSent = sfex.getValidSentAddresses();
		// if (validSent != null) {
		// System.out.println("    ** ValidSent Addresses");
		// for (int i = 0; i < validSent.length; i++)
		// System.out.println("         " + validSent[i]);
		// }
		// }
		// System.out.println();
		// if (ex instanceof MessagingException)
		// ex = ((MessagingException) ex).getNextException();
		// else
		// ex = null;
		// } while (ex != null);
		// return false;
		// }
		// return true;
	}
}
