# Project Overview

## Server Architecture

### Single Server Setup
- The server operates as a single entity, accepting connections from clients.

### Thread-based Parallel Processing
- Utilizes thread-based parallel processing to handle multiple socket connections simultaneously.
- Threads are assigned specific tasks, enabling efficient communication through designated buffers.

## Client Interaction

### Socket Communication
- Clients connect to the server using socket connections.
- ObjectOutputStream and ObjectInputStream facilitate communication with the server.
- Upon completion, clients close the socket connection.

## Authentication Mechanism

### User Account Requirement
- API access is restricted until users create an account or log in.
- Session ID is stored for each login.

### Authorization Check
- Verifies the authorization of a user before executing write API tasks.
- Example: Seller ID is stored during the seller's first login, and subsequent requests are checked against this ID for authorization.

## Item Management

### Keywords and Search
- Each item is associated with a set of keywords stored in the database.
- Searches involve intersecting keyword sets to respond with relevant items.

![Screenshot 2024-02-02 at 2.38.22 PM.png](..%2F..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2Fpm%2Fwjzzl6dx7nj4bgqy4nywh1140000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_exBOaP%2FScreenshot%202024-02-02%20at%202.38.22%20PM.png)

## Database Interaction

### JDBC Connector
- Initial interaction with the database involves a socket connection, utilizing JDBC Java connector.
- All client request handlers reuse the same database connection to optimize connection costs.

## Inactivity Handling

### Timer Demon for Sessions
- A Timer Demon is created for each client connection session.
- Two timeouts: 3-minute warning and 5-minute logout.
- Session timer resets upon each client request, preventing inactivity triggers.
- Warning sent to the client after 3 minutes of inactivity.
- Socket connection closed after 5 minutes of inactivity.

### Warning and Logout Notifications
- Server writes a warning to the client using OutputStream after a 3-minute inactivity period.
- Client is automatically logged out if inactive for 5 minutes.

## Dependencies

- Java
- JDBC Connector
- Git

## Setup Instructions

1. Clone the repository.
2. Install Java8 dependency
3. Configure database connection parameters.
4. Run the server.java and client.java to test with user inputs and observer logging
5. Use BuyerServer, ClientServer, BuyerClient, SellerClient to auto test the code
6. Use SellerTest, BuyerTest to test the cod at Scale
