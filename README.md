# moviedb-rxjava-example

## What is it?
 This is a project for demonstrating the use of RxJava with a simple but real use case.
 
 The app uses the [themoviedb.org](https://themoviedb.org) API to search for and display actors and movies. 
 
## How do I start?
 Checkout the `master` branch or the `start_point` tag if you'd like to work through the exercise, or checkout the `solution` branch for the full solution, or any of the `rxjava/` tags to view an intermediate/in-progress stage.
 
 As this is intended as a practical, hands-on exercise, the app comes with a ready-made feature, to search actors by name, without using RxJava. `SearchActorsActivity` has a `SearchView` in the `Toolbar`, which executes a search via the `VanillaDataSource` and `VanillaRestClient`. An asynchronous `Retrofit` call is used, and the response is converted into `ActorViewModel` objects using the `ActorViewModelConverter`, which are displayed in `ActorCard`s in a `RecyclerView` back on the `SearchActorsActivity`. 
 
 For demonstration purposes, there is also a "finished" RxJava implementation of a movie list feature (see `MovieListActivity`, `RxJavaDataSource` and `RxJavaRestClient`), since it would be pretty annoying to have to switch between branches/tags if you wanted to check something.
  
## So what should I do?
 Start from `start_point`, and refactor the actors search feature to use RxJava! 
   
### 1. Retrieving the Configuration object
themoviedb.org API has a `Configuration` object which is required if you want to retrieve any images from the server. Our `ActorCard` has an `ImageView` into which we load a picture of that actor, using `Picasso`, so we need that `Configuration` object. But the config isn't going to change very often, so we probably only need to request it once. At this point, we're not handling that very well. In the `VanillaDataSource`, we request the `Configuration` in the constructor, and store the value in the `config` member variable.
 
 Look at the `VanillaRestClient`'s `getConfig` method: 
    
     @GET("/3/configuration")
     Call<Configuration> getConfiguration(@Query("api_key") String apiKey);
 
 This takes a `Callback` parameter, which gets passed to the asynchronous `getConfiguration` method in `ConfigurationService`. There's already a reactive version of this call, which doesn't take a `Callback`, and returns an `Observable` of type `Configuration`, instead of `void`:
     
     @GET("/3/configuration")
     Observable<Configuration> getConfigurationRx(@Query("api_key") String apiKey);

 Try refactoring the `VanillaDataSource` to use this reactive method instead of the asynchronous one. 
 
 (Don't forget to consider scheduling, using the `observeOn` and `subscribeOn` operators). 

### 2. Making the Configuration retrieval reactive 
We expose a getter for `config`, and an `isInitialised` method (which is a bit silly, since it only checks whether the `config` variable is `null`). `SearchActorActivity` checks the value of `isInitialised` before beginning a search, and shows an `AlertDialog` to the user if they try to search before the `Configuration` has been retrieved. In usability terms, this is atrocious.

`SearchActorsActivity` shouldn't have to care whether the data source is "initialised" yet. If the object has been constructed, that should be enough. When a user executes a search, their intention has been made clear. If the `Configuration` isn't available yet, the search should be performed when it is.
 
 We're already using RxJava to get the `Configuration` when the `VanillaDataSource` is constructed. It would make more sense to only make that call when we need to. We can change the `getConfiguration` method to return an `Observable<Configuration>` instead of a plain `Configuration`. We could do:
 
     return restClient.getConfig(); 
     
 ... but then we'd we making the network call every time someone calls `getConfiguration`. Instead, we can use the `doOnNext` operator, to assign the retrieved `Configuration` to our `config` member variable. Like so: 
 
     return restClient.getConfig()
                 .doOnNext(new Action1<Configuration>() {
                     @Override
                     public void call(Configuration configuration) {
                         config = configuration;
                     }
                 });
                 
 ... but that still wouldn't be making any use of the member variable, so let's take it up a notch.
 
 We can use `Observable.create` to create an `Observable` which returns the value of `config`. We can also use the `restClient` to get an `Observable` which populates our `config` (as above).
 Now here's where it gets fun: we can use `Observable.concat` to combine these two streams into one, and we can use the `first` operator to only return the first value which is emitted. But that will be `null`, since `config` hasn't been set yet. So we can use a predicate in the `first` operator, and make sure the `Configuration` object is valid!
 
 Now the `SearchActorActivity` doesn't have to check whether the data source is initialised, it just gets an `Observable<Configuration>`, which emits from memory or from the network, and `SearchActorActivity` doesn't have to know or care. 

 Bonus points: If you've gone this far, you've probably noticed that `ActorModelViewConverter` now needs to be refactored to take an `Observable<Configuration>` instead of a `Configuration`. How could you convert this class to be reactive too?
 
 Extra bonus points: `concat` can be used with up to 9 `Observable` objects. We're only using two - getting data from memory, and from the network. Try implementing a third source, and adding that in to the `concat`/`first` arrangement.
  
### 3. Making the actors search reactive
Our `getActors` method is still asynchronous, it still uses a callback. This is less complex than `Configuration`, since each search should presumably be a new network request. We don't need to do any of the in-memory caching stuff.

Try converting this to being reactive as well, returning an `Observable` instead of using a `Callback`. What changes will you have to make to `SearchActorActivity`?

(Don't forget your schedulers!)
 
### 4. Making the SearchView reactive
 Look at `SearchActorsActivity`. At the moment, users have to explicitly perform the search action, to begin a search. That doesn't sound very reactive to me. 
 
 Luckily, Jake Wharton's `rxbinding-appcompat-v7` contains an `RxSearchView` which has a static `queryTextChangeEvents` that returns an `Observable`. You could use this to perform searches when the query text changes. 
 
 But that would kick off a new search every time the user types a letter! Screw that. You'll want to make use of some operators to fine-tune the behaviour of the search feature. Check out `filter`, and `debounce`, for example. 
 
 What other operators could come in handy here?

### And beyond...
That brings you up to the level of `solution`, or the `rxjava/searchview` tag! 

If you're still hungry, maybe have a look at the movie list feature, `MovieListActivity`. Genres require special treatment in this API too. The `Movie` object returned from the API only has numerical genre IDs, not genre names. A separate call is required to get the mapping of ids to genre names.
In a reactive world, adding that third API call to the mix isn't so bad. Converting genre IDs to genre names sounds like a good use case for `map`, or maybe `flatmap` operators, depending on your implementation.
How would you combine the multiple calls together? `combineLatest`, or `zip` maybe?

Or hey, do whatever you want. Go nuts. Reactive!