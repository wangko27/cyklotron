package net.cyklotron.cms.aggregation.util;

import java.util.Locale;

import org.objectledge.table.comparator.BaseStringComparator;

import net.cyklotron.cms.aggregation.RecommendationResource;

/**
 * This is a comparator for comparing source resource names.
 *
 * @author <a href="mailto:pablo@caltha.pl">Pawel Potempski</a>
 * @version $Id: SourceNameComparator.java,v 1.4 2005-02-15 17:31:48 rafal Exp $
 */
public class SourceNameComparator
    extends BaseStringComparator
{
    public SourceNameComparator(Locale locale)
    {
        super(locale);
    }
    
    public int compare(Object o1, Object o2)
    {
        if(!((o1 instanceof RecommendationResource && o2 instanceof RecommendationResource )))
        {
            return 0;
        }
        RecommendationResource r1 = (RecommendationResource)o1;
        RecommendationResource r2 = (RecommendationResource)o2;
        return compareStrings(r1.getSource().getName(), r2.getSource().getName());
    }
}