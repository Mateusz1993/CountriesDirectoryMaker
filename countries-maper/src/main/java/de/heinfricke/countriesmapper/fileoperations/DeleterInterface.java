package de.heinfricke.countriesmapper.fileoperations;

import java.util.List;
import java.util.Map;

import de.heinfricke.countriesmapper.country.Country;

public interface DeleterInterface {
    void deleteDirectories(Map<String, List<Country>> organizedCountriesMap, String userPath);
}
