package hod.response.parser;

import java.util.List;

/**
 * Created by vanphongvu on 10/10/16.
 */

public class SentimentAnalysisV2Response {
    public List<SentimentObj> sentiment_analysis;

    public class SentimentObj
    {
        public List<Entity> positive;
        public List<Entity> negative;
        public Aggregate aggregate;

        public class Aggregate
        {
            public String sentiment;
            public Double score;
        }

        public class Entity
        {
            public String sentiment;
            public String topic;
            public Double score;
            public String original_text;
            public String normalized_text;
            public Double original_length;
            public Double normalized_length;
            public Integer offset;
        }
    }
}
