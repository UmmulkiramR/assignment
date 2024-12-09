# Assignment App Documentation

## Overview
The Assignment App is a Spring Boot-based REST application supporting basic CRUD operations for managing customers. It includes endpoints for creating, reading, updating, and deleting customer records and is configured to run locally using Docker or on a Minikube cluster with an in-memory database.

---

## Features
1. **CRUD Operations**:
    - Create a customer.
    - Retrieve all customers or a specific customer by ID.
    - Update a customer record.
    - Delete a customer record.

2. **Local Container Support**:
    - Easily run the application locally using `docker-compose`.

3. **Minikube Deployment**:
    - Deploy the application on a Minikube cluster with an in-memory database.

---

## Running the Application Locally with Docker
1. Ensure Docker is installed and running on your machine.
2. Navigate to the project root directory.
3. Run the following command to start the application:
   ```bash
   docker-compose up
4. You can check if the application is alive at: http://localhost:8087/assignment/alive

## Deploying the Application on Minikube

Follow these steps to deploy the application on a Minikube cluster:

Step 1: Create a Deployment:

```kubectl create deployment assignment-app --image=assignment_v```

Step 2: Build the Docker Image:

```docker build . -t assignment_v1```

Step 3: Load the Image into Minikube:

```minikube image load assignment_v1```

Step 4: Expose the Deployment:

```kubectl expose deployment assignment-app --type=NodePort --port=8087```

Step 5: Access the Application externally as a service:

```minikube service assignment-app --url```
This step will return a url in response like http://127.0.0.1:62532 - This will be the base url for all APIs


Notes

	1.	The application uses an in-memory database for quick setup and testing.
	2.	Ensure Minikube is installed and running correctly before starting the deployment process.
	3.	The browser access URL (http://127.0.0.1:<port>) is only available on the machine where Minikube is running.


## Troubleshooting

	•	Cannot access the service in the browser: Ensure Minikube is running and the deployment is exposed correctly.
	•	Docker image not found in Minikube: Verify that the image was built and loaded into Minikube using the correct name.

## Swagger
Swagger doc can be accessed here - http://localhost:8081/swagger-ui/index.html