# EventSite HTTP Server

EventSite is a custom-built HTTP server written in Java. This project serves as a foundational learning tool for understanding the core principles of how HTTP servers work, while also providing basic functionality for handling client requests and serving responses.

## Features

- **Custom HTTP Server**: Implements a basic HTTP server using `ServerSocket`.
- **Request Parsing**: Reads and parses HTTP requests to extract method, URI, headers, and body.
- **Response Handling**: Sends structured HTTP responses with appropriate status codes and headers.
- **Threaded Connections**: Handles multiple client connections concurrently using threading.
- **Dynamic Routing**: Maps specific URIs to handlers for dynamic response generation.
- **Static File Serving**: Serves static resources like HTML, CSS, and images (planned).

## Project Structure

```
EventSite/
├── .idea/               # IDE configuration files
├── WebRoot/             # Web resources (HTML, CSS, JS)
├── src/                 # Java source code
├── pom.xml              # Maven configuration file
├── Request.txt          # Example on HTTP requests
└── .gitignore           # Git ignored files and directories
```

## Getting Started

### Prerequisites

- **Java**: Ensure you have Java 8 or later installed.
- **Maven**: Required for managing dependencies and building the project.

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Prince-Obiuto/EventSite.git
   cd EventSite
   ```

2. Build the project using Maven:
   ```bash
   mvn clean install
   ```

3. Run the server:
   ```bash
   java -jar target/EventSite-1.0-SNAPSHOT.jar
   ```

4. Access the server in your browser or using a tool like `curl`:
   ```bash
   curl http://localhost:8080
   ```

## How It Works

1. **Server Initialization**:
   - The server listens on a specified port (default: 8080) for incoming client connections.

2. **Request Handling**:
   - Reads the HTTP request line and headers from the client.
   - Parses the method (GET, POST, etc.) and URI to determine the appropriate response.

3. **Response Generation**:
   - Constructs HTTP responses with status codes, headers, and optional body content.

4. **Threading**:
   - Each client connection is handled in a separate thread to support multiple simultaneous requests.

## Roadmap

- [ ] Complete request parsing for headers and body.
- [ ] Implement dynamic routing with handlers for different endpoints.
- [ ] Add static file serving from the `WebRoot` directory.
- [ ] Improve error handling and return proper HTTP status codes.
- [ ] Introduce logging for requests and responses.
- [ ] Add HTTPS support using SSL.

## Contributing

Contributions are welcome! If you'd like to improve the server or add new features, feel free to submit a pull request.

### Steps to Contribute:
1. Fork the repository.
2. Create a new branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes and push the branch:
   ```bash
   git push origin feature-name
   ```
4. Open a pull request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Acknowledgments

- **Java Networking**: Inspired by foundational concepts in Java's `java.net` package.
- **HTTP Protocol**: Guided by the HTTP/1.1 specification for request and response handling.

---

Feel free to explore the project and modify it to suit your needs. If you encounter any issues or have questions, open an issue on GitHub or reach out to me directly.
