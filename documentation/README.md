# US047 - Hosting System on Tomcat Server and Connecting to Database Container

## Table of Contents

- [1. Requirements](#1-requirements)
- [2. Step-by-step guide](#2-step-by-step-guide)
  - [Docker Configuration](#docker-configuration)
    - [Explanation of Dockerfile-be](#explanation-of-dockerfile-be)
      - [Build Stage](#build-stage)
      - [Deployment Stage](#deployment-stage)
    - [Explanation of Dockerfile-fe](#explanation-of-dockerfile-fe)
      - [Build Stage](#build-stage-1)
      - [Deployment Stage](#deployment-stage-1)
    - [Explanation of docker-compose.yml](#explanation-of-docker-composeyml)
      - [Services](#services)
      - [Volumes](#volumes)
    - [Explanation of nginx.conf](#explanation-of-nginxconf)
      - [Configuration Breakdown](#configuration-breakdown)


## 1. Requirements

As Product Owner, I want the system to be hosted in a tomcat server and
connect to a database container to persist information.

Requirements for hosting the system on a Tomcat server and connecting it to a database container:

- The system must be hosted on a server running Apache Tomcat (to be able to deploy the system as a WAR file).
- The system must be connected to a MySQL database running within a separate container.
- The system must be able to retrieve and persist data in the database container.


## 2. Step-by-step guide

#### Docker Configuration

- **Dockerfile-be:** Configures the back-end application to run on Tomcat.
- **Dockerfile-fe:** Configures the front-end application.
- **docker-compose.yml:** Manages multi-container Docker applications, including the web application and the database.

### Explanation of Dockerfile-be

This `Dockerfile-be` is responsible for building and packaging the backend application, then deploying it to a Tomcat server container. Here is a detailed breakdown of each section and its use in the context of User Story 47:

#### Build Stage

```dockerfile
FROM maven:3.8.1-openjdk-17 AS build

ENV MAVEN_OPTS="-Xmx2g"

WORKDIR /app

COPY .. .

RUN mvn clean package -DskipTests
```

1. **Base Image:**
   ```dockerfile
   FROM maven:3.8.1-openjdk-17 AS build
   ```
    - This line specifies that the build stage will use the Maven 3.8.1 with OpenJDK 17 image. This image is pre-configured with Maven and JDK, suitable for building Java applications.

2. **Environment Variable:**
   ```dockerfile
   ENV MAVEN_OPTS="-Xmx2g"
   ```
    - Sets the Maven options to increase the maximum heap size to 2GB, which helps prevent memory-related issues during the build process in DEI Virtual Servers Private Cloud.

3. **Working Directory:**
   ```dockerfile
   WORKDIR /app
   ```
    - Sets the working directory inside the container to `/app`, where the application files will be copied and built.

4. **Copying Files:**
   ```dockerfile
   COPY . .
   ```
    - Copies all files from the current directory on the host machine to the `/app` directory in the container.

5. **Build Application:**
   ```dockerfile
   RUN mvn clean package
   ```
    - Runs the Maven command to clean and package the application, skipping tests for faster builds. The result is a WAR file located in the `target` directory.
    - Since the default method for packaging a Spring Boot application is to create an executable JAR file, the `pom.xml` file must be modified to generate a WAR file instead. 
This can be done by changing the packaging type to `war` in the `pom.xml` file.
```xml
<groupId>org.switch2023</groupId>
<artifactId>switch2023project_g2</artifactId>
<name>switch-2023 switch-2023-g2</name>
<version>1.0-SNAPSHOT</version>
<packaging>war</packaging>
```

#### Deployment Stage
```dockerfile
FROM tomcat:10.0

RUN mv /usr/local/tomcat/webapps.dist /usr/local/tomcat/webapps

COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/smarthome.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
```

#### Deployment Stage

1. **Base Image:**
   ```dockerfile
   FROM tomcat:10.0
   ```
    - Tomcat 10 provides better support for the latest Java versions, ensuring compatibility with our system developed in Java 17.

2. **Move Default Webapps:**
   ```dockerfile
   RUN mv /usr/local/tomcat/webapps.dist /usr/local/tomcat/webapps
   ```
    - Moves the default web applications provided by Tomcat to the standard `webapps` directory.

3. **Copy WAR File:**
   ```dockerfile
   COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/smarthome.war
   ```
    - Copies the WAR file generated in the build stage into the `webapps` directory of Tomcat, renaming it to `smarthome.war`.

4. **Expose Port:**
   ```dockerfile
   EXPOSE 8080
   ```
    - Exposes port 8080 on the container, which is the default port for Tomcat to serve applications.

5. **Run Tomcat:**
   ```dockerfile
   CMD ["catalina.sh", "run"]
   ```
    - Sets the default command to run when the container starts, which is to start Tomcat using `catalina.sh`.

### Explanation of Dockerfile-fe
This `Dockerfile-fe` is responsible for building and packaging the front-end application, then deploying it to a Nginx server container.

#### Build Stage
```dockerfile
FROM node:alpine AS build

WORKDIR /app

COPY ./frontendgrupo2/package*.json ./

RUN npm install

COPY ./frontendgrupo2 ./

RUN npm run build -- --mode production
```

1. **Base Image:**
   ```dockerfile
   FROM node:alpine AS build
   ```
    - This line specifies that the build stage will use the Node.js image based on Alpine Linux, which is a lightweight and secure base image.

2. **Working Directory:**
   ```dockerfile
   WORKDIR /app
   ```
    - Sets the working directory inside the container to `/app`, where the application files will be copied and built.

3. **Copying Package Files:**
   ```dockerfile
   COPY ./frontendgrupo2/package*.json ./
   ```
    - Copies the `package.json` and `package-lock.json` files from the `frontendgrupo2` directory on the host machine to the working directory in the container.

4. **Install Dependencies:**
   ```dockerfile
   RUN npm install
   ```
    - Installs the npm dependencies listed in the `package.json` file.

5. **Copying Source Files:**
   ```dockerfile
   COPY ./frontendgrupo2 ./
   ```
    - Copies all the source files from the `frontendgrupo2` directory on the host machine to the working directory in the container.

6. **Build Application:**
   ```dockerfile
   RUN npm run build -- --mode production
   ```
    - Runs the npm command to build the application in production mode, generating optimized static files in the `dist` directory.
```bash
# Set the API base URL for the production environment (
VITE_API_BASE_URL=http://<ip adress>/api
```

#### Deployment Stage
```dockerfile
FROM nginx:alpine

COPY --from=build /app/dist /usr/share/nginx/html

COPY ./nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
```

1. **Base Image:**
   ```dockerfile
   FROM nginx:alpine
   ```
    - Uses the Nginx image based on Alpine Linux as the base for the final container. Nginx is a high-performance web server suitable for serving static files.

2. **Copy Built Files:**
   ```dockerfile
   COPY --from=build /app/dist /usr/share/nginx/html
   ```
    - Copies the built static files from the `dist` directory in the build stage to the default directory for Nginx to serve static files.

3. **Copy Nginx Configuration:**
   ```dockerfile
   COPY ./nginx.conf /etc/nginx/conf.d/default.conf
   ```
    - Copies the custom Nginx configuration file from the host machine to the Nginx configuration directory in the container.

4. **Expose Port:**
   ```dockerfile
   EXPOSE 80
   ```
    - Exposes port 80 on the container, which is the default port for Nginx to serve HTTP traffic.


### Explanation of docker-compose.yml

The `docker-compose.yml` file is used to define and run multi-container Docker applications. 
In this case, it sets up the services required for the Smart Home project, including a database, backend, frontend, and a database management tool. 
Here is a detailed breakdown of each section:

```yaml
version: '3.8'

services:

  db:
    image: mariadb
    container_name: my_database
    ports:
      - "3306:3306"
    environment:
      MARIADB_ROOT_PASSWORD: 123
      MARIADB_DATABASE: smarthomedb
      MARIADB_USER: user
      MARIADB_PASSWORD: 123
    volumes:
      - db_data:/var/lib/mysql
    healthcheck:
      test:
        [
          "CMD",
          "healthcheck.sh",
          "--su-mysql",
          "--connect",
          "--innodb_initialized"
        ]
      timeout: 10s

  adminer:
    image: adminer
    container_name: my_adminer
    ports:
      - "8282:8080"

  smarthome_be:
    build:
      context: .
      dockerfile: Dockerfile-be
    container_name: smarthome_be
    environment:
        SPRING_DATASOURCE_URL: jdbc:mariadb://db:3306/smarthomedb
        SPRING_DATASOURCE_USERNAME: user
        SPRING_DATASOURCE_PASSWORD: 123
        SPRING_JPA_HIBERNATE_DDL_AUTO: create
        SPRING_DATASOURCE_DRIVER_CLASS_NAME: org.mariadb.jdbc.Driver
        SPRING_PROFILES_ACTIVE: dev
    ports:
      - "8080:8080"
    depends_on:
        db:
            condition: service_healthy

  smarthome_fe:
    build:
      context: .
      dockerfile: Dockerfile-fe
    container_name: smarthome_fe
    ports:
      - "80:80"
    depends_on:
      - smarthome_be

volumes:
  db_data:
```

#### Services

1. **Database (db):**
   ```yaml
   db:
     image: mariadb
     container_name: my_database
     ports:
       - "3306:3306"
     environment:
       MARIADB_ROOT_PASSWORD: 123
       MARIADB_DATABASE: smarthomedb
       MARIADB_USER: user
       MARIADB_PASSWORD: 123
     volumes:
       - db_data:/var/lib/mysql
     healthcheck:
       test:
         [
           "CMD",
           "healthcheck.sh",
           "--su-mysql",
           "--connect",
           "--innodb_initialized"
         ]
       timeout: 10s
   ```
    - **Image:** Uses the official MariaDB image.
    - **Container Name:** Names the container `my_database`.
    - **Ports:** Maps port 3306 on the host to port 3306 on the container.
    - **Environment Variables:** Sets database credentials and database name.
    - **Volumes:** Uses a named volume `db_data` to persist data.
    - **Healthcheck:** Ensures the database service is healthy before other services depend on it.

2. **Adminer:**
   ```yaml
   adminer:
     image: adminer
     container_name: my_adminer
     ports:
       - "8282:8080"
   ```
    - **Image:** Uses the official Adminer image, a database management tool.
    - **Container Name:** Names the container `my_adminer`.
    - **Ports:** Maps port 8282 on the host to port 8080 on the container.

3. **Backend (smarthome_be):**
   ```yaml
   smarthome_be:
     build:
       context: .
       dockerfile: Dockerfile-be
     container_name: smarthome_be
     environment:
         SPRING_DATASOURCE_URL: jdbc:mariadb://db:3306/smarthomedb
         SPRING_DATASOURCE_USERNAME: user
         SPRING_DATASOURCE_PASSWORD: 123
         SPRING_JPA_HIBERNATE_DDL_AUTO: create
         SPRING_DATASOURCE_DRIVER_CLASS_NAME: org.mariadb.jdbc.Driver
         SPRING_PROFILES_ACTIVE: dev
     ports:
       - "8080:8080"
     depends_on:
         db:
             condition: service_healthy
   ```
    - **Build:** Builds the backend service using the `Dockerfile-be`.
    - **Container Name:** Names the container `smarthome_be`.
    - **Environment Variables:** Configures the Spring Boot application to connect to the database.
    - **Ports:** Maps port 8080 on the host to port 8080 on the container.
    - **Depends On:** Ensures the database service is healthy before starting.

4. **Frontend (smarthome_fe):**
   ```yaml
   smarthome_fe:
     build:
       context: .
       dockerfile: Dockerfile-fe
     container_name: smarthome_fe
     ports:
       - "80:80"
     depends_on:
       - smarthome_be
   ```
    - **Build:** Builds the frontend service using the `Dockerfile-fe`.
    - **Container Name:** Names the container `smarthome_fe`.
    - **Ports:** Maps port 80 on the host to port 80 on the container.
    - **Depends On:** Ensures the backend service is running before starting.

#### Volumes

```yaml
volumes:
  db_data:
```
- **Named Volume:** Defines a named volume `db_data` to persist database data.


### Explanation of nginx.conf

The `nginx.conf` file configures the Nginx server to serve static files for the front-end application and proxy API 
requests to the back-end service. Here is a detailed breakdown of each section:

```nginx
server {
    listen 80;
    server_name localhost;

    location / {
        root /usr/share/nginx/html;
        try_files $uri $uri/ /index.html;
    }

    location /api/{
        proxy_pass http://smarthome_be:8080/smarthome/api/v1/;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_cache_bypass $http_upgrade;

        add_header 'Access-Control-Allow-Methods' 'GET, POST, OPTIONS';
        add_header 'Access-Control-Allow-Headers' 'DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization';
    }
}
```

#### Configuration Breakdown

1. **Server Block:**
   ```nginx
   server {
       listen 80;
       server_name localhost;
   ```
    - **listen 80;**: Configures Nginx to listen on port 80, the default HTTP port.
    - **server_name localhost;**: Sets the server name to `localhost`.

2. **Location Block for Static Files:**
   ```nginx
   location / {
       root /usr/share/nginx/html;
       try_files $uri $uri/ /index.html;
   }
   ```
    - **location / {**: Matches all requests to the root URL.
    - **root /usr/share/nginx/html;**: Sets the root directory for requests to `/usr/share/nginx/html`, where the static files are stored.
    - **try_files $uri $uri/ /index.html;**: Tries to serve the requested file, and if not found, serves `index.html`. This is essential for single-page applications (SPAs) to handle client-side routing.

3. **Location Block for API Requests:**
   ```nginx
   location /api/ {
       proxy_pass http://smarthome_be:8080/smarthome/api/v1/;
       proxy_http_version 1.1;
       proxy_set_header Upgrade $http_upgrade;
       proxy_set_header Connection 'upgrade';
       proxy_set_header Host $host;
       proxy_cache_bypass $http_upgrade;

       add_header 'Access-Control-Allow-Methods' 'GET, POST, OPTIONS';
       add_header 'Access-Control-Allow-Headers' 'DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization';
   }
   ```
    - **location /api/ {**: Matches all requests to `/api/`.
    - **proxy_pass http://smarthome_be:8080/smarthome/api/v1/;**: Forwards requests to the back-end service at `http://smarthome_be:8080/smarthome/api/v1/`.
    - **proxy_http_vers## 2. Step-by-step guideion 1.1;**: Uses HTTP/1.1 for proxying.
    - **proxy_set_header Upgrade $http_upgrade;**: Sets the `Upgrade` header for WebSocket support.
    - **proxy_set_header Connection 'upgrade';**: Sets the `Connection` header for WebSocket support.
    - **proxy_set_header Host $host;**: Sets the `Host` header to the host of the request.
    - **proxy_cache_bypass $http_upgrade;**: Bypasses the cache for WebSocket connections.
    - **add_header 'Access-Control-Allow-Methods' 'GET, POST, OPTIONS';**: Adds CORS headers to allow specific methods.
    - **add_header 'Access-Control-Allow-Headers' 'DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization';**: Adds CORS headers to allow specific headers.

## 3. Setting up the Virtual Server in DEI Private Cloud

1. Login into `https://vs-ctl.dei.isep.ipp.pt`
2. Click `Login - Cloud's Web Interface`
3. Click `Available Virtual Server Templates`
4. Pick the template `Alpine Linux 3.16.3 (fresh install with Docker) - all VNETs`
![img.png](resources/VM_template.png)

5. Initialize the container (press `Start`).
6. Access the container by clicking `Unencrypted local-only access` (You need to be connected to the DEI-ISEP VPN) or `Encrypted global access`.
7. Install necessary packages for deployment:

```bash
apk update
apk add git
apk add docker
apk add docker-compose
```

If you want to use your local machine to deploy the project:
```bash
apk add openssh
```
In your local machine you can run the following command to copy the public key to the virtual server:
```bash
ssh root@<ip_address>
root@10.9.20.229's password: dei.isep (default password)
```


8. Clone the repository:

```bash
git clone https://<token>@<repository_url>
```
9. Navigate to the project directory:

```bash
cd <project_directory>
```

10. Build and run the Docker containers:

```bash
docker-compose up --build
```

11. Access the front-end application in the browser using the IP address of the virtual server. 	http://10.9.20.229/ or http://vs229.dei.isep.ipp.pt/

## 4. Conclusion

In this guide, we've walked through the comprehensive steps necessary to host a system on a Tomcat server, connecting it to a MySQL database container. The process included configuring Docker files for both the back-end and front-end applications, setting up Docker Compose to manage multi-container applications, and customizing Nginx for serving static files and proxying API requests.

Here's a brief summary of what we achieved:

1. **Back-End Configuration**:
- Used a Dockerfile-be to build a Java application with Maven, package it as a WAR file, and deploy it on a Tomcat server.

2. **Front-End Configuration**:
- Used a Dockerfile-fe to build a Node.js application, generate optimized static files, and deploy them on a Nginx server.

3. **Multi-Container Setup**:
- Utilized docker-compose.yml to define services including a MariaDB database, an Adminer database management tool, the back-end application, and the front-end application.
- Configured environment variables for seamless database connectivity and ensured service dependencies were appropriately managed.

4. **Multi-Container Setup**:
- Customized nginx.conf to serve the front-end application and proxy API requests to the back-end service, ensuring efficient routing and client-side routing support for the SPA.

5. **Virtual Server Setup in DEI Private Cloud**:
- Detailed the steps to set up a virtual server using a specific Alpine Linux template, install necessary packages, clone the project repository, and deploy the Docker containers.

By following this guide, you can ensure a reliable and consistent deployment process for your application, 
leveraging the flexibility and scalability offered by Docker and Docker Compose. The resulting system is robust, 
with well-defined service dependencies and efficient routing, providing a solid foundation for further development and 
scaling.

