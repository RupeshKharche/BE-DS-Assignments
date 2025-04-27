import socket
import time
from datetime import datetime

client_sockets = []
client_times = []

def start_server():
    host = '0.0.0.0'  # Listen on all interfaces
    port = 8080
    num_clients = 1  # Adjust for number of clients

    server_socket = socket.socket()
    server_socket.bind((host, port))
    server_socket.listen(5)
    print(f"Server started on {host}:{port}. Waiting for {num_clients} client(s)...")

    # Accept clients
    for i in range(num_clients):
        try:
            conn, addr = server_socket.accept()
            print(f"Client {i+1} connected from {addr}")
            client_sockets.append(conn)
        except socket.error as e:
            print(f"Error accepting connection: {e}")
            continue

    # Receive client times
    for conn in client_sockets:
        try:
            client_time = float(conn.recv(1024).decode())
            client_times.append(client_time)
            print(f"Received client time: {datetime.fromtimestamp(client_time)}")
        except socket.error as e:
            print(f"Error receiving time: {e}")
            continue

    # Get server's time
    master_time = time.time()
    print(f"Server (Master) time: {datetime.fromtimestamp(master_time)}")
    client_times.append(master_time)

    # Calculate average time
    average_time = sum(client_times) / len(client_times)
    print(f"Calculated average synchronized time: {datetime.fromtimestamp(average_time)}")

    # Send synchronized time to clients
    for conn in client_sockets:
        try:
            conn.send(str(average_time).encode())
        except socket.error as e:
            print(f"Error sending time: {e}")

    print("Synchronized time sent to all clients. Closing connections.")
    for conn in client_sockets:
        try:
            conn.close()
        except socket.error:
            pass
    server_socket.close()

if __name__ == '__main__':
    start_server()