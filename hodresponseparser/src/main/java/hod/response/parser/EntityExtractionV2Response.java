package hod.response.parser;

import java.util.List;

/**
 * Created by vanphongvu on 10/10/16.
 */

public class EntityExtractionV2Response {
    public List<Entity> entities;

    public class Entity
    {
        public String normalized_text;
        public String original_text;
        public String type;
        public Long normalized_length;
        public Long original_length;
        public Double score;
        public String normalized_date;
        public EntityAdditionalInformation additional_information;
        public List<Component> components;
        public List<Matches> matches;
        public Integer documentIndex;
    }
    public class Component
    {
        public Integer original_length;
        public String original_text;
        public String type;
    }
    public class Matches
    {
        public Integer original_length;
        public String original_text;
        public Integer offset;
    }
    public class EntityAdditionalInformation
    {
        public List<String> person_profession;
        public String person_date_of_birth;
        public String person_date_of_death;
        public Double lon;
        public Double lat;
        public Double wikidata_id;
        public String wikipedia_eng;
        public String image;

        public Double place_population;
        public String place_country_code;
        public String place_region1;
        public String place_region2;
        public String place_type;
        public Integer place_timezone;
        public Integer place_elevation;
        public String place_continent;

        public String url_homepage;
        public List<String> company_wikipedia;
        public List<String> company_ric;
        public List<String> company_google;
        public List<String> company_yahoo;

        public String country;

        public List<String> disease_icd10;
        public List<String> disease_diseasesdb;

        public List<String> film_director;
        public List<String> film_producer;
        public List<String> film_writer;
        public List<String> film_starring;
        public List<String> film_composer;
        public List<String> film_studio;
        public List<String> film_year;
        public List<String> film_runtime;
        public List<String> film_country;
        public List<String> film_language;
        public List<String> film_screenwriter;
        public List<String> film_imdb;
        public List<String> film_dir_photography;
        public List<String> film_distributor;
        public String film_budget;
        public String film_gross;
        public List<String> film_genre;

        public List<String> team_sport;
        public String team_league;
        public String language_family;
        public String language_iso639_1;
        public List<String> language_iso639_2;
        public List<String> language_iso639_3;
        public List<String> language_script;
        public List<String> language_group;
        public List<String> language_official_country_code;
        public Integer language_nativespeakers;
    }
}

