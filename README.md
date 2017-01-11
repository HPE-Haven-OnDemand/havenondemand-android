# Android client library for Haven OnDemand

Official Android client library to help with calling [Haven OnDemand APIs](http://havenondemand.com).

The library contains 2 packages:

HODClient package for sending HTTP GET/POST requests to HavenOnDemand APIs.

HODResponseParser package for parsing JSON responses from HavenOnDemand APIs.

HODClient library supports minimum Android API level 10.

## What is Haven OnDemand?
Haven OnDemand is a set of over 70 APIs for handling all sorts of unstructured data. Here are just some of our APIs' capabilities:
* Speech to text
* OCR
* Text extraction
* Indexing documents
* Smart search
* Language identification
* Concept extraction
* Sentiment analysis
* Web crawlers
* Machine learning

For a full list of all the APIs and to try them out, check out https://www.havenondemand.com/developer/apis


## Integrate HavenOnDemand into an Android project
Open your app build.gradle and add the dependency as follows:

    dependencies {
        // add hodclient and hodresponseparser dependencies
        compile 'com.havenondemand:hodclient:2.2'
        compile 'com.havenondemand:hodresponseparser:2.2'
    }

## HODClient API References
**Constructor**

Creates and initializes an HODClient object.
```
HODClient(String apiKey, IHODClientCallback callback)
```

* `apiKey` your developer apikey.
* `callback` class that implements the IHODClientCallback interface.

*Example code:*
```
import com.hod.api.hodclient.HODClient;
import com.hod.api.hodclient.IHODClientCallback;

public class MyActivity extends Activity implements IHODClientCallback 
{
    HODClient hodClient = new HODClient("your-api-key", this);
    
    @Override
    public void requestCompletedWithJobID(String response){ }
        
    @Override
    public void requestCompletedWithContent(String response){ }
    
    @Override
    public void onErrorOccurred(String errorMessage){ }
}
```

**Function GetRequest**

Sends a HTTP GET request to call a Haven OnDemand API.
```
void GetRequest(Map<String,Object> params, String hodApp, Boolean async)
```
*Parameter:*
* `params` a HashMap object containing key/value pair parameters to be sent to a Haven OnDemand API, where the keys are the parameters of that API.

Note: In the case of a parameter type is an array<>, the value must be defined as a List\<String\> object.

E.g.:
```android
Map<String, Object> params = new HashMap<String, Object>();
List<String> urls = new ArrayList<String>();
urls.add("http://www.cnn.com");
urls.add("http://www.bbc.com");
params.put("url", urls);
params.put("unique_entities", "true");
List<String> entities = new ArrayList<String>();
entities.add("people_eng");
entities.add("places_eng");
params.put("entity_type", entities);
```

* `hodApp` a string to identify a Haven OnDemand API. E.g. "extractentities". Current supported apps are listed in the HODApps class.
* `async` specifies API call as Asynchronous or Synchronous.

*Response:*

* If async is true, response will be returned via the requestCompletedWithJobID(String response) callback function.
* If async is false, response will be returned via the requestCompletedWithContent(String response) callback function.
* If there is an error occurred, the error message will be sent via the onErrorOccurred(String errorMessage) callback function.

*Example code:*
``` 
// Call the Entity Extraction API to find people and places from CNN website

String hodApp = HODApps.ENTITY_EXTRACTION;
Map<String,Object> params =  new HashMap<String,Object>();
params.put("url", "http://www.cnn.com");
List<String> entities = new ArrayList<String>();
entities.add("people_eng");
entities.add("places_eng");
params.put("entity_type", entities);
hodClient.GetRequest(params, hodApp, false);
```

**Function PostRequest**

Sends a HTTP POST request to call a Haven OnDemand API.
```
void PostRequest(Map<String,Object> params, String hodApp, Boolean async)
```

*Parameters:*
* `params` a HashMap object containing key/value pair parameters to be sent to a Haven OnDemand API, where the keys are the parameters of that API. 

*Note:* In the case of a parameter type is an array<>, the value must be defined as a List\<String\> object.

E.g.:
``` 
Map<String, Object> params = new HashMap<String, Object>();
List<String> urls = new ArrayList<String>();
urls.add("http://www.cnn.com");
urls.add("http://www.bbc.com");
params.put("url", urls);
params.put("unique_entities", "true");
List<String> entities = new ArrayList<String>();
entities.add("people_eng");
entities.add("places_eng");
params.put("entity_type", entities);
```

* `hodApp` a string to identify a Haven OnDemand API. E.g. "ocrdocument". Current supported apps are listed in the IODApps class.
* `async` specifies API call as Asynchronous or Synchronous.

*Response:*

* If async is true, response will be returned via the requestCompletedWithJobID(String response) callback function.
* If async is false, response will be returned via the requestCompletedWithContent(String response) callback function.
* If there is an error occurred, the error message will be sent via the onErrorOccurred(String errorMessage) callback function.

*Example code:*
```android 
// Call the OCR Document API to scan text from an image file

String hodApp = HODApps.OCR_DOCUMENT;
Map<String,Object> params =  new HashMap<String,Object>();
params.put("file", "full/path/filename.jpg");
params.put("mode", "document_photo");
hodClient.PostRequest(params, hodApp, true);
```

**Function GetRequestCombination**

Sends a HTTP GET request to call a combination API.
```
GetRequestCombination(Map<String,Object> params, String hodApp, Boolean async)
```
* `params` a HashMap object containing key/value pair parameters to be sent to a Haven OnDemand combination API, where the keys are the parameters of that API.
* `hodApp` a string to identify a Haven OnDemand combination API.
* `async` specifies API call as Asynchronous or Synchronous.

*Example code:*
``` 
// Call a combination API named "ocrtoconcept", which recognizes text from an image and extract the concept of the text.
params.put("url", "http://www.some.domain/review.jpg");
params.put("languages", "en");
hodClient.GetRequestCombination(params, "ocrtoconcept", false);
```

**Function PostRequestCombination**

Sends a HTTP POST request to call a combination API.
```
PostRequestCombination(Map<String,Object> params, String hodApp, Boolean async)
```
* `params` a HashMap object containing key/value pair parameters to be sent to a Haven OnDemand combination API, where the keys are the parameters of that API.
* `hodApp` a string to identify a Haven OnDemand combination API.
* `async` specifies API call as Asynchronous or Synchronous.

*Example code:*
``` 
// Call a combination API named "imagetosentiment", which recognizes text from an image and analyze the sentiment of the text.
Map<String,Object> params =  new HashMap<>();
Map<String,String> file = new HashMap<>();
file.put("imageFile", "FullPath/AndName/image.jpg");
params.put("file", file);
hodClient.PostRequestCombination(params, "imagetosentiment", true);
```

**Function GetJobResult**

Sends a request to Haven OnDemand to retrieve content identified by the jobID.
```
void GetJobResult(String jobID)
```

*Parameter:*
* `jobID` is the job ID returned from a Haven OnDemand API upon an asynchronous call.

*Response:*
 
* Response will be returned via the requestCompletedWithContent(String response)

*Example code:*
```android
// Parse a JSON string contained a jobID and call the function to get the actual content from Haven OnDemand server

@Override
public void requestCompletedWithJobID(String response) 
{ 
    try {
        JSONObject mainObject = new JSONObject(response);
        if (!mainObject.isNull("jobID")) {
            jobID = mainObject.getString("jobID");
            hodClient.GetJobResult(jobID);
        }
    } catch (Exception ex) { }
}
```

**Function GetJobStatus**

Sends a request to Haven OnDemand to retrieve status of a job identified by a job ID. If the job is completed, the response will be the result of that job. Otherwise, the response will contain the current status of the job.
```
void GetJobStatus(String jobID)
```

*Parameter:*

* `jobID` is the job ID returned from a Haven OnDemand API upon an asynchronous call.

*Response:*
 
* Response will be returned via the requestCompletedWithContent(String response)

*Example code:*
```
// Parse a JSON string contained a jobID and call the function to get the actual content from Haven OnDemand server

@Override
public void requestCompletedWithJobID(String response) 
{ 
    try {
        JSONObject mainObject = new JSONObject(response);
        if (!mainObject.isNull("jobID")) {
            jobID = mainObject.getString("jobID");
            hodClient.GetJobStatus(jobID);
        }
    } catch (Exception ex) { }
}
```

----
## API callback functions
In your class, you will need to inherit the IHODClientCallback interface and implement callback functions to receive responses from the server
```android
public class MyClass implements IHODClientCallback {

@Override
public void requestCompletedWithJobID(String response) {}
    
@Override
public void requestCompletedWithContent(String response) {}
    
@Override
public void onErrorOccurred(string errorMessage){}
    
}
```

When you call the GetRequest() or PostRequest() with the ASYNC mode, the response will be returned to this callback function. The response is a JSON string containing the jobID.

```
@Override
public void requestCompletedWithJobID(String response) 
{
    
}
```

When you call the GetRequest() or PostRequest() with the SYNC mode or call the GetJobResult() or GwtJobStatus() function, the response will be returned to this callback function. The response is a JSON string containing the actual result of the service.

```
@Override
public void requestCompletedWithContent(String response) 
{
    
}
```

If there is an error occurred, the error message will be returned to this callback function.

```
@Override
public void onErrorOccurred(string errorMessage)
{
    
}
```

## HODResponseParser API References
**Constructor**

Creates and initializes an HODResponseParser object.
```
HODResponseParser()
```

*Example code:*
``` 
import hod.response.parser.HODErrorCode;
import hod.response.parser.HODErrorObject;
import hod.response.parser.HODResponseParser;

public class MyActivity extends Activity 
{
    HODResponseParser hodParser = new HODResponseParser();
}
```

**Function ParseJobID**

Parses a jobID from a json string returned from an asynchronous API call.
```
String ParseJobID(String response)
```

*Parameters:*

* response: a json string returned from an asynchronous API call.

*Return value:*

* The jobID or an empty string if not found.

*Example code:*
## 
    // Parse the jobID from within HODClient callback function
    void requestCompletedWithJobID(string response)
    {
        String jobID = hodParser.ParseJobID(response);
        if (jobID != "")
            hodClient.GetJobResult(jobID);
    }

----
**Function ParseSpeechRecognitionResponse**

    SpeechRecognitionResponse ParseSpeechRecognitionResponse(String jsonStr)

*Description:*
 
* Parses a json response from Haven OnDemand Speech Recognition API and returns a SpeechRegconitionResponse object.

> Note: See the full list of standard parser functions from the Standard response parser functions section at the end of this document.

*Parameters:*

* jsonStr: a json string returned from a synchronous API call or from the GetJobResult() or GetJobStatus() function.

*Return value:*

* An object containing API's response values. If there is an error or if the job is not completed (callback from a GetJobStatus call), the returned object is null and the error or job status can be accessed by calling the GetLastError() function.

*Example code:*
## 
    // Parse the Sentiment Analysis response from within HODClient callback function
    void requestCompletedWithContent(string response)
    {
        SentimentAnalysisResponse resp = hodParser.ParseSentimentAnalysisResponse(response);

        if (resp != null) {
            String positive = "";
            for (SentimentAnalysisResponse.Entity ent : resp.positive) {
                if (ent.original_text != null)
                    positive += "Statement: " + ent.original_text + "\n";
                if (ent.sentiment != null)
                    positive += "Sentiment: " + ent.sentiment + "\n";
                if (ent.topic != null)
                    positive += "Topic: " + ent.topic + "\n";
                if (ent.score != null)
                    positive += "Score: " + ent.score.toString() + "\n";
            }
            String negative = "";
            for (SentimentAnalysisResponse.Entity ent : resp.negative) {
                if (ent.original_text != null)
                    negative += "Statement: " + ent.original_text + "\n";
                if (ent.sentiment != null)
                    negative += "Sentiment: " + ent.sentiment + "\n";
                if (ent.topic != null)
                    negative += "Topic: " + ent.topic + "\n";
                if (ent.score != null)
                    negative += "Score: " + ent.score.toString() + "\n";
            }
            String sentiment = positive;
            sentiment += negative;
            sentiment += "Aggregate: \n" + resp.aggregate.sentiment + "\n";
            sentiment += resp.aggregate.score + "\n---";
            //print sentiment result 
        } else { // check status or handle error
            List<HODErrorObject> errors = hodParser.GetLastError();
            String errorMsg = "";
            for (HODErrorObject err: errors) {
                if (err.error == HODErrorCode.QUEUED) {
                    // sleep for a few seconds then check the job status again
                    hodClient.GetJobStatus(err.jobID);
                    return;
                } else if (err.error == HODErrorCode.IN_PROGRESS) {
                    // sleep for for a while then check the job status again
                    hodClient.GetJobStatus(err.jobID);
                    return;
                } else {
                    errorMsg += String.format("Error code: %d\nError Reason: %s\n", err.error, err.reason);
                    if (err.detail != null)
                        errorMsg += "Error detail: " + err.detail + "\n";
                }
                // print error message.
            }
        }
    }
----
**Function ParseCustomResponse**

Parses a json string and returns a custom object type based on the T class.
```
Object ParseCustomResponse(Class<?> T, String jsonStr)
```

*Parameters:*

* `\<T\>` a custom class object.
* `jsonStr` a json string returned from a synchronous API call or from the GetJobResult() or GetJobStatus() function.

*Return value:*

* An object containing API's response values. If there is an error or if the job is not completed (callback from a GetJobStatus call), the returned object is null and the error or job status can be accessed by calling the GetLastError() function.

*Example code:*
## 
    // Parse the Query Text Index response from within HODClient callback function
    public class MyTextIndexResponse {
        public List<Document> documents;
        public class Document {
            public String reference;
            public String index;
            public List<String> store_name;
            public List<String> operation_time;
            public List<String> store_location;
            public List<String> contact_number;
            public List<String> product_category;
        }
    }
    void requestCompletedWithContent(string response)
    {
        MyTextIndexResponse resp = (MyTextIndexResponse) hodParser.ParseCustomResponse(MyTextIndexResponse.class, response);
        if (resp != null) {
            for (MyTextIndexResponse.Document doc : resp.documents) {
                // access document field ...
                // e.g. doc.reference
            }
        } else { // check status or handle error
            List<HODErrorObject> errors = parser.GetLastError();
            String errorMsg = "";
            for (HODErrorObject err: errors) {
                if (err.error == HODErrorCode.QUEUED) {
                    // sleep for a few seconds then check the job status again
                    hodClient.GetJobStatus(err.jobID);
                    return;
                } else if (err.error == HODErrorCode.IN_PROGRESS) {
                    // sleep for for a while then check the job status again
                    hodClient.GetJobStatus(err.jobID);
                    return;
                } else {
                    errorMsg += String.format("Error code: %d\nError Reason: %s\n", err.error, err.reason);
                    if (err.detail != null)
                        errorMsg += "Error detail: " + err.detail + "\n";
                }
                // print error message.
            }
        }
    }


**Function GetLastError**

Get the latest error(s) if any happened during parsing the json string or HOD error returned from HOD server. > Note: The job "queued" or "in progress" status is also considered as an error situation. See the example below for how to detect and handle error status.
```
List<HODErrorObject> GetLastError()
```

*Return value:*

* An list object contains HODErrorObject

*Example code:*
```
List<HODErrorObject> errors = parser.GetLastError();
String errorMsg = "";
for (HODErrorObject err : errors) {
    if (err.error == HODErrorCode.QUEUED) {
        hodClient.GetJobStatus(err.jobID);
        return;
    } else if (err.error == HODErrorCode.IN_PROGRESS) {
        hodClient.GetJobStatus(err.jobID);
        return;
    } else {
        errorMsg += String.format("Error code: %d\nError Reason: %s\n", err.error, err.reason);
        if (err.detail != null)
            errorMsg += "Error detail: " + err.detail + "\n";
    }
    // print errorMsg
}
```
----

## Demo code 1: 

**Call the Entity Extraction API to extract people and places from cnn.com website with a synchronous GET request**

```
import com.hod.api.hodclient.IHODClientCallback;
import com.hod.api.hodclient.HODApps;
import com.hod.api.hodclient.HODClient;
import hod.response.parser.HODErrorCode;
import hod.response.parser.HODErrorObject;
import hod.response.parser.HODResponseParser;
    
public class MyActivity extends Activity implements IHODClientCallback {

    HODClient hodClient;
    HODResponseParser hodParser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            
        hodClient = new HODClient("your-apikey", this);
        hodParser = new HODResponseParser();
            
        useHODClient();
    }

    private void useHODClient() {
        String hodApp = HODApps.ENTITY_EXTRACTION;
        params.put("url", "http://www.cnn.com");
        List<String> entities = new ArrayList<String>();
        entities.add("people_eng");
        entities.add("places_eng");
        params.put("entity_type", entities);
        params.put("unique_entities", "true");
            
        hodClient.GetRequest(params, hodApp, false);
    }
    
    // implement callback functions
    @Override
    public void requestCompletedWithContent(String response) { 
        EntityExtractionResponse resp = hodParser.ParseCustomResponse(response);
        if (resp != null) {
            String values = "";
            for (EntityExtractionResponse.Entity ent : resp.entities) {
                values += ent.type + "\n";
                values += ent.normalized_text + "\n";
                if (ent.type.equals("places_eng")) {
                    values += ent.additional_information.place_country_code + "\n";
                    values += ent.additional_information.place_elevation + "\n";
                    values += ent.additional_information.place_population + "\n";
                } else if (ent.type.equals("people_eng")) {
                    values += ent.additional_information.person_date_of_birth + "\n";
                    values += ent.additional_information.person_profession + "\n";
                    values += ent.additional_information.wikipedia_eng + "\n";
                }
            }
            // print the values
        } else {
            List<HODErrorObject> errors = parser.GetLastError();
            String errorMsg = "";
            for (HODErrorObject err : errors) {
                errorMsg += String.format("Error code: %d\nError Reason: %s\n", err.error, err.reason);
                if (err.detail != null)
                    errorMsg += "Error detail: " + err.detail + "\n";
                // handle error message
            }
        }	
    }
        
    @Override
    public void onErrorOccurred(String errorMessage) { 
        // handle error if any
    }
}
```

## Demo code 2:
 
**Call the OCR Document API to recognize text from an image with an asynchronous POST request**

```
import com.hod.api.hodclient.IHODClientCallback;
import com.hod.api.hodclient.HODApps;
import com.hod.api.hodclient.HODClient;
import hod.response.parser.HODErrorCode;
import hod.response.parser.HODErrorObject;
import hod.response.parser.HODResponseParser;
import hod.response.parser.OCRDocumentResponse;
    
public class MyActivity extends Activity implements IHODClientCallback {

    HODClient hodClient;
    HODResponseParser hodParser;
    String hodApp = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            
        hodClient = new HODClient("your-apikey", this);
        hodParser = new HODResponseParser();    
        useHODClient();
    }
    
    private void useHODClient() {
        hodApp = HODApps.OCR_DOCUMENT;
        Map<String, Object> params = new HashMap<String, Object>();
            
        params.put("file", "path/and/filename");
        params.put("mode", "document_photo");
    
        hodClient.PostRequest(params, hodApp, true);
    }
        
    // implement delegated functions
        
    /**************************************************************************************
    * An async request will result in a response with a jobID. We parse the response to get
    * the jobID and send a request for the actual content identified by the jobID.
    **************************************************************************************/ 
    @Override
    public void requestCompletedWithJobID(String response) { 
        String jobID = parser.ParseJobID(response);
        if (jobID.length() > 0)
            hodClient.GetJobStatus(jobID);
    }
    
    @Override
    public void requestCompletedWithContent(String response) { 
        OCRDocumentResponse resp = hodParser.ParseOCRDocumentResponse(response);
        if (resp != null) {
            String text = "";
            for (OCRDocumentResponse.TextBlock block : resp.text_block) {
                text += block.text + "\n";
            }
            // print recognized text.
        } else {
            List<HODErrorObject> errors = parser.GetLastError();
            String errorMsg = "";
            for (HODErrorObject err: errors) {
                if (err.error == HODErrorCode.QUEUED) {
                    // sleep for a few seconds then check the job status again
                    hodClient.GetJobStatus(err.jobID);
                    return;
                } else if (err.error == HODErrorCode.IN_PROGRESS) {
                    // sleep for for a while then check the job status again
                    hodClient.GetJobStatus(err.jobID);
                    return;
                } else {
                    errorMsg += String.format("Error code: %d\nError Reason: %s\n", err.error, err.reason);
                    if (err.detail != null)
                        errorMsg += "Error detail: " + err.detail + "\n";
                }
                // print error message.
            }
        }
    }
        
    @Override
    public void onErrorOccurred(String errorMessage) { 
        // handle error if any
    }
}
```

----
## Standard response parser functions

```
ParseSpeechRecognitionResponse(String jsonStr)
ParseCancelConnectorScheduleResponse(String jsonStr)
ParseConnectorHistoryResponse(String jsonStr)
ParseConnectorStatusResponse(String jsonStr)
ParseCreateConnectorResponse(String jsonStr)
ParseDeleteConnectorResponse(String jsonStr)
ParseRetrieveConnectorConfigurationFileResponse(String jsonStr)
ParseRetrieveConnectorConfigurationAttrResponse(String jsonStr)
ParseStartConnectorResponse(String jsonStr)
ParseStopConnectorResponse(String jsonStr)
ParseUpdateConnectorResponse(String jsonStr)
ParseExpandContainerResponse(String jsonStr)
ParseStoreObjectResponse(String jsonStr)
ParseViewDocumentResponse(String jsonStr)
ParseGetCommonNeighborsResponse(String jsonStr)
ParseGetNeighborsResponse(String jsonStr)
ParseGetNodesResponse(String jsonStr)
ParseGetShortestPathResponse(String jsonStr)
ParseGetSubgraphResponse(String jsonStr)
ParseSuggestLinksResponse(String jsonStr)
ParseSummarizeGraphResponse(String jsonStr)
ParseOCRDocumentResponse(String jsonStr)
ParseRecognizeBarcodesResponse(String jsonStr)
ParseRecognizeImagesResponse(String jsonStr)
ParseDetectFacesResponse(String jsonStr)
ParsePredictResponse(String jsonStr)
ParsePredictV2Response(String jsonStr)
ParseRecommendResponse(String jsonStr)
ParseRecommendV2Response(String jsonStr)
ParseTrainPredictionResponse(String jsonStr)
ParseTrainPredictionV2Response(String jsonStr)
ParseTrendAnalysisResponse(String jsonStr)
ParseCreateQueryProfileResponse(String jsonStr)
ParseDeleteQueryProfileResponse(String jsonStr)
ParseRetrieveQueryProfileResponse(String jsonStr)
ParseUpdateQueryProfileResponse(String jsonStr)
ParseFindRelatedConceptsResponse(String jsonStr)
ParseAutoCompleteResponse(String jsonStr)
ParseAnomalyDetectionResponse(String jsonStr)
ParseEntityExtractionResponse(String jsonStr)
ParseEntityExtractionV2Response(String jsonStr)
ParseExtractConceptsResponse(String jsonStr)
ParseExpandTermsResponse(String jsonStr)
ParseHighlightTextResponse(String jsonStr)
ParseIdentifyLanguageResponse(String jsonStr)
ParseTokenizeTextResponse(String jsonStr)
ParseSentimentAnalysisResponse(String jsonStr)
ParseSentimentAnalysisV2Response(String jsonStr)
ParseAddToTextIndexResponse(String jsonStr)
ParseCreateTextIndexResponse(String jsonStr)
ParseDeleteTextIndexResponse(String jsonStr)
ParseDeleteFromTextIndexResponse(String jsonStr)
ParseIndexStatusResponse(String jsonStr)
ParseListResourcesResponse(String jsonStr)
ParseRestoreTextIndexResponse(String jsonStr)
```
----
## Supported standard response classes

```
SpeechRecognitionResponse
CancelConnectorScheduleResponse
ConnectorHistoryResponse
ConnectorStatusResponse
CreateConnectorResponse
DeleteConnectorResponse
RetrieveConnectorConfigurationFileResponse
RetrieveConnectorConfigurationAttrResponse
StartConnectorResponse
StopConnectorResponse
UpdateConnectorResponse
ExpandContainerResponse
StoreObjectResponse
ViewDocumentResponse
GetCommonNeighborsResponse
GetNeighborsResponse
GetNodesResponse
GetShortestPathResponse
GetSubgraphResponse
SuggestLinksResponse
SummarizeGraphResponse
OCRDocumentResponse
RecognizeBarcodesResponse
RecognizeImagesResponse
DetectFacesResponse
PredictResponse
PredictV2Response
RecommendResponse
RecommendV2Response
TrainPredictionResponse
TrainPredictionV2Response
TrendAnalysisResponse
CreateQueryProfileResponse
DeleteQueryProfileResponse
RetrieveQueryProfileResponse
UpdateQueryProfileResponse
FindRelatedConceptsResponse
AutoCompleteResponse
AnomalyDetectionResponse
EntityExtractionResponse
EntityExtractionV2Response
ExtractConceptsResponse
ExpandTermsResponse
HighlightTextResponse
IdentifyLanguageResponse
TokenizeTextResponse
SentimentAnalysisResponse
SentimentAnalysisV2Response
AddToTextIndexResponse
CreateTextIndexResponse
DeleteTextIndexResponse
DeleteFromTextIndexResponse
IndexStatusResponse
ListResourcesResponse
RestoreTextIndexResponse
```
---
## License
Licensed under the MIT License.