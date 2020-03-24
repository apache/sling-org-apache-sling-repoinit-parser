/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.sling.repoinit.parser.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.sling.repoinit.parser.impl.ParseException;
import org.apache.sling.repoinit.parser.impl.RepoInitParserImpl;
import org.apache.sling.repoinit.parser.impl.TokenMgrError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/** Test various parsing errors */
@RunWith(Parameterized.class)
public class ParsingErrorsTest {

    private String input;
    private Class<? extends Throwable> expected;
    
    @Parameters
    public static Collection<Object[]> data() {
        @SuppressWarnings("serial")
        final List<Object []> result = new ArrayList<Object []>() {{
            add(new Object[] { "foo", ParseException.class  });
            add(new Object[] { "12", ParseException.class  });
            
            add(new Object[] { "set ACL on /apps \n remove * for u \n end", null });
            add(new Object[] { "set ACL on /apps \n badkeyword * for u \n end", ParseException.class });
            add(new Object[] { "set ACL on appsWithoutSlash \n remove * for u \n end", ParseException.class  });
            add(new Object[] { "set ACL", ParseException.class  });
            add(new Object[] { "set ACL \n end", ParseException.class  });
            
            add(new Object[] { "create service user bob, alice, tom21", null  });
            add(new Object[] { "create service user bob-221_BOB", null  });
            
            // this passes since introducing "register namespace" and loosening
            // the PRINCIPAL regexp
            add(new Object[] { "create service user bob/221", null  });
            
            add(new Object[] { "create service user bob,/alice, tom21", ParseException.class  });
            add(new Object[] { "create service user bob,alice,tom21 # comment not allowed here", TokenMgrError.class });
            add(new Object[] { "CREATE service user bob, alice, tom21", ParseException.class });
            add(new Object[] { "create SERVICE user bob, alice, tom21", ParseException.class });
            
            // Disable service user with missing reason
            add(new Object[] { "disable service user foo", ParseException.class });
            
            // Quoted strings in disable service user
            add(new Object[] { "disable service user foo", ParseException.class });
            add(new Object[] { "disable service user foo missing colon and quotes", ParseException.class });
            add(new Object[] { "disable service user foo : missing quotes", ParseException.class });
            add(new Object[] { "disable service user foo \"missing colon\"", ParseException.class });
            add(new Object[] { "disable service user foo : missing start quote\"", ParseException.class });
            add(new Object[] { "disable service user foo : \"missing end quote", ParseException.class });
            add(new Object[] { "disable service user foo: \"Unescaped quoted single backslash \"\\\" fails", ParseException.class });

            // SLING-7066 default mixin is not supported
            add(new Object[] { "create path (sling:Folder mixin mix:A) /var/foo", ParseException.class });
            add(new Object[] { "create path (mixin mix:A) /var/foo", ParseException.class });

            // SLING-7061
            add(new Object[] { "set repository ACL for principal1\nallow jcr:somePermission on /\nend", ParseException.class });

            // path must come before password if used
            add(new Object[] { "create user E with password PWD with path P", ParseException.class });

            // SLING-8757 - functions at the beginning of paths
            add(new Object[] { "set ACL on home(missingRParen \n remove * for u \n end", ParseException.class });
            add(new Object[] { "set ACL on spaceAfterFunctionName(user) \n remove * for u \n end", ParseException.class });
            add(new Object[] { "set ACL on one(name)two(onlyOneFunctionAllowed) \n remove * for u \n end", ParseException.class });
            add(new Object[] { "set ACL on home(alice:colonNotAllowed) \n remove * for u \n end", ParseException.class });
            add(new Object[] { "set ACL on home(alice#hashNotAllowed) \n remove * for u \n end", ParseException.class });
            add(new Object[] { "set ACL on home(alice,commaNotAllowed) \n remove * for u \n end", ParseException.class });
            add(new Object[] { "set ACL on home(alice,comma,not,allowed) \n remove * for u \n end", ParseException.class });

            // SLING-9084 - add/remove group members
            add(new Object[] { "add to group missingUsernames", ParseException.class });
            add(new Object[] { "add missingGroupName, another to group", ParseException.class });
            add(new Object[] { "add bob, alice to group only, one, allowed", ParseException.class });
            add(new Object[] { "remove from group missingUsernames", ParseException.class });
            add(new Object[] { "remove missingGroup from group", ParseException.class });
            add(new Object[] { "remove bob, alice from group only, one, really", ParseException.class });
            add(new Object[] { "add bob, alice from group shouldBeToNotFrom", ParseException.class });
            add(new Object[] { "remove bob, alice to group shouldBeFromNotTo", ParseException.class });
            add(new Object[] { "add bob, alice group missingTo", ParseException.class });
            add(new Object[] { "remove bob, alice group missingFrom", ParseException.class });

            // SLING-9171 Support setting node properties via repoinit
            add(new Object[] { "set properties on appsWithoutSlash \n set sling:ResourceType{String} to /x/y/z \n end", ParseException.class });
            add(new Object[] { "set properties on /apps  \n set dob{Date} to 13-10-2019 \n end", ParseException.class });
            add(new Object[] { "set properties on /pathA/b  \n set someProp{inValidType} to abc \n end", ParseException.class });
            add(new Object[] { "set properties on /pathA/b  \n set lowercasetype{string} to abc \n end", ParseException.class });
            add(new Object[] { "set properties on /pathA/b  \n set {String} to missingPropertyName \n end", ParseException.class });
            add(new Object[] { "set properties on /pathA/b  \n set somepProp{String} withoutTo \n end", ParseException.class });
            add(new Object[] { "set properties on /noPropsFails  \n end", ParseException.class });
        }};
        return result;
    }
    
    public ParsingErrorsTest(String input, Class<? extends Throwable> expected) {
        this.input = input;
        this.expected = expected;
    }

    private String getInfo(String msg, Throwable unexpected) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        pw.println("For input '" + input + "', unexpected stack trace=");
        unexpected.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    @Test
    public void checkResult() throws ParseException, IOException {
        final StringReader r = new StringReader(input);
        boolean noException = false;
        try {
            new RepoInitParserImpl(r).parse();
            noException = true;
        } catch(Exception e) {
            assertEquals(getInfo(input, e), expected, e.getClass());
        } catch(Error err) {
            assertEquals(getInfo(input, err), expected, err.getClass());
        } finally {
            r.close();
        }
        
        if(noException && expected != null) {
            fail("Expected a " + expected.getSimpleName() + " for [" + input + "]");
        }
    }
}
