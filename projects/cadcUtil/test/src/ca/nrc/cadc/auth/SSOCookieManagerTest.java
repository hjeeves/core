/*
 ************************************************************************
 ****  C A N A D I A N   A S T R O N O M Y   D A T A   C E N T R E  *****
 *
 * (c) 2012.                         (c) 2012.
 * National Research Council            Conseil national de recherches
 * Ottawa, Canada, K1A 0R6              Ottawa, Canada, K1A 0R6
 * All rights reserved                  Tous droits reserves
 *
 * NRC disclaims any warranties         Le CNRC denie toute garantie
 * expressed, implied, or statu-        enoncee, implicite ou legale,
 * tory, of any kind with respect       de quelque nature que se soit,
 * to the software, including           concernant le logiciel, y com-
 * without limitation any war-          pris sans restriction toute
 * ranty of merchantability or          garantie de valeur marchande
 * fitness for a particular pur-        ou de pertinence pour un usage
 * pose.  NRC shall not be liable       particulier.  Le CNRC ne
 * in any event for any damages,        pourra en aucun cas etre tenu
 * whether direct or indirect,          responsable de tout dommage,
 * special or general, consequen-       direct ou indirect, particul-
 * tial or incidental, arising          ier ou general, accessoire ou
 * from the use of the software.        fortuit, resultant de l'utili-
 *                                      sation du logiciel.
 *
 *
 * @author jenkinsd
 * 4/17/12 - 11:21 AM
 *
 *
 *
 ****  C A N A D I A N   A S T R O N O M Y   D A T A   C E N T R E  *****
 ************************************************************************
 */
package ca.nrc.cadc.auth;



import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.Date;

import javax.servlet.http.Cookie;

import org.junit.Test;


public class SSOCookieManagerTest
{
    private SSOCookieManager testSubject;

    @Test
    public void parseOldCookieValue() throws Exception
    {
        Cookie ck = new Cookie(SSOCookieManager.DEFAULT_SSO_COOKIE_NAME, "username=TESTUSER|sessionID=88|token=AAABBB");
        
        SSOCookieManager cm = new SSOCookieManager();
        
        CookiePrincipal cp = cm.createPrincipal(ck);

        assertEquals("SessionId should be AAABBB", "AAABBB", cp.getSessionId());
    }

    @Test
    public void parseCookieValue() throws Exception
    {
        Cookie ck = new Cookie(SSOCookieManager.DEFAULT_SSO_COOKIE_NAME,
                               "AAABBB");

        SSOCookieManager cm = new SSOCookieManager();
        
        CookiePrincipal cp = cm.createPrincipal(ck);

        assertEquals("SessionId should be AAABBB", "AAABBB", cp.getSessionId());
    }
    
    @Test
    public void parseDelegCookieValue() throws Exception
    {
        HttpPrincipal user = new HttpPrincipal("auser");
        URI scope = new URI("vos://cadc.nrc.ca~vospace/myspace");
        DelegationToken dt = new DelegationToken(user, 10, scope, new Date());
        Cookie ck = new Cookie(SSOCookieManager.DELEGATION_COOKIE_NAME,
                               dt.format(false));

        SSOCookieManager cm = new SSOCookieManager();
        
        CookiePrincipal cp = cm.createPrincipal(ck);

        assertEquals("SessionId missmatch", dt.format(false), cp.getSessionId());
    }
}
