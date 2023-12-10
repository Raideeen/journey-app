# Journey

Journey is an Android application developed in Kotlin and Java. The application allows users to
create and save their own stories written in markdown and includes a database of countries fetched
from the [REST Countries API](https://restcountries.com/).
It uses Room for local data storage and Espresso for UI testing.

## Installation

Clone this repository and import into **Android Studio**

```bash
git clone https://github.com/Raideeen/journey-app.git
```

## Usage

After installing the application, users can log in to access their personal notebook. They can
create and save their own stories. The application also includes a database of countries that users
can explore.
The login page is made with hard-coded credentials for demonstration purposes. The username
is `user` and the password is `1234`.

## Running the tests

To run the existing unit tests, use the following command:

```bash
./gradlew test
```

To run the existing UI tests, use the following command:

```bash
./gradlew connectedAndroidTest
```

## Dependencies

The project is built with the following dependencies:

- [Kotlin](https://kotlinlang.org/) - The main programming language
- [Java](https://www.java.com/) - Used for some parts of the application
- [Gradle](https://gradle.org/) - Dependency Management
- [Room](https://developer.android.com/training/data-storage/room) - Used for local data storage
- [Espresso](https://developer.android.com/training/testing/espresso) - Used for UI testing
- [Retrofit](https://square.github.io/retrofit/) - Used for API calls
- [Markwon](https://noties.io/Markwon/) - Used to display markdown text

## License

This project is licensed under the MIT License - see
the [LICENSE.md](https://github.com/Raideeen/journey-app/blob/main/LICENSE) file for details

## Contact Information

For any questions or discussions about the project, please reach out to us
at `adrien.djebar@proton.me`.

## Acknowledgments

This simple application was developed as part of the Android course at the École supérieure
d'ingénieurs Léonard-de-Vinci (ESILV) located at Paris La Défense.

We would like to thanks our Android
professor, [Arnaud Chirat](linkedin.com/in/arnaud-chirat-30b58413a), for his guidance and support.


Thanks to all the contributors of the libraries used in this project.
