package hod.response.parser;

import java.util.List;

/**
 * Created by vanphongvu on 10/10/16.
 */

public class PredictV2Response {
    public List<Dataset> dataset;

    public class Dataset
    {
        public Object row;
        public String prediction;
        public Double confidence;
    }
}
