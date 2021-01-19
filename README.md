# MovieDatabase

Libraries I used:
- Retrofit: my HTTP client to model my network calls
- OkHttp: for sending and receiving network requests (created an interceptor for adding the API key and for logging to make sure my requests were formed correctly during development)
- Gson: for parsing the JSON returned from network requests
- Picasso: for displaying images
- Mockito: for mocking classes in my unit tests
- Kotlin Coroutines Test: for testing asynchronous code in my coroutines

Notes on my approach: 
- I started with the Android "Master/Detail" template to give me a bit of a jump start. This did some of the work of setting up the basic RecyclerView and adapter for me
- I used MVVM so that the ViewModels could handle to business logic of fetching data, and also to retain the data, scroll state, etc. through orientation changes
    - I didn't use databinding, because I personally like to see where all UI values are set in the Activity/Fragment and keep all that view stuff together. 
      I find it's sometime easier to debug than databinding.
    - I included unit tests for both of the ViewModels
