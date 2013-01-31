package net.cyklotron.cms.search.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.Validate;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.jcontainer.dna.Logger;
import org.objectledge.coral.session.CoralSessionFactory;
import org.objectledge.coral.store.Resource;
import org.objectledge.filesystem.FileSystem;
import org.objectledge.filesystem.LocalFileSystemProvider;

import net.cyklotron.cms.search.analysis.AnalyzerProvider;

class GenericIndexFactoryImpl
    implements GenericIndexFactory
{

    private FileSystem fileSystem;

    private Logger logger;

    private IndexInitializer indexInitializator;

    GenericIndexFactoryImpl(FileSystem fileSystem, Logger logger, CoralSessionFactory coralSessionFactory,
        IndexInitializer indexInitializator)
    {
        this.fileSystem = fileSystem;
        this.logger = logger;
        this.indexInitializator = indexInitializator;
    }

    @Override
    public GenericIndex<? extends Resource> createOrOpenIndex(String pathToDirectory,
        FromDocumentMapper<? extends Resource> fromDocumentMapper,
        ToDocumentMapper<? extends Resource> toDocumentMapper,
        ResourceProvider<? extends Resource> resourceProvider)
        throws IOException
    {
        validateNotNull(pathToDirectory, fromDocumentMapper, toDocumentMapper);

        return createOrOpenIndex(pathToDirectory, fromDocumentMapper, toDocumentMapper,
            resourceProvider,
            AnalyzerProvider.DEFAULT_PROVIDER);
    }

    @Override
    public GenericIndex<? extends Resource> createOrOpenIndex(String pathToDirectory,
        FromDocumentMapper<? extends Resource> fromDocumentMapper,
        ToDocumentMapper<? extends Resource> toDocumentMapper,
        ResourceProvider<? extends Resource> resourceProvider, AnalyzerProvider analyzerProvider)
        throws IOException
    {
        validateNotNull(pathToDirectory, fromDocumentMapper, toDocumentMapper);
        Validate.notNull(analyzerProvider);

        File indexLocation = ((LocalFileSystemProvider)fileSystem.getProvider("local"))
            .getFile(pathToDirectory);

        Directory directory = new NIOFSDirectory(indexLocation);
        indexInitializator.forceCreateOrOpenIndex(directory);

        return new GenericIndex(fileSystem, logger, pathToDirectory,
            analyzerProvider, fromDocumentMapper, toDocumentMapper, resourceProvider, directory);
    }

    private void validateNotNull(String pathToDirectory,
        FromDocumentMapper<? extends Resource> fromDocumentMapper,
        ToDocumentMapper<? extends Resource> toDocumentMapper)
    {
        Validate.notBlank(pathToDirectory);
        Validate.notNull(fromDocumentMapper);
        Validate.notNull(toDocumentMapper);
    }

}
