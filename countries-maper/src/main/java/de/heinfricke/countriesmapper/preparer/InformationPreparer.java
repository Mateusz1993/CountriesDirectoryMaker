package de.heinfricke.countriesmapper.preparer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import de.heinfricke.countriesmapper.country.Country;

/**
 * This class is used to take all informations about countries and put them into
 * Country objects.
 * 
 * @author mateusz
 *
 */
public class InformationPreparer {

	/**
	 * This method gets all informations about country and create new Country
	 * object with these informations.
	 * 
	 * @param line
	 *            As parameter it takes each country from reading file.
	 * @return It returns new Country object with all informations about
	 *         country.
	 * @throws JSONException
	 * @throws RuntimeException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public Country collectCountryData(String line)
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

	/**
	 * This method creates and configures new ObjectMapper object.
	 * 
	 * @return It returns new ObjectMapper object.
	 */
	private ObjectMapper returnObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}

	/**
	 * This method returns HTTP response.
	 * 
	 * @param client
	 *            As first parameter it takes Client object.
	 * @param getUrl
	 *            As second parameter it takes URL address.
	 * @return It returns ClientResponse object with HTTP response.
	 */
	private ClientResponse returnResponse(Client client, String getUrl) {
		WebResource webResource = client.resource(getUrl);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException();
		}
		return response;
	}

	/**
	 * This method set all neighbors into one string. It takes country code of
	 * neighbor and return full name of neighboring country. All neighbors are
	 * separated with "/".
	 * 
	 * @param client
	 *            As first parameter it takes Client object.
	 * @param country
	 *            As second parameter it takes Country object.
	 * @throws IOException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 */
	private void setAllNeighborsIntoOneString(Client client, Country country)
			throws IOException, JsonParseException, JsonMappingException {
		List<String> neighbors = country.getBorders();
		List<String> fullNameNeighbors = new ArrayList<String>();
		List<Country> borderCountries = new ArrayList<Country>();
		
		String borders = "";
		for (String neighbor : neighbors) {
			String getUrl = ("http://restcountries.eu/rest/v1/alpha/" + neighbor);
			ClientResponse response = returnResponse(client, getUrl);
			String result = response.getEntity(String.class);
			ObjectMapper objectMapper = returnObjectMapper();

			Country newCountry = null;
			newCountry = objectMapper.readValue(result, Country.class);
			fullNameNeighbors.add(newCountry.getName());
			borders += (newCountry.getName() + "/");
			
			Country newerCountry = new Country(newCountry.getName());
			borderCountries.add(newerCountry);
		}
		borders = borders.substring(0, borders.length() - 1);
		country.setAllBordersInOneString(borders);
		country.setBorders(fullNameNeighbors);
		country.setBorderCountries(borderCountries);
		
	}
}
