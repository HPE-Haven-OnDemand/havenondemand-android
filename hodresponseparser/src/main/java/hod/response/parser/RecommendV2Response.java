package hod.response.parser;

import java.util.List;

/**
 * Created by vanphongvu on 10/10/16.
 */

public class RecommendV2Response {
    public List<Dataset> dataset;

    public class Dataset
    {
        public Object row;
        public String prediction;
        public List<Recommendations> recommendations;
    }

    public class Recommendations
    {
        public Double confidence;
        public Double distance;
        public String prediction;
        public Object recommendation;
    }
}
