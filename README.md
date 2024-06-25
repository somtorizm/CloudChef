# Client Cloud Chef Task App README

Welcome to the Client App! This repository contains a basic Android application and a local server designed to handle reliable, low-latency communication using Ktor. The system is capable of managing disconnections and reconnections effectively while maintaining a high fidelity of data exchange with top-notch performance.

## Problem Statement

**Objective**: Develop a basic Android application that communicates with a local server using a reliable, low-latency communication protocol. This system should be capable of handling disconnections and reconnections effectively, maintaining a high fidelity of data exchange.

### Key Requirements

1. **Android Application**:
    - The app maintains an active connection with the server using a WebSocket-based communication protocol, ensuring each message is delivered only once, eliminating any possibility of duplicate messages, with low latency, and maintaining the order of message delivery.
    - Every 2 seconds, the server sends a message to the app, which the app must acknowledge upon receiving.
    - The application keeps the front camera of the device active for scanning QR/barcodes in parallel with receiving messages.
    - The server handles connections, disconnections, and reconnections without data loss or delay beyond a 5-second threshold.
    
2. **Server**:
    - A basic server using Ktor sends messages to the connected Android app every 2 seconds.
    - The server handles connections, disconnections, and reconnections without data loss or delay beyond a 5-second threshold.
    
3. **Disconnection and Reconnection Handling**:
    - The communication protocol ensures messages are stored until they are confirmed as received by the app, handling intermittent connectivity and ensuring no messages are lost during disconnections.
    
4. **Performance Constraints**:
    - The delay from message sending to receiving should not exceed 1 second, ensuring real-time performance crucial for kitchen operations.

### Technical Stack (Suggested)

- Android SDK, Java, Kotlin for the app development.
- Ktor framework for the server development.
- Consideration of IoT communication techniques.

## Outcome

- **Functional Android Application and Server**: A fully functional Android application and server setup that demonstrates efficient, reliable communication under specified conditions.
- **Demonstration Video**: A screencast showcasing the functionality of the app, including real-time message handling, QR/barcode scanning, and system resilience during network fluctuations.

## Features

- Active WebSocket connection for reliable, low-latency communication.
- Regular server-to-client messaging every 2 seconds.
- Front camera activation for QR/barcode scanning.
- Robust handling of disconnections and reconnections without data loss or significant delay.

**Note**: Storing unique messages for each user ID is not implemented due to limitations in identifying connected users in each session.

## Installation

### Prerequisites

- Android Studio installed on your development machine.
- Ktor installed for the server.

### Steps

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/somtorizm/CloudChef.git
    cd client-app
    ```

2. **Android Application**:
    - Open the project in Android Studio.
    - Build and run the app on your Android device or emulator.

## Usage

1. **Start the Server**:
    - Ensure the server is running and ready to send messages every 2 seconds.
    - Ensure you update the client.webSocketSession url to your local server ip.

2. **Run the Android App**:
    - Open the app on your device or emulator.
    - The app will connect to the server and start receiving messages while keeping the front camera active for QR/barcode scanning.

## Demonstration Video

- [Demo Video Link](https://yourvideolink.com) - A screencast showcasing the app functionality, including real-time message handling, QR/barcode scanning, and resilience during network fluctuations.

---

Feel free to update any links or specific details according to your project setup.
