/*
 Copyright(c) 2011,  Oriental e-way Software Limited. All rights reserved.
 http://www.oe-way.com.cn
 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
   1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
   2. Redistributions in binary form must reproduce the above copyright  notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
   3. Redistributions in any form must be accompanied by information on how to obtain complete source code for the Bizfocus software.
 */

package com.liyzh.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends Authenticator {

    private PasswordAuthentication m_Authentication;

    public MailAuthenticator(String username, String password) {
        m_Authentication = new PasswordAuthentication(username, password);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return m_Authentication;
    }

    public String getPassword() {
        return m_Authentication.getPassword();
    }

    public String getUserName() {
        return m_Authentication.getUserName();
    }

}
