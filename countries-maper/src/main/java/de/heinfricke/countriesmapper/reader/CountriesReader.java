package de.heinfricke.countriesmapper.reader;

//////////////////////////////////////////////////////////////
//															//
//															//
//															//
//															//
//															//
//															//
//			     BETTER DON'T READ THIS CLASS				//
//				TOO MANY COMMENTS, ENETERS, ETC				//
//															//
//															//
//															//
//															//
//															//
//															//
//////////////////////////////////////////////////////////////

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jackson.map.DeserializationConfig;
import com.sun.jersey.api.client.*;
import de.heinfricke.countriesmapper.country.*;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * This class contains methods which are used to read files's contents.
 * 
 * @author mateusz
 *
 */
public class CountriesReader {
	/**
	 * This method reads list of countries from file, makes new 'Country'
	 * objects where each one contains name of country and returns these objects
	 * as Set.
	 * 
	 * @param path
	 *            Path to file with list of countries.
	 * @return Set of 'Country' objects.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws JSONException
	 */
	public Set<Country> readCountries(String path, boolean prepareInfromationFile)
			throws FileNotFoundException, IOException, JSONException, RuntimeException {
		System.out.println("\nYour path to .txt file is: " + path);

		Set<Country> namesOfCountries = new TreeSet<Country>();

		File file = new File(path);
		String line;

		BufferedReader bufferedReader = null;
		try {
			FileReader fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (!line.isEmpty()) {
					Country test;
					if (prepareInfromationFile) {
						test = prepareInformationsAboutCountries(line);
					} else {
						test = new Country(line);
					}
					namesOfCountries.add(test);
				}
			}
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
		return namesOfCountries;
	}

	private Country prepareInformationsAboutCountries(String line)
			throws JSONException, RuntimeException, JsonParseException, JsonMappingException, IOException {
		Client client = Client.create();
		line = line.replaceAll(" ", "%20");

		String getUrl = ("http://restcountries.eu/rest/v1/name/" + line);
		ClientResponse response = returnResponse(client, getUrl);

		String result = response.getEntity(String.class);
		ObjectMapper objectMapper = returnObjectMapper();

		Country country = null;
		result = result.substring(1, result.length() - 1);
		country = objectMapper.readValue(result, Country.class);

		setAllNeighborsIntoOneString(client, country);

		return country;
	}

	private ObjectMapper returnObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}

	private ClientResponse returnResponse(Client client, String getUrl) {
		WebResource webResource = client.resource(getUrl);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException();
		}
		return response;
	}

	private void setAllNeighborsIntoOneString(Client client, Country country)
			throws IOException, JsonParseException, JsonMappingException {
		List<String> neighbors = country.getBorders();
		String borders = "";
		for (String neighbor : neighbors) {
			String getUrl = ("http://restcountries.eu/rest/v1/alpha/" + neighbor);
			ClientResponse response = returnResponse(client, getUrl);
			String result = response.getEntity(String.class);

			ObjectMapper objectMapper = returnObjectMapper();

			Country newCountry = null;
			newCountry = objectMapper.readValue(result, Country.class);

			borders += (newCountry.getName() + "/");
		}
		borders = borders.substring(0, borders.length() - 1);
		country.setAllBordersInOneString(borders);
		System.out.println(country.getAllBordersInOneString());
	}
}
