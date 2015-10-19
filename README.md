# Athena
The cornerstone of the new SaaS system.

# How to set up the development environment?
- After `git clone` this project into you local directory, run `./gradlew build` will automatically resolve all the dependencies and build the whole system.

- You can also build a specific subproject with running `./gradlew <subproject-path>`, for example, run `./gradlew :core:account:build` will build the *account* subproject only.

- To develop this project in IntelliJ IDEA, just run `./gradlew cleanIdea idea` at the root directory, which will generate the IDEA project file. Open it with IDEA, and there you go.
