package de.heinfricke.countriesmapper;

import java.util.ArrayList;
import java.util.List;

public class ComputedListFilter {

	private BigLoadComputingProcessor processor;

	public ComputedListFilter(BigLoadComputingProcessor processor) {
		this.processor = processor;
	}
	
	public List<String> filterByHardcodedValue(String value) {
		List<String> list = processor.getListOfElements();
		
		List<String> result = new ArrayList<String>();
		for (String element : list) {
			if (element.equals(value)) {
				result.add(element);
			}
		}
		
		return result;
	}
	
}
