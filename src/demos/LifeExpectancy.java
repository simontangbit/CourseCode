package demos;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Visualizes life expectancy in different countries.
 * <p>
 * It loads the country shapes from a GeoJSON file via a data reader, and loads the population density values from
 * another CSV file (provided by the World Bank). The data value is encoded to transparency via a simplistic linear
 * mapping.
 */
public class LifeExpectancy extends PApplet {

    UnfoldingMap map;
    Map<String, Float> lifeExpByCountry;
    List<Feature> countries;
    List<Marker> countryMarkers;
    String _CSVFilePath;


    public void setup() {
        size(800, 600, OPENGL);
        map = new UnfoldingMap(this, 50, 50, 700, 500, new Google.GoogleMapProvider());
        MapUtils.createDefaultEventDispatcher(this, map);

        _CSVFilePath = "LifeExpectancyWorldBankModule3.csv";
//        lifeExpByCountry = new HashMap<String, Float>();
        lifeExpByCountry = loadLifeExpectancyFromCSV(_CSVFilePath);
        countries = GeoJSONReader.loadData(this, "data/countries.geo.json");
        countryMarkers = MapUtils.createSimpleMarkers(countries);
        map.addMarkers(countryMarkers);
        map.zoomToLevel(2);
        shadeCountries();

    }

    public void draw() {
        map.draw();

    }

    //Helper method to color each country based on life expectancy
    //Red-orange indicates low (near 40)
    //Blue indicates high (near 100)
    private void shadeCountries() {
        for (Marker marker : countryMarkers) {
            String countryId = marker.getId();
            if (lifeExpByCountry.containsKey(countryId)) {
                Float lifeExp=lifeExpByCountry.get(countryId);
                int colorLevel=(int)map(lifeExp,40,90,10,255);
                marker.setColor(color(255-colorLevel,100,colorLevel));
            } else {
                marker.setColor(color(125,125,125));
            }

        }
    }

    //Helper method to load life expectancy data from file
    private Map<String, Float> loadLifeExpectancyFromCSV(String fileName) {
        Map<String, Float> lifeMap = new HashMap<String, Float>();
        String[] rows = loadStrings(fileName);
        for (String row : rows) {
            String[] columns ;
            columns = row.split(",");

            if (columns.length > 5 && !columns[5].isEmpty()&&!columns[5].equals("Title")&&GetCountByChar(columns[5],'.')==1) {
//                for (String Column:columns){
//                    print("-----------"+Column);
//                }
                float value = Float.parseFloat(columns[5]);
                lifeMap.put(columns[4], value);
            }
//            print(row);
        }
        return lifeMap;
    }

    public static void main(String[] args){
        PApplet.main(new String[] {"--present", "demos.LifeExpectancy"});
    }

    public static int GetCountByChar(String source,char ch){
        if (!source.contains(String.valueOf(ch)))
            return 0;
        int count=0;
        char[] chars= source.toCharArray();
        for (char a :chars){
            if (a==ch){
                ++count;
            }
        }
        return count;
    }

}
