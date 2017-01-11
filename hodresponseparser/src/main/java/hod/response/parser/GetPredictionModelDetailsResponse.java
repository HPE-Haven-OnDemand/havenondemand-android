package hod.response.parser;

import java.util.List;

/**
 * Created by vanphongvu on 10/10/16.
 */

public class GetPredictionModelDetailsResponse {
    public List<Models> models;
    public class Models
    {
        public String model_name;
        public String date_created;
        public String model_type;
        public List<Structure> structure;
        public Performance_measures performance_measures;
    }
    public class Structure
    {
        public String name;
        public String order;
        public String type;
        public Properties properties;
    }
    public class Properties
    {
        public Boolean label;
    }
    public class Performance_measures
    {
        public String selected_algorithm;
        public List<Algorithm_params> algorithm_params;
        public String prediction_field;
        public Classification_measures classification_measures;
        public Regression_measures regression_measures;
    }
    public class Algorithm_params
    {
        public String param_name;
        public String param_value;
    }
    public class Classification_measures
    {
        public double accuracy;
        public double precision;
        public double recall;
        public double f_measure;
        public List<Confusion_matrix> confusion_matrix;
        public double train_accuracy;
        public Boolean overfitting;
        public Boolean underfitting;
    }
    public class Confusion_matrix
    {
        public String predicted_label;
        public String actual_label;
        public Double amount;
    }
    public class Regression_measures
    {
        public Boolean overfitting;
        public Double mean_squared_error;
        public Double root_mean_squared_error;
        public Double mean_absolute_error;
        public Double coefficient_of_determination;
        public Double train_mean_squared_error;
        public Double train_root_mean_squared_error;
        public Double train_mean_absolute_error;
        public Double train_coefficient_of_determination;
    }
}
