# Numbers

>I wish I could say that my personal projects reflected my best work, but unfortunately it takes time to develop production quality apps and whilst working as a full-time android developer I often need to spend my remaining time on other pursuits. As they are just for my use, there's not the usual amount of attention paid to testing that one would want on an app in the playstore.

A small personal sandbox project, an app to be used on my PCT hike. A **unit converter** (to help me with the whole metric-imperial confusion) and **item counter**, so I can keep some stats about my hike. 

Now that I've finished my hike, photos and stats can be found [here](https://photos.app.goo.gl/iYMvSZTMy6I3FhhI2).

### Key Project Features
* **Basic SQLite DB** - The database is written in the older style using SQLiteOpenHelper. There may be newer high-level ways to setup and interact with a database in Android these days, but it's still good to know how this stuff works, and it's a good opportunity to get to understand SQL statement
* **Loaders** - Before RxJava and LiveData became the thing, this was a nice way to observe changes to data and update the UI
* **'Silent' setters** - I added some extension functions to allow me to set observed values without triggering listeners. This is necessary to prevent infinite loops as the input fields can also be set in the code.
* **DiffUtil** - calculates changes to a data set and then allows a RecyclerView adapter to animate changes.
* **Networking** - Using Retrofit and OkHttp to fetch currency exchange rates.

### App Features
* Convert a selection of Mass, Temperature, Distance and Curreny units
* Save a conversion to the history and restore it anytime
* Add an item to count
* Edit or remove a counter item

### Converter Screen
![converter-screen](https://user-images.githubusercontent.com/25524912/38684804-6e8cc4e6-3e68-11e8-9b4b-77f51a26fab7.jpg)

### Counter Screen
![counter-screen](https://user-images.githubusercontent.com/25524912/38684806-6ead042c-3e68-11e8-993f-c98ed15b9754.jpg)

### See it in Action
<img src="Numbers.gif" width="300"/>
Please ignore the weird gif pattern
