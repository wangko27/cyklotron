package net.cyklotron.cms.modules.views.documents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.jcontainer.dna.Logger;
import org.objectledge.context.Context;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.coral.store.Resource;
import org.objectledge.forms.FormsService;
import org.objectledge.i18n.I18nContext;
import org.objectledge.parameters.Parameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.table.TableStateManager;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.web.HttpContext;
import org.objectledge.web.mvc.MVCContext;


import net.cyklotron.cms.CmsDataFactory;
import net.cyklotron.cms.documents.DocumentService;
import net.cyklotron.cms.documents.FooterResource;
import net.cyklotron.cms.documents.keywords.KeywordResource;
import net.cyklotron.cms.documents.table.FooterSequenceComparator;
import net.cyklotron.cms.integration.IntegrationService;
import net.cyklotron.cms.preferences.PreferencesService;
import net.cyklotron.cms.related.RelatedService;
import net.cyklotron.cms.site.SiteResource;
import net.cyklotron.cms.site.SiteService;
import net.cyklotron.cms.structure.StructureService;
import net.cyklotron.cms.style.StyleService;

/**
 *
 */
public class KeywordsList
    extends BaseDocumentScreen
{
    
    public KeywordsList(Context context, Logger logger,
        PreferencesService preferencesService, CmsDataFactory cmsDataFactory,
        TableStateManager tableStateManager, StructureService structureService,
        StyleService styleService, SiteService siteService, RelatedService relatedService,
        DocumentService documentService, IntegrationService integrationService,
        FormsService formsService)
    {
        super(context, logger, preferencesService, cmsDataFactory, tableStateManager,
                        structureService, styleService, siteService, relatedService,
                        documentService, integrationService);
    }
    
    public void process(Parameters parameters, MVCContext mvcContext, TemplatingContext templatingContext, HttpContext httpContext, I18nContext i18nContext, CoralSession coralSession)
        throws ProcessingException
    {
        try
        {
            SiteResource site = getSite();
            Resource root = documentService.getKeywordsRoot(coralSession, site);
            Resource[] resources = coralSession.getStore().getResource(root);
            List keywords = new ArrayList();
            HashMap keywordsMap = new HashMap();
            for(int i = 0; i < resources.length; i++)
            {
                if(resources[i] instanceof KeywordResource)
                {
                	keywords.add(((KeywordResource)resources[i]));
                }
            }
            templatingContext.put("keywords", keywords);
        }
        catch(Exception e)
        {
            throw new ProcessingException("failed to lookup resource", e);
        }
    }
}
