# movieproject
This is a colorful app that showcases all the popular and trending movies as part of Udacity's Android Developer Nanodegree.

## Features

With the app, you can:
* Discover the most popular, the most rated or the highest rated movies
* Save favorite movies locally to view them even when offline
* Watch trailers
* Read reviews

## How to Work with the Source

This app uses [The Movie Database](https://www.themoviedb.org/documentation/api) API to retrieve movies.
You must provide your own API key in order to build the app. When you get it, just paste it to:
    ```
    app/build.gradle
    ```

    API_KEY = "xxxx"
    //xxxx to whatever your API key is.


## Screenshots

![screen](drawable/screenone.png)

![screen](drawable/screentwo.png)


## Libraries


* [Retrofit](https://github.com/square/retrofit)
* [Picasso](http://square.github.io/picasso/)



## License

MIT License

Copyright (c) 2018 Sam Guan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.