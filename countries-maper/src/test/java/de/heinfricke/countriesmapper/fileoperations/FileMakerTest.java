package de.heinfricke.countriesmapper.fileoperations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.preparer.GroupOfCountries;

public class FileMakerTest {

	@Mock
	private File file;
	
	@Test
	public void test() {
		final File directoryAbc = mock(File.class);
		final File directoryDef = mock(File.class);
		final File directoryPqr = mock(File.class);
		final File directoryAlbania = mock(File.class);
		final File directoryBelgium = mock(File.class);
		final File directoryPoland = mock(File.class);
		final File directoryPortugal = mock(File.class);
		final File directoryRomonia = mock(File.class);
		
		FileMaker fileMaker = new FileMaker() {
			protected File createFile(String pathOfGorupDirectory) {
				if (pathOfGorupDirectory.endsWith("ABC")) {
					return directoryAbc;
				} else if (pathOfGorupDirectory.endsWith("DEF")) {
					return directoryDef;
				} else if (pathOfGorupDirectory.endsWith("PQR")) {
					return directoryPqr;
				} else if (pathOfGorupDirectory.endsWith("Albania")) {
					return directoryAlbania;
				} else if (pathOfGorupDirectory.endsWith("Belgium")) {
					return directoryBelgium;
				} else if (pathOfGorupDirectory.endsWith("Poland")) {
					return directoryPoland;
				} else if (pathOfGorupDirectory.endsWith("Portugal")) {
					return directoryPortugal;
				} else if (pathOfGorupDirectory.endsWith("Romania")) {
					return directoryRomonia;
				} else {
					throw new IllegalStateException();
				}
			};
		};
		
		String path = "somepath";
		List<GroupOfCountries> groupOfCountries = new ArrayList<GroupOfCountries>();
		
		List<Country> countries = new ArrayList<Country>();
		countries.add(new Country("Albania"));
		countries.add(new Country("Belgium"));
		GroupOfCountries firstGroup = new GroupOfCountries("ABC", countries);
		groupOfCountries.add(firstGroup);
		
		countries = new ArrayList<Country>();
		countries.add(new Country("Poland"));
		countries.add(new Country("Portugal"));
		GroupOfCountries secondGroup = new GroupOfCountries("PQR", countries);
		groupOfCountries.add(secondGroup);
		
		fileMaker.createFiles(groupOfCountries, path);

		verify(directoryAbc, times(1)).mkdirs();
		verify(directoryDef, times(0)).mkdirs();
		verify(directoryPqr, times(1)).mkdirs();
		verify(directoryAlbania, times(1)).mkdirs();
		verify(directoryBelgium, times(1)).mkdirs();
		verify(directoryPoland, times(1)).mkdirs();
		verify(directoryPortugal, times(1)).mkdirs();
		verify(directoryRomonia, times(0)).mkdirs();
	}
}
