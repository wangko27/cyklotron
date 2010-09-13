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

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jcontainer.dna.Configuration;
import org.jcontainer.dna.Logger;
import org.objectledge.filesystem.FileSystem;
import org.objectledge.filesystem.UnsupportedCharactersInFilePathException;
import org.picocontainer.Startable;

import net.cyklotron.cms.files.util.CSVFileReader;

/**
 * An implementation of <code>related.relationships</code> Coral resource class.
 * 
 * @author Coral Maven plugin
 */
public class PnaDatabaseServiceImpl
    implements PnaDatabaseService, Startable
{
    private Logger logger;

    private String dataSourcePath;

    private String dataLocalDir;

    private FileSystem fileSystem;

    private final Map<String, Set<Pna>> pnaByProvince = new HashMap<String, Set<Pna>>();

    private final Map<String, Set<Pna>> pnaByCity = new HashMap<String, Set<Pna>>();

    private final Map<String, Set<Pna>> pnaByPostCode = new HashMap<String, Set<Pna>>();

    public PnaDatabaseServiceImpl(Configuration config, Logger logger, FileSystem fileSystem)
    {
        this.logger = logger;
        this.dataSourcePath = config.getChild("data_source_path").getValue("");
        this.dataLocalDir = config.getChild("data_local_dir").getValue("/ngo/database");
        this.fileSystem = fileSystem;
    }

    public void downloadSource()
        throws IOException
    {
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(dataSourcePath);
        client.executeMethod(method);
        String sourcePdfPath = dataLocalDir + "/spispna.pdf";
        String sourceTmpPath = sourcePdfPath + ".tmp";
        try
        {
            if(!fileSystem.isDirectory(dataLocalDir))
            {
                fileSystem.mkdirs(dataLocalDir);
            }
            fileSystem.write(sourceTmpPath, method.getResponseBodyAsStream());
            method.releaseConnection();
            fileSystem.rename(sourceTmpPath, sourcePdfPath);
        }
        catch(UnsupportedCharactersInFilePathException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void parseSource()
        throws Exception
    {
        PNASourceParser parser = new PNASourceParser(fileSystem, logger);
        parser.parse(dataLocalDir + "/spispna.pdf");
        List<String[]> content = parser.getContent();
        String interimCsvPath = dataLocalDir + "/spispna.csv";
        String interimTmpPath = interimCsvPath + ".tmp";
        Writer writer = fileSystem.getWriter(interimTmpPath, "UTF-8");
        PNASourceParser.dump(Collections.singletonList(parser.getHeadings()), writer);
        PNASourceParser.dump(content, writer);
        writer.close();
        try
        {
            fileSystem.rename(interimTmpPath, interimCsvPath);
        }
        catch(UnsupportedCharactersInFilePathException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update()
    {
        try
        {
            if(!fileSystem.isFile(dataLocalDir + "/spispna.pdf"))
            {
                downloadSource();
                parseSource();
            }
            pnaByProvince.clear();
            pnaByCity.clear();
            pnaByPostCode.clear();
            CSVFileReader csvReader = new CSVFileReader(fileSystem.getInputStream(dataLocalDir
                + "/spispna.csv"), "UTF-8", ';');
            Map<String, String> line = csvReader.getNextLine();
            while(line != null)
            {
                addPna(line.get("Województwo"), line.get("Miejscowość"), line.get("Ulica"),
                    line.get("PNA"));
                line = csvReader.getNextLine();
            }
        }
        catch(IOException e)
        {
            logger.info("Could not read ngo data file " + e.getMessage());
        }
        catch(Exception e)
        {
            logger.info("Could not read ngo data file " + e.getMessage());
        }
    }

    private void addPna(String province, String city, String street, String postCode)
    {
        Pna pna = new Pna(province, city, street, postCode);
        add(pna, province, pnaByProvince);
        add(pna, city, pnaByCity);
        add(pna, postCode, pnaByPostCode);
    }

    private void add(Pna item, String key, Map<String, Set<Pna>> map)
    {
        Set<Pna> set = map.get(key);
        if(set == null)
        {
            set = new HashSet<Pna>();
            map.put(key, set);
        }
        set.add(item);
    }

    @Override
    public Set<Pna> getPnaSetByPostCode(String postCode)
    {
        return pnaByPostCode.get(postCode);
    }

    @Override
    public Set<Pna> getPnaSetByCity(String city)
    {
        return pnaByCity.get(city);
    }

    @Override
    public Set<Pna> getPnaSetByProvince(String area)
    {
        return pnaByProvince.get(area);
    }

    @Override
    public void start()
    {
        update();
    }

    @Override
    public void stop()
    {

    }
}
