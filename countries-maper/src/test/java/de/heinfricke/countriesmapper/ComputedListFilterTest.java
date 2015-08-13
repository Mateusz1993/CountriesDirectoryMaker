package de.heinfricke.countriesmapper;

import java.util.List;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ComputedListFilterTest {

	@Mock
	private BigLoadComputingProcessor processor;
	
	@InjectMocks
	private ComputedListFilter filter;
	
	@Test
	public void testFilter() {
		
		List<String> processorResults = Arrays.asList("dupa", "blada", "ze", "az", "strach");
		
//		doCallRealMethod().when(processor).getListOfElements();
		
		doReturn(processorResults).when(processor).getListOfElements();
		
		List<String> results = filter.filterByHardcodedValue("dupa");
		
		assertThat(results, is(notNullValue()));
		assertThat(results.size(), is(1));
		assertThat(results.get(0), is("dupa"));
	}
	
}
