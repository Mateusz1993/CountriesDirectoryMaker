package de.heinfricke.countriesmapper.fileoperations;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.preparer.GroupOfCountries;
import de.heinfricke.countriesmapper.utils.UserInputs;
import de.heinfricke.countriesmapper.utils.UserInputs.DirectoriesActivity;

@RunWith(MockitoJUnitRunner.class)
public class FileDeleterTest {
	
	@Mock
	private File file;
	
	@Mock
	private File childFile;
	
	@Mock
	private UserInputs userInputs;
	
	private FileDeleter fileDeleter;
	
	@Mock
	private FileDeleter fileDeleterTest;
	
	@Mock
	private File secondChildFile;
	
	@Before
	public void setUp() {
		fileDeleter = new FileDeleter(userInputs) {
			protected File createFile(String pathOfGorupDirectory) {
				return file;
			};
		};
	}
	
	@Ignore
	@Test
	public void test() {
		File[] fileChildrenEmpty = new File[0];
		File[] fileChildrenWithMockChild = new File[] { childFile };
		doReturn(true).when(file).isDirectory();
		doReturn(fileChildrenWithMockChild).doReturn(fileChildrenEmpty).when(file).listFiles();
		
		doReturn(DirectoriesActivity.DELETE).when(userInputs).userDecisionAboutDirectories();
		String path = "somepath";
		
		List<GroupOfCountries> groupOfCountries = new ArrayList<GroupOfCountries>();
		List<Country> countries = new ArrayList<Country>();
		countries.add(new Country("Albania"));
		countries.add(new Country("Belgium"));
		GroupOfCountries group = new GroupOfCountries("ABC", countries);
		groupOfCountries.add(group);
		
		fileDeleter.deleteDirectories(groupOfCountries, path);
		
		verify(childFile, times(1)).delete();
	}
	
	@Ignore
	@Test
	public void testMultipleGroupsReplace() throws IOException {
		
		final File directoryAbc = mock(File.class);
		final File directoryDef = mock(File.class);
		final File directoryPqr = mock(File.class);
		
		FileDeleter fileDeleter = new FileDeleter(userInputs) {
			protected File createFile(String pathOfGorupDirectory) {
				if (pathOfGorupDirectory.endsWith("ABC")) {
					return directoryAbc;
				} else if (pathOfGorupDirectory.endsWith("DEF")) {
					return directoryDef;
				} else if (pathOfGorupDirectory.endsWith("PQR")) {
					return directoryPqr;
				} else {
					throw new IllegalStateException();
				}
			};
		};
		
		File[] fileChildrenEmpty = new File[0];
		File[] fileChildrenWithMockChild = new File[] { childFile };
		
		doReturn(true).when(file).isDirectory();
		doReturn(fileChildrenWithMockChild).doReturn(fileChildrenEmpty).when(file).listFiles();
		
		doReturn(DirectoriesActivity.REPLACE).when(userInputs).userDecisionAboutDirectories();
		String path = "somepath";
		
		//Zrób 2 grupy z państwami
		List<GroupOfCountries> groupOfCountries = new ArrayList<GroupOfCountries>();
		
		List<Country> countries = new ArrayList<Country>();
		countries.add(new Country("Albania"));
		countries.add(new Country("Belgium"));
		GroupOfCountries firstGroup = new GroupOfCountries("ABC", countries);
		groupOfCountries.add(firstGroup);
		
		countries = new ArrayList<Country>();
		countries.add(new Country("Poland"));
		countries.add(new Country("Portugal"));
		countries.add(new Country("Romania"));
		GroupOfCountries secondGroup = new GroupOfCountries("PQR", countries);
		groupOfCountries.add(secondGroup);
		
		//Napisz test, który sprawdzi że usunięte zostały tylko te kraje które występują w podanych grupach
		fileDeleter.deleteDirectories(groupOfCountries, path);
		
		verify(directoryAbc, times(1)).delete();
		verify(directoryDef, times(0)).delete();
		verify(directoryPqr, times(1)).delete();
	}
	
	@Test
	public void deleteDirectoriesTest(){
		File[] fileChildrenEmpty = new File[0];
		File[] fileChildrenWithMockChild = new File[] { childFile };
		File[] fileChildrenWithSecondMockChild = new File[] { secondChildFile };
		
		doReturn(true).when(file).isDirectory();
		doReturn(false).when(childFile).delete();
		doReturn(true).when(secondChildFile).delete();
		doReturn(fileChildrenWithSecondMockChild).doReturn(fileChildrenWithMockChild).doReturn(fileChildrenEmpty).when(file).listFiles();
		
		doReturn(DirectoriesActivity.REPLACE).when(userInputs).userDecisionAboutDirectories();
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
		countries.add(new Country("Romania"));
		GroupOfCountries secondGroup = new GroupOfCountries("PQR", countries);
		groupOfCountries.add(secondGroup);
		
		countries = new ArrayList<Country>();
		countries.add(new Country("Greece"));
		countries.add(new Country("Israel"));
		GroupOfCountries thirdGroup = new GroupOfCountries("GHI", countries);
		groupOfCountries.add(thirdGroup);
		
		fileDeleter.deleteDirectories(groupOfCountries, path);
		
		verify(childFile, times(1)).delete();
		verify(secondChildFile, times(1)).delete();
	}
}
