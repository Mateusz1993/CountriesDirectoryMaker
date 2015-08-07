package de.heinfricke.countriesmapper.fileoperations;

import java.util.List;
import java.util.Map;

import de.heinfricke.countriesmapper.country.Country;

public interface MakerInterface {
    public void createDirectories(Map<String, List<Country>> organizedCountriesMap, String userPath);
}
