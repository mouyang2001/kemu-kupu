Tuesday 14/09
Received group allocations on Canvas. I took initiative and found the others on facebook and added them to a messenger group chat so that we could communicate. I also created a shared drive on google drive and we used this to fill out the team contract.

Wednesday 15/09
Organised a zoom meeting where we decided on whose assignment 2 we were going to work off. Callum’s code was the cleanest and most advanced so we decided on his. We also decided on how we were going to divide the work: I would do the front-end and design work, Matthew would implement the majority of the functionality and Callum would do back-end work including java concurrency. After this meeting I designed how the app would flow between screens and what each screen would look like. 

Thursday 16/09
The others looked over my design and I made a few edits according to their feedback, and we set up a github repository for our work.

Friday 17/09
I started working on implementing the code for the design. We discovered that Callum’s code wasn’t designed in a way that made for easy integration with javafx and scenebuilder, so Matthew changed several of the classes.

Saturday 18/09
I implemented the code for the menu and topic choice screens, and also created classes for all the other scenes we would need. On feedback from the group, I cut down the number of scenes that we would use by implementing some conditional functionality. 

Sunday 19/09
We were having a bit of difficulty creating the quiz functionality, so we decided to implement another class to control the quiz and handle the word selection.

Monday 20/09
I finished implementing the design functionality, including having correct/incorrect pop up for a few seconds after submitting a question, an image button to click to replay the sound and some general fix-ups in positioning and sizing. 

Wednesday 22/09
On reviewing the project I found that you did not get a correct/incorrect message on the final question you entered, and festival didn't automatically re-say the word on your second attempt, so I corrected both of these issues.




Wednesday 6/10
We had a group meeting to discuss how we would take the project further. We identified several key features to implement, and each took a few to work on. I will be working on showing the numbers of letters in the word to spell, and on improving the finish screen with time based scoring and statistics.

Thursday 7/10
I implemented the functionality for showing how many letters are in the word in the quiz module. This is currently done by having a label overlaying the textfield input. If I have time I would like to improve this by having the underscores dissapear as you type each letter so that it is more intuitive. I will also need to implement this functionality in the practice module, but I had to wait for Matthew to do the bulk of the set up for that class.

Friday 8/10
I started implementing the stats. I started by adding some lists in the quiz game class to record whether you got each word right or wrong. I then added functionality to record how long you took to answer the question, and changed the scoring system based on this time (i.e. the faster you answer, the better your score).

Saturday 9/10
Matthew improved the time based scoring using a linear decay function. I completed the stats implementation by storing the most recent quiz in a file and the information is then displayed in the table.

Tuesday 12/10
I added the show letters functionality to the practise module, and created a new word list that contains all words. I then found copyright free audio clips and added them to the app for getting questions correct, incorrect and finishing the quiz.

Wednesday 13/10
Responding to some peer feedback we recieved from assignment 4, I added a menu button in the quit that would exit you out of the current quiz. I also added tooltips to better explain the function of some buttons. 

Thursday 14/10
I implemented a leaderboard functionality. This uses a mix of objects and files to so that the player can view scores from previous games (i.e. it doesn't delete when you exit the game), and it allows player to type in their name to save their score if they reach a high score.
