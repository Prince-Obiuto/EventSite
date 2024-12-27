package com.cit306.EventSite.httpserver.core;

import com.cit306.EventSite.http.HTTPStatusCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HTTPConnectionWorkerThread extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(HTTPConnectionWorkerThread.class);

    private final Socket socket;
    private final String webRoot;

    public HTTPConnectionWorkerThread(Socket socket, String webRoot){
        this.socket = socket;
        this.webRoot = webRoot; // Store webroot path
    }

    @Override
    public void run() {

        try (InputStream inputStream = socket.getInputStream(); OutputStream outputStream = socket.getOutputStream()) {

            String html = """
                    <!DOCTYPE html>
                    <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                            <title>Somclete Technologies</title>
                            <script src="https://cdn.tailwindcss.com"></script>
                            <style>
                                .fade-in {
                                    opacity: 0;
                                    transform: translateY(20px);
                                    transition: opacity 1s ease-out, transform 1s ease-out;
                                }
                    
                                .fade-in.show {
                                    opacity: 1;
                                    transform: translateY(0);
                                }
                    
                                .image-slider {
                                    position: relative;
                                    width: 100%;
                                    height: 300px;
                                    overflow: hidden;
                                }
                    
                                .image-slider img {
                                    position: absolute;
                                    width: 100%;
                                    height: 100%;
                                    object-fit: cover;
                                    opacity: 0;
                                    transition: opacity 1.5s ease-in-out;
                                }
                    
                                .image-slider img.active {
                                    opacity: 15;
                                }
                    
                                body {
                                    scroll-behavior: smooth;
                                }
                    
                                @keyframes scroll {
                                    0% { transform: translateY(0); }
                                    100% { transform: translateY(-10px); }
                                }
                    
                                .scroll-animate {
                                    animation: scroll 1.5s ease-in-out infinite alternate;
                                }
                            </style>
                            <script>
                                document.addEventListener('DOMContentLoaded', () => {
                                    // Fade-in animations
                                    const elements = document.querySelectorAll('.fade-in');
                                    elements.forEach((el, index) => {
                                        setTimeout(() => {
                                            el.classList.add('show');
                                        }, index * 300);
                                    });
                    
                                    // Image slider
                                    const images = document.querySelectorAll('.image-slider img');
                                    let currentImageIndex = 0;
                    
                                    setInterval(() => {
                                        images[currentImageIndex].classList.remove('active');
                                        currentImageIndex = (currentImageIndex + 1) % images.length;
                                        images[currentImageIndex].classList.add('active');
                                    }, 3000);
                                });
                            </script>
                        </head>
                        <body class="bg-gray-900 text-white font-sans">
                            <!-- Navbar -->
                            <header class="bg-gray-800">
                                <div class="container mx-auto flex justify-between items-center py-4 px-6">
                                    <div class="flex items-center space-x-4">
                                        <img src="https://via.placeholder.com/50" alt="Logo" class="w-10 h-10">
                                        <h1 class="text-2xl font-bold text-yellow-400">Somclete Technologies</h1>
                                    </div>
                                    <nav class="space-x-6">
                                        <a href="#about" class="text-white hover:text-yellow-400">About</a>
                                        <a href="#team" class="text-white hover:text-yellow-400">Team</a>
                                        <a href="#contact" class="text-white hover:text-yellow-400">Contact</a>
                                    </nav>
                                </div>
                            </header>
                    
                            <!-- Hero Section -->
                            <section class="bg-gradient-to-r from-yellow-500 to-yellow-300 py-20">
                                <div class="container mx-auto flex flex-col md:flex-row items-center">
                                    <div class="text-center md:text-left md:w-1/2 fade-in">
                                        <h2 class="text-4xl font-bold text-gray-900 scroll-animate">Empowering a Greener Future with Solar Innovation</h2>
                                        <p class="mt-4 text-gray-800">At Somclete Technologies, we leverage cutting-edge Web3 principles to revolutionize solar energy solutions.</p>
                                        <a href="#about" class="mt-6 inline-block px-8 py-3 bg-gray-900 text-yellow-400 font-semibold rounded-lg shadow-lg hover:bg-gray-800">Learn More</a>
                                    </div>
                                    <div class="md:w-1/2 image-slider">
                                        <img src="https://via.placeholder.com/600x300?text=Image+1" alt="Solar Innovation 1" class="active">
                                        <img src="https://via.placeholder.com/600x300?text=Image+2" alt="Solar Innovation 2">
                                        <img src="https://via.placeholder.com/600x300?text=Image+3" alt="Solar Innovation 3">
                                        <img src="https://via.placeholder.com/600x300?text=Image+4" alt="Solar Innovation 4">
                                        <img src="https://via.placeholder.com/600x300?text=Image+5" alt="Solar Innovation 5">
                                        <img src="https://via.placeholder.com/600x300?text=Image+6" alt="Solar Innovation 6">
                                        <img src="https://via.placeholder.com/600x300?text=Image+7" alt="Solar Innovation 7">
                                        <img src="https://via.placeholder.com/600x300?text=Image+8" alt="Solar Innovation 8">
                                    </div>
                                </div>
                            </section>
                    
                            <!-- About Section -->
                            <section id="about" class="py-20 bg-gray-900">
                                <div class="container mx-auto text-center fade-in">
                                    <h2 class="text-3xl font-bold text-yellow-400">About Us</h2>
                                    <p class="mt-4 text-gray-400">Somclete Technologies is a leading innovator in solar energy, integrating advanced Web3 technologies to deliver sustainable solutions. Our mission is to make renewable energy accessible and affordable for everyone.</p>
                                </div>
                            </section>
                    
                            <!-- Team Section -->
                            <section id="team" class="py-20 bg-gray-800">
                                <div class="container mx-auto text-center">
                                    <h2 class="text-3xl font-bold text-yellow-400 fade-in">Meet the Team</h2>
                                    <div class="mt-8 grid grid-cols-1 md:grid-cols-3 gap-6">
                                        <!-- CEO -->
                                        <div class="bg-gray-900 p-6 rounded-lg shadow-lg fade-in">
                                            <img src="https://via.placeholder.com/150" alt="CEO" class="w-24 h-24 mx-auto rounded-full">
                                            <h3 class="mt-4 text-xl font-semibold text-yellow-400">John Doe</h3>
                                            <p class="text-gray-400">CEO & Founder</p>
                                            <p class="mt-2 text-sm text-gray-500">Visionary leader driving sustainable energy solutions with Web3 technologies.</p>
                                        </div>
                                        <!-- CTO -->
                                        <div class="bg-gray-900 p-6 rounded-lg shadow-lg fade-in">
                                            <img src="https://via.placeholder.com/150" alt="CTO" class="w-24 h-24 mx-auto rounded-full">
                                            <h3 class="mt-4 text-xl font-semibold text-yellow-400">Jane Smith</h3>
                                            <p class="text-gray-400">Chief Technology Officer</p>
                                            <p class="mt-2 text-sm text-gray-500">Expert in blockchain and decentralized technologies for renewable energy.</p>
                                        </div>
                                        <!-- COO -->
                                        <div class="bg-gray-900 p-6 rounded-lg shadow-lg fade-in">
                                            <img src="https://via.placeholder.com/150" alt="COO" class="w-24 h-24 mx-auto rounded-full">
                                            <h3 class="mt-4 text-xl font-semibold text-yellow-400">Alice Johnson</h3>
                                            <p class="text-gray-400">Chief Operations Officer</p>
                                            <p class="mt-2 text-sm text-gray-500">Ensuring seamless operations to bring our solar solutions to life.</p>
                                        </div>
                                    </div>
                                </div>
                            </section>
                    
                            <!-- Contact Section -->
                            <section id="contact" class="py-20 bg-gray-900">
                                <div class="container mx-auto text-center fade-in">
                                    <h2 class="text-3xl font-bold text-yellow-400">Get in Touch</h2>
                                    <p class="mt-4 text-gray-400">Have questions or want to collaborate? Reach out to us!</p>
                                    <form class="mt-6 space-y-4 max-w-md mx-auto">
                                        <input type="text" placeholder="Your Name" class="w-full px-4 py-2 bg-gray-800 text-white rounded-lg focus:ring-2 focus:ring-yellow-400">
                                        <input type="email" placeholder="Your Email" class="w-full px-4 py-2 bg-gray-800 text-white rounded-lg focus:ring-2 focus:ring-yellow-400">
                                        <textarea placeholder="Your Message" rows="4" class="w-full px-4 py-2 bg-gray-800 text-white rounded-lg focus:ring-2 focus:ring-yellow-400"></textarea>
                                        <button type="submit" class="w-full px-4 py-2 bg-yellow-400 text-gray-900 font-semibold rounded-lg hover:bg-yellow-300">Send Message</button>
                                    </form>
                                </div>
                            </section>
                    
                            <!-- Footer -->
                            <footer class="bg-gray-800 py-6">
                                <div class="container mx-auto text-center text-gray-500">
                                    <p>&copy; 2024 Somclete Technologies. All rights reserved.</p>
                                </div>
                            </footer>
                        </body>
                    </html>""";

            final String CRLF = "\n\r"; //13, 10

            String response =
                    "HTTP/1.1 " + HTTPStatusCodes.SERVER_SUCCESS_200_OK + CRLF +  // Status Line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Type: " + " " + CRLF +
                            "Content-Length: " + html.getBytes().length + CRLF + // HEADER
                            CRLF +
                            html +
                            CRLF + CRLF;

            outputStream.write(response.getBytes());

            LOGGER.info("Connection processing finished{}", socket.getInetAddress());
        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
            throw new RuntimeException(e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    LOGGER.error("Error closing socket: ", e);
                }
            }
        }
    }
}