package de.heinfricke.countriesmapper;

import java.util.Arrays;
import java.util.List;

public class BigLoadComputingProcessor {

	public List<String> getListOfElements() {
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return Arrays.asList("dupa", "żopa", "zad");
	}
}
