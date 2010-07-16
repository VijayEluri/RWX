/*
 *  Copyright (C) 2010 John Casey.
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.redhat.xmlrpc.impl.stax;

import com.redhat.xmlrpc.error.XmlRpcException;
import com.redhat.xmlrpc.impl.stax.helper.RequestHelper;
import com.redhat.xmlrpc.impl.stax.helper.ResponseHelper;
import com.redhat.xmlrpc.spi.XmlRpcListener;
import com.redhat.xmlrpc.spi.XmlRpcParser;
import com.redhat.xmlrpc.vocab.XmlRpcConstants;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

public class StaxParser
    implements XmlRpcParser
{

    private final XMLInputFactory factory;

    private XMLStreamReader reader;

    public StaxParser( final InputStream in )
        throws XmlRpcException
    {
        factory = XMLInputFactory.newInstance();
        try
        {
            reader = factory.createXMLStreamReader( in );
        }
        catch ( final XMLStreamException e )
        {
            throw new XmlRpcException( "Failed to initialize stream reader: " + e.getMessage(), e );
        }
    }

    public StaxParser( final Reader in )
        throws XmlRpcException
    {
        factory = XMLInputFactory.newInstance();
        try
        {
            reader = factory.createXMLStreamReader( in );
        }
        catch ( final XMLStreamException e )
        {
            throw new XmlRpcException( "Failed to initialize stream reader: " + e.getMessage(), e );
        }
    }

    public StaxParser( final String in )
        throws XmlRpcException
    {
        factory = XMLInputFactory.newInstance();
        try
        {
            reader = factory.createXMLStreamReader( new StringReader( in ) );
        }
        catch ( final XMLStreamException e )
        {
            throw new XmlRpcException( "Failed to initialize stream reader: " + e.getMessage(), e );
        }
    }

    public void parse( final XmlRpcListener listener )
        throws XmlRpcException
    {
        try
        {
            while ( reader.hasNext() )
            {
                final int tag = reader.next();
                if ( tag == XMLStreamReader.START_ELEMENT )
                {
                    final String tagName = reader.getName().getLocalPart();
                    if ( XmlRpcConstants.REQUEST.equals( tagName ) )
                    {
                        RequestHelper.parse( reader, listener );
                    }
                    else if ( XmlRpcConstants.RESPONSE.equals( tagName ) )
                    {
                        ResponseHelper.parse( reader, listener );
                    }
                }
            }
        }
        catch ( final XMLStreamException e )
        {
            throw new XmlRpcException( "Failed to parse input: " + e.getMessage(), e );
        }
    }
}
