package com.xerox.amazonws.simpledb;

import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;

/**
 * This interface describes calls that the Domain will make into a caching
 * system. For now, only items are cached by id.
 */
public class SimpleItemCache implements ItemCache {
	// this is thread safe
	private Hashtable<String, Item> _cache = new Hashtable<String, Item>();

	/**
	 * This retrieves an item from the cache. A null is returned if the item
	 * is not cachced.
	 *
	 * @param id the identifier for the item being retrieved
	 * @return the item found (or null)
	 */
	public Item getItem(String id) {
		return _cache.get(id);
	}

	/**
	 * Stores an item in the cache.
	 *
	 * @param i the item to be stored
	 */
	public void putItem(Item i) {
		_cache.put(i.getIdentifier(), i);
	}

	/**
	 * Removes an item from the cache.
	 *
	 * @param id the identifier for the item being removed
	 */
	public void removeItem(String id) {
		_cache.remove(id);
	}

	/**
	 * Retrieves a complete list of items in the cache
	 */
	public List<Item> itemSet() {
		return new ArrayList<Item>(_cache.values());
	}

	/**
	 * Clears the cache. This would be used to ensure only new data is being fetched.
	 */
	public void clear() {
		_cache.clear();
	}
}
