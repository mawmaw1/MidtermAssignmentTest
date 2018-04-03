package testex.jokefetching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FetcherFactory implements IFetcherFactory{
    private final List<String> availableTypes =
            Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");

    @Override
    public List<String> getAvailableTypes() {
        return availableTypes;
    }

    @Override
    public List<IJokeFetcher> getJokeFetchers(String jokesToFetch) {

    String[] jokeTypes = jokesToFetch.split(",");
    List<IJokeFetcher> iJokeFetcherList = new ArrayList<>();
    for(String jokeType : jokeTypes){
        switch(jokeType){
            case "EduJoke" : iJokeFetcherList.add(new EduJoke());
            break;
            case "ChuckNorris" : iJokeFetcherList.add(new ChuckNorris());
            break;
            case "Moma" : iJokeFetcherList.add(new Moma());
            break;
            case "Tambal" : iJokeFetcherList.add(new Tambal());
            break;
        }
    }
    return iJokeFetcherList;

    }
}
