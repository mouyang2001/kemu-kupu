### 14/09
- Met team members over messenger.
- Created the team contract.

### 15/09
- Met team members over zoom.
- We decided that my assignment 2 would be the best to work off as it was clean and functional.
- We split up the work and I volunteered to work on festival, concurrency and other back-end work as I had previous experience with concurrency.
- I extended my assignment 2 design to support React style lifecycle methods to help integrate the concurrency.

### 16/09
- I reviewed Breanna's design for the app.
- I started adding multithreading to the design.
- This proved difficult as I haven't worked with JavaFX concurrency before.
- Creating clean abstractions for concurrency was difficult as JavaFX requires all GUI events to be on a single thread which littered the codebase with calls to the GUI event queue.
- I knew that the bad abstractions would make it more difficult for my team members to work with my code so I decided to hold off merging it and look for other solutions.

### 17/09
- Breanna had difficulty understanding some of my code so I explained how the codebase worked so she could work with it.
- Breanna had difficulty integrating FXML into the existing code and Matthew decided to start a fresh project that we knew worked with FXML.
- This would require us to write some parts of the project again but we thought it would help us in the long run as my assignment 2 was not designed with FXML in mind.

### 18/09
- All team members made suggestions and feedback about the latest parts of the design.
- We finalised most of the design so we all knew what we wanted to achieve and therefore had a clear vision for the project.

### 19/09
- I spent a lot of time refactoring the codebase to have cleaner and well documented code.
- I have found FXML quite difficult to work with, especially the controllers as they are loaded via reflection. I will spend some time reading the documentation on it to help be understand it better.
- I made festival run in the background on a thread pool.

### 20/09
- I updated the word lists to make the topics show up nicely in our application.
- This made development easy as the application would only need the file names for the topics.
- I also corrected the words that did not have all of their macrons.
- Lots more refactoring and documentation to keep the code easy to work with.
- Having to refactor lots of code each day is slowing me down, I will think about how our process could improve in the future.

### 23/09
- More cleanup as documentation isn't always being updated.
- I updated the word lists based on new information about loan words from the clients.
- Fixed a bug that allows the user to rapidly click the sound button and make festival keep repeating the word for a long time, including after the question is answered.
- Fixed a bug that cleared the correct/incorrect message quickly if the user answered the next question before the last question cleared the message.
- This was difficult to fix as JavaFX doesn't support cancelling events and I had to extend the thread pool to support delayed execution.
- Added error handling to tell the user if something went wrong instead of the application just not working.
- Added work arounds for a number of bugs in festival that were discovered by classmates.

### 24/09
- After reflecting on how to improve how we write code I added a code formatter to GitHub that will automatically fix any style issues to reduce time spent fixing them manually.
- Fixed a number of FXML warnings.
- Fixed a few bugs that allowed the user to submit after the last question was answered but before the finish screen.
- This was introduced when we decided to not show the finish screen straight away so the user would know if they got the word correct. It may help us to think about the possible side effects of our changes in the future to avoid more bugs.
- The client meeting slots were opened and I took initiative to book a slot quickly so we could get a good time.

### 25/09
- After extensively testing our application I found that we could not type macrons into our application as we expected.
- I notified my teammates and we discussed how we could modify our application to better support the user.
- A solution was discovered and we decided to add setup instructions for a Māori keyboard and keep our macronise button for not so tech savvy users.

### 13/10
- There was lots of technical debt that had been building up over the assignment.
- I decided to make some large refactors to clean up the codebase and make our lives easier going forward.
- I also spent some time going over all the documentation in the codebase.

### 14/10
- I added the voice speed feature which was very easy to implement.
- I also added a topic search feature which was also very simple to implement.
- I did some more refactoring as some of the new code was very inefficient and untidy.

### 16/10
- I have spent most of my time refactoring code and documenting it, which should have been done in the first place.

### 17/10
- I have been adding small improvements to the application that have been brought up on the issue tracker.
- Lots of small style issues and consistency issues are very time consuming to fix and it may help to get everyone to find all the small inconsistencies and tackle them together.
