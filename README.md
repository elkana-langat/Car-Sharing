# Project: Car Sharing

Welcome to the Car Sharing project! This project aims to create a platform where customers can rent cars from various companies. Here's how the project works and the steps to set it up.

## Prerequisites

Before you begin, ensure you have the following:

- Java Development Kit (JDK)
- H2 database library
- IDE (Integrated Development Environment) for Java, such as Eclipse or IntelliJ

## Description

The Car Sharing project allows companies to offer car rentals to customers through a user-friendly platform. Previously, there was no interaction with potential customers. This project introduces a login system for customers, enabling them to rent cars conveniently.

## Objectives

### Main Menu

Update the main menu with these new options:

1. Log in as a manager
2. Log in as a customer
3. Create a customer
0. Exit

- Log in as a manager: Follow the existing process from the previous stage.
- Log in as a customer: Implement the customer login process.

### Customer Operations

1. If the "Create a customer" option is selected:
   - Prompt the user for a customer name.
   - Save the customer's name in the database.
   - Set `RENTED_CAR_ID` to NULL by default.

2. If the "Log in as a customer" option is chosen:
   - Display a list of existing customers and prompt the user to select one.
   - If no customers exist, print "The customer list is empty!" and return to the main menu.
   - If a customer is selected, display the customer menu:

Customer Menu:
1. Rent a car
2. Return a rented car
3. My rented car
0. Back

- If "My rented car" is chosen, display the rented car's name and its company. If no car is rented, print "You didn't rent a car!"

- To return a rented car, set the customer's `RENTED_CAR_ID` to NULL. Provide appropriate messages based on success or failure.

- To rent a car:
   - Display a list of available companies and prompt the user to choose one.
   - If no companies are available, print "The company list is empty!" and return to the customer menu.
   - If the customer already rented a car, print "You've already rented a car!"
   - Display available cars for the chosen company. Exclude rented cars.
   - Let the user select a car and display a confirmation message.
