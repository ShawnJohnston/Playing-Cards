Shawn Johnston
ChooseShawnJohnston@gmail.com

Playing Card Project

Objective

    This mini-project is designed to be a demonstration of basic Object-Oriented Programming using Java. It will start as a 
    console application and eventually be refactored to support a GUI using Swing.

Setup
    
    1.  The first stage of this demonstration is to build a working deck of playing cards. The deck will contain 52
        cards, as is standard, along with the ability to include Jokers into the deck. The deck of cards is divided into
        4 suits, then subdivided into 13 values.
    2.  The second stage is to include a couple of methods of shuffling the deck. The first method will be a simple
        computerized randomization of the deck. The second method will be designed to simulate a hand-shuffle, using the
        riffle-riffle-box-riffle procedure. Small amounts of computerized randomization will be used within each step as necessary.
    3.  The third stage is to deal cards to a player and a dealer. To keep this demo simple, a basic 5-card stud game will
        be implemented for now (The player's 5 cards vs. the dealer's 5-cards). Standard poker ranks will be used and both
        hands will be evaluated for them. From weakest to strongest, they are:
        High Card -> Pair -> Two Pair -> Trips -> Straight -> Flush -> Full House -> Quads -> Straight Flush -> Royal Flush
    4.  The fourth stage will be implement a GUI using Swing.

Current Status of this Project

    This project is in stage 3 of the preceding design plan.

    At this time, the project will be put on hiatus in lieu of the start of the Spring semester. Development of this project will 
    resume in Summer 2021.
    
    The project currently exists as a collection of components. A poker game cannot be played at this time. Some components
    for game scenarios exist in a partially built form. A GUI was intended to be designed along side a console menu. Because
    these components are not finished, the project can't actually be "played" yet. Below is a brief description of some of the
    key components of the most up-to-date build.
    
    Playing card:
        This class will represent a playing card and encompasses the necessary elements that describe one. A card can have a value, 
        suit, color, can be face-up or face-down, etc. The cards do not have images for visual display yet.
    Deck of cards:
        A deck of cards containing 52 cards, plus Jokers if desired. The deck of cards builds itself upon instantiation by filling
        an array with every playing card that must be included. The playing cards are assigned in order of suit, then value.
    Shuffler:
        This class can shuffle a deck of cards, either by using a simple randomization method or by using a hand shuffling method
        that is indented to replicate a realistic shuffle.
    Hand evaluator:
        This class can read a 5-card hand and assign a poker rank to it. Identification of Straights, Flushes, Straight 
        Flushes, and Royal Flushes have been tested rigorously and confirmed to be operational. Pairs, Two Pairs, Trips,
        and Quads have a method for identification but have not been tested for efficacy.
    Game outcome:
        This class takes in two player hands, compares them, and determines a winner, loser, or tie. Currently, it only
        compares by ranking. In the even of equivalent ranks, more methods will need to be written to consider the values
        within the hand beyond just simply the rank.

Programming techniques used
    
    1.  Object-oriented programming design. Classes, constructors, overloaded constructors, getters, setters, class methods. Privatization of internal features. 
    2.  While-loop to control flow through the application, itself controlled by a boolean variable.
    3.  If-else statements, switch statements, and nesting of these for conditional control throughout the aplication.
    4.  For-loops and for-each loops for various purposes throughout the application, including loop nesting.
    5.  Use of counters to aid in conditional control.
    6.  Helper methods within consoleMenu class to procedurally build menus.
    7.  Exception handling for invalid inputs in consoleMenu.
    8.  Enumerator states in PlayingCard class. A card card either be face-up or face-down.
    9.  Use of arrays and array lists for storing related information, as well as procedural generation of things such as a sorted deck of cards and poker hands.
    10. Sorting of a poker hand. A method using a 2D for-loop compares each card in the hand to the value in index i of an array of playing card values.
    11. Tail recursion used in the Shuffler class.
    12. Java's Random class used for the random method in Shuffler, and to support secondary randomization in the handShuffle method.
    13. Unit testing using JUnit.
