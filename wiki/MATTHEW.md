# Matthew Ouyang Journey Log

This is for documenting development of project.


### Tue 14/09/2021
Found out team members. Collaborated on making team contract and signing.

### Wed 15/09/2021
Got introduced to team members. Met through Zoom developed a strategy to tackle assignment 3. Decided on who's codebase to build off, and we chose Callum's as was the most reboust and had potential. We designated peoples to different tasks. Frontend, Architecture and Backend festival tasks. Then we went about achieving those tasks.

### Thu 16/09/2021
A design and color scheme on figma by Breanna. I provided some feedback and edits that we all unanomously agreed upon before starting development.

### Fri 17/09/2021
Breanna tried implement fxml and css style sheets archiecture to Callum's A2 code. This endevour proved difficult. I took the iniative to delete everything and start from scratch. My thinking was that each scene required 3 files, a controller java class, fxml sheet, and css stylesheet. This structure is best as it allows for easy collaboration as it decouples scenes from one another. I quickly drafted up a Menu and Topic scenes so that I could show the others my thinking. We agreed upon this and decided to move forward with this structure.

### Sun 19/09/2021
Completed some scene fxml layouts. Implement list view so that we can select from the range of Maori word topics and transfer data to other scenes. Also implemented the quiz game functionality. I dicusssed with the group about how to best do this. I recommended a QuizGame class that could be instantiated on Quiz scene creation. This is because I wanted to decouple the game logic from the scene controller as the controller sole purpose should be interacting with the GUI. The others agreed on this decision and I was able to complete it. This development was pivotal as it stitches together all work that the others have done together.

### Mon 20/09/2021
I added a enter button key released listener on the input textfield to run the spell check routine. This enables both submit and enter buttons to work in place of each other, so you don't have to use the mouse. I now await for stakeholder information in tommorrows tuesday lecture.

### Fri 25/09/2021
I improved the feedback prompt messages. Then I added a dynamically altering final message on the finish scene. I was then tasked by the group to implement a macron button for people who aren't tech savvy to macronize the letters without the need of installing a maori keyboard. Finally I improved the layout and styling of the topic scene, improving it's color scheme.
