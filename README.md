# Ladders Game & Monopoly
STUDENT NAMES = "Gilianne Kate Alivia Reyes", "Trang Minh Duong"  
GROUP = "0011"

This project was developed as a part of the course IDATT2003 (Programming 2), which is a part of the Bachelor's program in Computer Science at NTNU. *Ladders Game & Monopoly* is a Java-based application for users to play Ladders Game or Monopoly with customisation.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Build and Run](#build-and-run)
- [Testing](#testing)
- [License](#license)

---

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/giliannereyes/idatt2003-portfolio-2025-group11.git

2. Ensure Maven is installed on your system. You can install it [here](https://maven.apache.org/download.cgi)
3. Build the project:
   ```bash
   mvn clean install

---

## Usage

*Ladders Game & Monopoly* allows you to customise the classical board games effortlessly through a user-friendly application. The application provides the following features:

### 1. **Game Selection Page**
After starting the application, you will be presented with the Game Selection View. Here you can choose between either **Ladders Game** or **Monopoly**. Choosing either of the boardgame options will lead to the "Player Setup"-page. Here the user can choose from:
1. **Loading a predefined player configuration.** Then the user can register the configuration to start playing.
2. **Customising the player configuration.** The user can choose from minimum two to maximum five players, each with their own customisable name and colour (red, blue, purple, orange, and green). The user can then save and/or register the configuration.

### 2. **Ladders Game**:
If the user has initially chosen **Ladders Game**, the user can choose either loading a predefined board configuration or one of the three default boards (Easy, Medium, and Hard) after registering the player configuration. The player can then choose to save the board configuration if they wish to. After the board configuration can the user click on the "Start Game"-button to start the game.
### 3. **Monopoly**:
If the user has initially chosen **Monopoly**, similar to **Ladders Game**, the user can choose either loading a predefined board configuration or one of the two default boards (Normal and Quick) after registering the player configuration. The following actions are exactly like that in **Ladders Game**: the user can choose to save the board configuration, and then start the game by clicking on the "Start Game"-button.

### **Example usage**:
1. **Run the application**:
   ```bash
   java -jar target/boardgame-idatt2003-1.0-SNAPSHOT.jar
   ```
   The application will display the main Game Selection Page.


2. **Ladders Game with a predefined player and board configuration**:
   - Select **Ladders Game** on the Game Selection View.
   - Choose "Load Player Configuration from CSV" and choose the CSV file accordingly. Then click "Register Player Configuration".
   - Choose "Load Board from JSON" and choose the JSON file accordingly.
   - Click "Start Game" to start playing with the predefined configuration.

3. **Monopoly with a customised player and board configuration**:
   - Select **Monopoly** on the Game Selection View.
   - Choose the numbers of players and their name and colour. To save the player configuration for later, choose "Save Player Configuration to CSV". Then click "Register Player Configuration".
   - Choose one of the default board modes, Normal or Quick (Easy, Medium, or Hard for **Ladders Game**).
   - Press "Start Game" to start playing with the customised configuration.

   When the game has started, the players can click on the text "Click for User Manual" to view User Manual.
---

## Build and Run
1. Build the application: Compile and build the project using Maven:
   ```bash
   mvn clean install
   ```
   this will generate a JAR file in the ```target``` directory

2. Run the Application: Execute the JAR file:
   ```bash
   java -jar target/boardgame-idatt2003-1.0-SNAPSHOT.jar
   ```
   alternatively
   ```bash
      mvn clean compile exec:java -Dexec.mainClass="no.ntnu.idatt.app.BoardGameApp"
     ```
   or
      ```bash
      mvn javafx:run
     ```
---

## Testing
1. Run all test using Maven:
   ```bash
   mvn test


2. Unit tests are included to ensure the stability of the application. The following tools are used:
- **Junit 5**: For writing and running unit tests.

---

## License
This project is licensed under the following:
1. [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0): Covers Apache Commons CSV and Google Gson libraries.
2. [Eclipse Public License 2.0](https://github.com/junit-team/junit5/blob/main/LICENSE.md): Covers Junit 5.
3. [GNU General Public License v2.0](https://github.com/openjdk/jfx/blob/jfx23/LICENSE): Covers JavaFX.
