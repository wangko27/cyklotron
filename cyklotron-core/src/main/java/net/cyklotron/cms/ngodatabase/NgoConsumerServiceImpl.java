// 
// Copyright (c) 2004, Caltha - Gajda, Krzewski, Mach, Potempski Sp.J. 
// All rights reserved. 
// 
// Redistribution and use in source and binary forms, with or without modification,  
// are permitted provided that the following conditions are met: 
// 
// * Redistributions of source code must retain the above copyright notice,  
//       this list of conditions and the following disclaimer. 
// * Redistributions in binary form must reproduce the above copyright notice,  
//       this list of conditions and the following disclaimer in the documentation  
//       and/or other materials provided with the distribution. 
// * Neither the name of the Caltha - Gajda, Krzewski, Mach, Potempski Sp.J.  
//       nor the names of its contributors may be used to endorse or promote products  
//       derived from this software without specific prior written permission. 
// 
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"  
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED  
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
// IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,  
// INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,  
// BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
// OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,  
// WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)  
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE  
// POSSIBILITY OF SUCH DAMAGE. 
// 

package net.cyklotron.cms.ngodatabase;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.jcontainer.dna.Logger;
import org.picocontainer.Startable;

import net.cyklotron.cms.ngodatabase.jms.DummyMessageListener;
import net.cyklotron.cms.ngodatabase.jms.MessagingConnectionProviderImpl;

public class NgoConsumerServiceImpl
    implements NgoConsumerService, Startable
{

    private static String brokerQueue = "TESTQUEUE";

    private Logger logger;

    private final Session jmsSession;

    private final Connection connection;

    public NgoConsumerServiceImpl(Logger logger, MessagingConnectionProviderImpl ngoConnectionProvider)
        throws Exception
    {
        this.logger = logger;
        this.connection = ngoConnectionProvider.createConnection("TEST", Connection.class);
        this.jmsSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    @Override
    public void start()
    {
        try
        {
            DummyMessageListener messageListener = new DummyMessageListener(logger);
            connection.setExceptionListener(messageListener);
            connection.start();

            Destination destination = jmsSession.createQueue(brokerQueue);
            MessageConsumer messageConsumer = jmsSession.createConsumer(destination);
            messageConsumer.setMessageListener(messageListener);

        }
        catch(JMSException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void stop()
    {
        try
        {
            if(jmsSession != null)
            {
                jmsSession.close();
            }
            if(connection != null)
            {
                connection.close();
            }
        }
        catch(JMSException e)
        {
            e.printStackTrace();
        }

    }

}
