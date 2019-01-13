package com.analyzer.services.datastore;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DatastoreOperationsTest {
	
	@Test
	public void validFilters()
	{
		assertTrue(DatastoreOperations.isInValidFilters(" ", ""));
		assertTrue(DatastoreOperations.isInValidFilters(null, ""));
		assertTrue(DatastoreOperations.isInValidFilters("test", ""));
		assertTrue(DatastoreOperations.isInValidFilters("", "test"));
		assertFalse(DatastoreOperations.isInValidFilters("test1", "test2"));
	}

}
