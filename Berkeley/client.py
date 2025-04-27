import socket
import time
import random
from datetime import datetime

def start_client():
    host = '127.0.0.1'  # Replace with server’s IP
    port = 8080

    client_socket = socket.socket()
    try:
        client_socket.connect((host, port))
    except socket.error as e:
        print(f"Failed to connect to server: {e}")
        return

    local_time = time.time() + random.uniform(-5, 5)
    print(f"Client local time (before sync): {datetime.fromtimestamp(local_time)}")

    try:
        client_socket.send(str(local_time).encode())
        sync_time = float(client_socket.recv(1024).decode())
        print(f"Synchronized time received from server: {datetime.fromtimestamp(sync_time)}")
    except socket.error as e:
        print(f"Error communicating with server: {e}")
    finally:
        client_socket.close()

if __name__ == '__main__':
    start_client()