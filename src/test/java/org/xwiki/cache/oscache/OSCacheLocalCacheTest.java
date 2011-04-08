/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.cache.oscache;

import org.junit.Assert;
import org.junit.Test;
import org.xwiki.cache.Cache;
import org.xwiki.cache.CacheFactory;
import org.xwiki.cache.config.CacheConfiguration;
import org.xwiki.cache.eviction.LRUEvictionConfiguration;
import org.xwiki.cache.tests.AbstractGenericTestCache;
import org.xwiki.component.manager.ComponentLookupException;

public class OSCacheLocalCacheTest extends AbstractGenericTestCache
{
    public OSCacheLocalCacheTest()
    {
        this("oscache/local");
    }

    protected OSCacheLocalCacheTest(String roleHint)
    {
        super(roleHint);
    }

    // ///////////////////////////////////////////////////////::
    // Tests

    @Test
    public void testCreateAndDestroyCacheLRUMaxEntries() throws ComponentLookupException, Exception
    {
        CacheFactory factory = getCacheFactory();

        CacheConfiguration conf = new CacheConfiguration();
        LRUEvictionConfiguration lec = new LRUEvictionConfiguration();
        lec.setMaxEntries(1);
        conf.put(LRUEvictionConfiguration.CONFIGURATIONID, lec);

        Cache<Object> cache = factory.newCache(conf);

        Assert.assertNotNull(cache);

        cache.set(KEY, VALUE);

        Assert.assertEquals(VALUE, cache.get(KEY));

        cache.set(KEY2, VALUE2);

        Assert.assertNull(cache.get(KEY));
        Assert.assertEquals(VALUE2, cache.get(KEY2));

        cache.dispose();
    }

    @Test
    public void testCreateAndDestroyCacheLRUTimeToLive() throws ComponentLookupException, Exception
    {
        CacheFactory factory = getCacheFactory();

        CacheConfiguration conf = new CacheConfiguration();
        LRUEvictionConfiguration lec = new LRUEvictionConfiguration();
        lec.setTimeToLive(1);
        conf.put(LRUEvictionConfiguration.CONFIGURATIONID, lec);

        Cache<Object> cache = factory.newCache(conf);

        Assert.assertNotNull(cache);

        cache.set(KEY, VALUE);

        Assert.assertEquals(VALUE, cache.get(KEY));

        Thread.sleep(1000);

        Assert.assertNull(cache.get(KEY));

        cache.dispose();
    }

    @Test
    public void testCreateAndDestroyCacheLRUAll() throws ComponentLookupException, Exception
    {
        CacheFactory factory = getCacheFactory();

        CacheConfiguration conf = new CacheConfiguration();
        LRUEvictionConfiguration lec = new LRUEvictionConfiguration();
        lec.setMaxEntries(1);
        lec.setTimeToLive(1);
        conf.put(LRUEvictionConfiguration.CONFIGURATIONID, lec);

        Cache<Object> cache = factory.newCache(conf);

        Assert.assertNotNull(cache);

        cache.set(KEY, VALUE);

        Assert.assertEquals(VALUE, cache.get(KEY));

        cache.set(KEY2, VALUE2);

        Assert.assertNull(cache.get(KEY));
        Assert.assertEquals(VALUE2, cache.get(KEY2));

        Thread.sleep(1000);

        Assert.assertNull(cache.get(KEY));
        Assert.assertNull(cache.get(KEY2));

        cache.dispose();
    }
}
