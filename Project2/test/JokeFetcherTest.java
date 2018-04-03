package test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import testex.*;
import testex.jokefetching.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;


@RunWith(MockitoJUnitRunner.class)
public class JokeFetcherTest {

    private JokeFetcher jokeFetcher;
    private IDateFormatter dateFormatter;

    @Mock IDateFormatter ifMock;
    @Mock IFetcherFactory factory;
    @Mock Moma moma;
    @Mock ChuckNorris chuck;
    @Mock EduJoke edu;
    @Mock Tambal tambal;

    @Before
    public void before(){
        //dateFormatter = mock(IDateFormatter.class);
        List<IJokeFetcher> fetchers = Arrays.asList(edu, chuck,moma,tambal);
        when(factory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal")).thenReturn(fetchers);
        List<String> types = Arrays.asList("EduJoke","ChuckNorris","Moma","Tambal");
        when(factory.getAvailableTypes()).thenReturn(types);
        jokeFetcher = new JokeFetcher (ifMock, factory);
    }
    @Test
    public void testGetJokeFetchers(){
        List<IJokeFetcher> result = factory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal");
        assertEquals(4, result.size());
    }
    @Test
    public void testGetAvailableTypes(){

        List<String> availableTypes = Arrays.asList("EduJoke","ChuckNorris","Moma","Tambal");
        assertThat(availableTypes, is(factory.getAvailableTypes()));
        assertEquals(4, availableTypes.size());
    }
    @Test
    public void testIsStringValid(){
        assertEquals(false,jokeFetcher.isStringValid("denne,indeholder,ej"));
        assertEquals(true,jokeFetcher.isStringValid("Moma"));

    }

    @Test
    public void testGetJokes() throws JokeException {
    Date date = new Date();
    Jokes jokes = jokeFetcher.getJokes("Moma", "Europe/Copenhagen", date);
    verify(ifMock, times(1)).getFormattedDate("Europe/Copenhagen",date);
    }

//    @Test
//    public void testEduJoke() throws JokeException {
//    Joke jokeTest = new Joke("blabla","bla");
//    when(edu.getJoke()).thenReturn(jokeTest);
//    Jokes eduJoke = jokeFetcher.getJokes("EduJoke","Europe/Copenhagen",new Date());
//    assertThat(eduJoke.getJokes(), hasItems(jokeTest));
//    verify(factory,times(1)).getJokeFetchers("EduJoke");
//    }



}

