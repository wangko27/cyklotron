package net.cyklotron.cms.search.analysis;

/**
 * Copied from Lucene 4.0.0 sources. Origin was StandardTokenizerFactory
 */
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.Reader;
import java.util.Map;

import org.apache.lucene.analysis.util.TokenizerFactory;

/**
 * Factory for {@link TextTokenizer}.
 * 
 * <pre class="prettyprint" >
 * &lt;fieldType name="text_stndrd" class="solr.TextField" positionIncrementGap="100"&gt;
 *   &lt;analyzer&gt;
 *     &lt;tokenizer class="solr.StandardTokenizerFactory" maxTokenLength="255"/&gt;
 *   &lt;/analyzer&gt;
 * &lt;/fieldType&gt;
 * </pre>
 */

public class TextTokenizerFactory
    extends TokenizerFactory
{

    private int maxTokenLength;

    @Override
    public void init(Map<String, String> args)
    {
        super.init(args);
        assureMatchVersion();
        maxTokenLength = getInt("maxTokenLength", TextAnalyzer.DEFAULT_MAX_TOKEN_LENGTH);
    }

    public TextTokenizer create(Reader input)
    {
        TextTokenizer tokenizer = new TextTokenizer(luceneMatchVersion, input);
        tokenizer.setMaxTokenLength(maxTokenLength);
        return tokenizer;
    }
}
