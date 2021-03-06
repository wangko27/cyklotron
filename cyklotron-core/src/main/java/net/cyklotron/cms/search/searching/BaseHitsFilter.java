package net.cyklotron.cms.search.searching;

import java.util.HashMap;

import org.objectledge.coral.entity.EntityDoesNotExistException;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.coral.store.Resource;
import org.objectledge.table.TableFilter;

import net.cyklotron.cms.search.searching.cms.LuceneSearchHit;

/**
 * This is a base filter for filtering search results upon their visibility based on branches permission
 * assignments.
 *
 * @author <a href="mailto:dgajda@caltha.pl">Damian Gajda</a>
 * @version $Id: BaseHitsFilter.java,v 1.7 2007-11-18 21:23:29 rafal Exp $
 */
public abstract class BaseHitsFilter<T extends SearchHit> 
    implements TableFilter<T>
{
    private HashMap<String, Boolean> branchAccessCache = new HashMap<String, Boolean>();

    protected CoralSession coralSession;
    
    public BaseHitsFilter(CoralSession coralSession)
    {
        this.coralSession = coralSession;
    }

    public boolean accept(T hit)
    {
        if(hit instanceof LuceneSearchHit)
        {
            String branchId = hit.get("branch_id");
            if(!branchAccessCache.containsKey(branchId))
            {
                try
                {
                    Resource branch = coralSession.getStore().getResource(Long.parseLong(branchId));
                    branchAccessCache.put(branchId, Boolean.valueOf(checkAccess(branch)));
                }
                catch(EntityDoesNotExistException e)
                {
                    // WARN: Maybe we should log it!!
                    branchAccessCache.put(branchId, Boolean.FALSE);
                }
            }
            return branchAccessCache.get(branchId);
        }
        else
        {
            return true;
        }
    }
    
    public abstract boolean checkAccess(Resource branch);
}
