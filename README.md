# Eiver-Test-Wallyd
This app has been created in sole purpose of displaying a list of populars movies,
you can also view  details about a specific  movie, 
such as the title, the release date and of course the trailer. 
Enjoy

Features : 
- List of Movies
- Details of a movie ( trailers of videos)
- Infite Scroll (Pagination)
- Search for a movie
- Handle NetWork Failure
- Handle Configuration Changes

Technical Decisions:

I chose the go with the MVVM architecture because it’s more befitting for this project’s size and it’s the architecture that I know the most in term of implementations along with MVC, and MV ann clean architecture
 I used Kotlin Coroutines help to manage long-running tasks and Retrofit as REST API, because both combinations provide an exceptional tools to build a strong app capable of requesting data in asynchronous way in a background thread for the network call and the main thread for updating the UI

To load Images I used Glide, I could have gone with Picasso, or ImageLoader, but I chose Glide because of its network and cache performances

For implementing the infite scroll and the pagination I used Paging Library 3 developped by google which is still in beta state


