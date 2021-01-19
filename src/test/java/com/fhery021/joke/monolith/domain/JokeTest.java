package com.fhery021.joke.monolith.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fhery021.joke.monolith.web.rest.TestUtil;

public class JokeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Joke.class);
        Joke joke1 = new Joke();
        joke1.setId(1L);
        Joke joke2 = new Joke();
        joke2.setId(joke1.getId());
        assertThat(joke1).isEqualTo(joke2);
        joke2.setId(2L);
        assertThat(joke1).isNotEqualTo(joke2);
        joke1.setId(null);
        assertThat(joke1).isNotEqualTo(joke2);
    }
}
