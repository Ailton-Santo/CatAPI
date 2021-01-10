# CatAPI

This project follows MVVM architectural pattern in order to  segregate  the business logic from the UI and also to facilitate the process of writing unit tests.

I've used technologies such as Retrofit to make API requests, RxJava to retrieve items, RecyclerView to display the retrieved items onto the screen and Dagger framework to separate the network calls from the ViewModel.

In the UI, I've added a swipeLayout that allows the user to refresh the screen with a simple swipe and used Glide Library to load up the URL'S into the ImageView.

Also added a Radio Button functionality to display different type of image formats.

Finally, for the Unit Tests, I used Mockito for the set up and the writting process.
