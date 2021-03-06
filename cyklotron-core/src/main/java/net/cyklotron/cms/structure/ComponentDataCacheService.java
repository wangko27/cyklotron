package net.cyklotron.cms.structure;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jcontainer.dna.Logger;
import org.objectledge.ComponentInitializationError;
import org.objectledge.cache.CacheFactory;
import org.objectledge.context.Context;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.threads.Task;
import org.objectledge.threads.ThreadPool;

import net.cyklotron.cms.CmsData;

public class ComponentDataCacheService
{
    private final Map<String, String> sharedKeys = new HashMap<String, String>();

    private final Map<String, CacheEntry<?>> dataCache;

    private static final String CACHE_NAME = "componentData";

    private static final long CACHE_EXPUNGE_INTERVAL = 60 * 1000l; // 1 minute

    public ComponentDataCacheService(CacheFactory cacheFactory, ThreadPool threadPool, Logger log)
    {
        try
        {
            dataCache = cacheFactory.getInstance(CACHE_NAME, CACHE_NAME);
        }
        catch(Exception e)
        {
            throw new ComponentInitializationError("componentData cache not configured");
        }
        threadPool.runDaemon(new ResultsCacheCleaner(dataCache, log));
    }

    // component data caching

    private String getKey(NavigationNodeResource node, String componentInstance, String id)
    {
        StringBuilder buff = new StringBuilder();
        if(node != null)
        {
            buff.append(node.getIdString());
        }
        else
        {
            buff.append("global");
        }
        buff.append('.').append(componentInstance);
        if(id != null && id.length() > 0)
        {
            buff.append('.').append(id);
        }            
        return buff.toString();
    }

    private String getKey(CmsData cmsData, String id)
    {
        return getKey(cmsData.getNode(), cmsData.getComponent().getInstanceName(), id);
    }

    /**
     * Returns synchronization guard object. This object should be used for synchronizing thread
     * operations involving component data cache.
     * @param id TODO
     * @param key thread local key
     * 
     * @return synchronization guard object.
     */
    public Object getGuard(CmsData cmsData, String id)
    {
        String key = getKey(cmsData, id);
        String sharedKey = null;
        synchronized(sharedKeys)
        {
            sharedKey = (String)sharedKeys.get(key);
            if(sharedKey == null)
            {
                sharedKey = key;
                sharedKeys.put(key, key);
            }
        }
        return sharedKey;
    }

    /**
     * Returns cached component data.
     * 
     * @param <T> type of data, application dependent.
     * @param cmsData CmsData, identifying a component.
     * @param id identifier of the specific data item, null when not used.
     * @return cached data, or null when not available.
     */
    public <T> T getCachedData(CmsData cmsData, String id)
    {
        String key = getKey(cmsData, id);
        if(dataCache == null)
        {
            // caching disabled
            return null;
        }
        else
        {
            synchronized(dataCache)
            {
                @SuppressWarnings("unchecked")
                CacheEntry<T> entry = (CacheEntry<T>)dataCache.get(key);
                long currentTimeMillis = System.currentTimeMillis();
                if(entry != null && entry.isValid(currentTimeMillis))
                {
                    return entry.data;
                }
                else
                {
                    return null;
                }
            }
        }
    }

    /**
     * Stores cached component data.
     * 
     * @param <T> type of data, application dependent.
     * @param cmsData CmsData, identifying a component.
     * @param id identifier of the specific data item, null when not used.
     * @param cacheInterval cache interval in seconds.
     * @param data cached data.
     */
    public <T> void setCachedData(CmsData cmsData, String id, T results, int cacheInterval)
    {
        String key = getKey(cmsData, id);
        if(dataCache != null)
        {
            synchronized(dataCache)
            {
                CacheEntry<T> entry = new CacheEntry<T>(results, System.currentTimeMillis()
                    + cacheInterval * 1000l);
                dataCache.put(key, entry);
            }
        }
    }

    /**
     * Clears cached component data.
     * 
     * @param node navigation node.
     * @param componentInstance component instance name.
     */
    public void clearCachedData(NavigationNodeResource node, String componentInstance)
    {
        String key = getKey(node, componentInstance, null);
        if(dataCache != null)
        {
            synchronized(dataCache)
            {
                Iterator<Map.Entry<String,CacheEntry<?>>> i = dataCache.entrySet().iterator();
                while(i.hasNext())
                {
                    if(i.next().getKey().startsWith(key))
                    {
                        i.remove();
                    }
                }
            }
        }
    }

    public static class CacheEntry<T>
    {
        final private T data;

        final private long expiryTime;

        public CacheEntry(T data, long expiryTime)
        {
            this.data = data;
            this.expiryTime = expiryTime;
        }

        public boolean isValid(long now)
        {
            return expiryTime > now;
        }
        
        public int size()
        {
            if(data.getClass().isArray())
            {
                return Array.getLength(data);
            }
            if(data instanceof Collection)
            {
                return ((Collection<?>)data).size();
            }
            return 0;
        }
    }

    private static class ResultsCacheCleaner
        extends Task
    {
        private final Map<String, CacheEntry<?>> cache;

        private final Logger log;

        private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        public ResultsCacheCleaner(Map<String, CacheEntry<?>> cache, Logger log)
        {
            this.cache = cache;
            this.log = log;
        }

        public String getName()
        {
            return "CategoryQuery results cache cleaner";
        }

        @Override
        public void process(Context context)
            throws ProcessingException
        {
            log.debug("Cleaner thread started");
            while(!Thread.interrupted())
            {
                synchronized(this)
                {
                    try
                    {
                        wait(CACHE_EXPUNGE_INTERVAL);
                    }
                    catch(InterruptedException e)
                    {
                        return;
                    }
                }
                long now = System.currentTimeMillis();
                log.debug("woke up " + dateFormat.format(new Date(now)));
                synchronized(cache)
                {
                    Iterator<Map.Entry<String, CacheEntry<?>>> i = cache.entrySet().iterator();
                    while(i.hasNext())
                    {
                        Map.Entry<String, CacheEntry<?>> entry = i.next();
                        log.debug(entry.getKey() + " will exipre at "
                            + dateFormat.format(new Date(entry.getValue().expiryTime)));
                        if(!entry.getValue().isValid(now))
                        {
                            i.remove();
                            log.debug(entry.getKey() + " expired at "
                                + dateFormat.format(new Date(entry.getValue().expiryTime))
                                + ", removed");
                        }
                        else
                        {
                            log.debug(entry.getKey() + " will exipre at "
                                + dateFormat.format(new Date(entry.getValue().expiryTime)));
                        }
                    }
                }
            }
        }
    }
}
