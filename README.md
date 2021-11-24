# okta-springboot-react

The goal of this project is to implement an application where a user can manage (create/read/update/delete) jobs. For it, we will create a backend Restful API called `jobs-api` and a frontend application called `jobs-ui`. Furthermore, we will use [`Okta`](https://www.okta.com/) to secure the complete application.

## Project User Interface Preview

![jobs-portal-preview](documentation/jobs-portal-preview.gif)

## Project diagram

![project-diagram](documentation/project-diagram.png)

## Applications

- ### jobs-api

  [`Spring Boot`](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) Web Java application that exposes a REST API for managing jobs. It has some endpoints that are secured. `jobs-api` uses `Okta` to handle authentication and authorization.
  
  The table below shows the endpoins, each one are secured or not and the authorization role required to access the secured ones.
 
  | Endpoint                | Secured | Role                        |
  | ----------------------- | ------- | --------------------------- |
  | `GET /actuator/*`       |      No |                             |
  | `POST /authenticate`    |      No |                             |
  | `GET /api/jobs/newest`  |      No |                             |
  | `POST /api/jobs`        |     Yes | `JOBS_STAFF`                |
  | `PUT /api/jobs/{id}`    |     Yes | `JOBS_STAFF`                |
  | `DELETE /api/jobs/{id}` |     Yes | `JOBS_STAFF`                |
  | `GET /api/jobs/{id}`    |     Yes | `JOBS_STAFF, JOBS_CUSTOMER` |
  | `PUT /api/jobs/search`  |     Yes | `JOBS_STAFF, JOBS_CUSTOMER` |

- ### jobs-ui

  [`ReactJS`](https://reactjs.org/) frontend application where customers can look for a job and staff members can handle jobs. In order to access it, a person must login. The authentication is handled by `Okta`.

## Prerequisites

- [`Java 11+`](https://www.oracle.com/java/technologies/downloads/#java11)
- [`npm`](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)
- [`Docker`](https://www.docker.com/)
- [`Docker-Compose`](https://docs.docker.com/compose/install/)
- [`Okta` account](https://developer.okta.com/signup/)

## Configuring Okta

### Access Developer Edition Account

- If you do not have a Developer Edition Account, you can create one at https://developer.okta.com/signup/
- If you already have, access https://developer.okta.com/login/

### Access Okta Admin Dashboard

If you are in `Okta Developer Dashboard` home page, click `Admin` button on the top-right

![okta-developer-home](documentation/okta-developer-home.png)

The picture below is how `Okta Admin Dashboard` looks like

![okta-admin-dashboard](documentation/okta-admin-dashboard.png)

### Add Application

- In the `Okta Admin Dashboard` main menu on the left, click `Applications` menu and then `Applications` sub-menu
- In the next page, click `Create App Integration` button
- Select `OIDC - OpenID Connect` as _Sign on method_ and `Single-Page Application` as _Application type_. Click `Next` button
- Enter the following values in the form.
  - General Settings
    - App integration name: `Jobs Portal SPA`
    - Grant type: check `Authorization Code` and `Implicit`
    - Sign-in redirect URIs: `http://localhost:3000/implicit/callback` and `http://localhost:8080/authenticate`
    - Sign-out redirect URIs: `http://localhost:3000`
  - Assignments
    - Controlled access: `Skip group assignment for now`
- Click `Save` button
- On the next screen, it's shown the 2 important values we will need to configure and run the `Jobs Portal SPA`: `Client ID` and `Okta Domain`

### Create groups

- In the `Okta Admin Dashboard` main menu on the left, click `Directory` menu and then `Groups` sub-menu
- Add Staff Group
  - Click `Add Group` button
  - Enter `JOBS_STAFF` for the _Name_ and `Jobs Staff Group` for the _Group Description_
  - Click `Add Group` button
- Add Customer Group
  - Click `Add Group` button
  - Enter `JOBS_CUSTOMER` for the _Name_ and `Jobs Customer Group` for the _Group Description_
  - Click `Add Group` button

### Add people

- In the `Okta Admin Dashboard` main menu on the left, click `Directory` menu and then `People` sub-menu
- Click `Add person` button
- Enter the following information for the Staff person
  - First name: `Mario`
  - Last name: `Bros`
  - Username: `mario.bros@jobs.com`
  - Primary email: `mario.bros@jobs.com`
  - Groups: `JOBS_STAFF` (the group will popup; select it to add it)
  - Password: `Set by admin`
  - Set a strong password in the text-field that will appear
  - `Uncheck` the check-box that says _"User must change password on first login"_
  - Click `Save and Add Another` button
- Enter the following information for the Customer person
  - First name: `Luigi`
  - Last name: `Bros`
  - Username: `luigi.bros@jobs.com`
  - Primary email: `luigi.bros@jobs.com`
  - Groups: `JOBS_CUSTOMER` (the group will popup; select it to add it)
  - Password: `Set by admin`
  - Set a strong password in the text-field that will appear
  - Leave `Uncheck` the check-box that says _"User must change password on first login"_
  - Click `Save` button

### Assign Groups to Application

- In the `Okta Admin Dashboard` main menu on the left, click `Directory` menu and then `Groups` sub-menu
- Select `JOBS_STAFF`
- Click `Manage Apps` button
- Click the `Assign` button related to `Jobs Portal SPA` and then click `Done`
- In the `Okta Admin Dashboard` main menu on the left, click `Groups` sub-menu again
- Select `JOBS_CUSTOMER`
- Click `Manage Apps` button
- Click the `Assign` button related to `Jobs Portal SPA` and then click `Done`

### Add Claim

- In the `Okta Admin Dashboard` main menu on the left, click `Security` menu and then `API` sub-menu
- In `Authorization Servers` tab, select the `default`
- In `Claims` tab, click `Add Claim`
- Enter the following information for the claim
  - Name: `groups`
  - Include in token type:
    - Select `Access Token`
    - Keep `Always` in the right field
  - Value type: `Groups`
  - Filter:
    - Select `Matches regrex`
    - Set `.*` in the right field
  - Include in: `Any scope`
  - Click `Create` button

## Start Environment

- Open a terminal and inside `okta-springboot-react` root folder run
  ```
  docker-compose up -d
  ```

- Wait for `elasticsearch` Docker container to be up and running. To check it, run
  ```
  docker-compose ps
  ```

## Running applications

- **jobs-api**

  - In a terminal, navigate to `okta-springboot-react/jobs-api` folder

  - Export the following environment variables. Those values were obtained while (adding Application)[#add-application]
    ```
    export OKTA_CLIENT_ID=...
    export OKTA_DOMAIN=...
    ```

  - Run the [`Maven`](https://maven.apache.org/) command below to start `jobs-api`
    ```
    ./mvnw clean spring-boot:run
    ```

- **jobs-ui**

  - Open a new terminal and navigate to `okta-springboot-react/jobs-ui` folder

  - Create a file called `.env.local` with the following content. Those values were obtained while (adding Application)[#add-application]
    ```
    REACT_APP_OKTA_CLIENT_ID=<OKTA_CLIENT_ID>
    REACT_APP_OKTA_ORG_URL=https://<OKTA_DOMAIN>
    ```

  - If you are running `jobs-ui` for the first time, execute the [`npm`](https://www.npmjs.com/) command below
    ```
    npm install
    ```

  - To start `jobs-api` run
    ```
    npm start
    ```
    It will open `job-ui` in a browser automatically.

## Applications URLs

| Application | URL                                   |
| ----------- | ------------------------------------- |
| jobs-api    | http://localhost:8080/swagger-ui.html |
| jobs-ui     | http://localhost:3000                 |

## Using jobs-ui

- Open a browser and access http://localhost:3000

- Click `Login` in the navigation bar

- The Okta login page will appear. Enter the username & password of the person added at the step [`Configuring Okta > Add people`](#add-people) and click `Sign In`.

- Done!

> **Note:** If you are using the person `luigi.bros@jobs.com`, you will not be able to create/update/delete a job because it doesn't have the required role for it.

## Getting Access Token

In order to use just the `jobs-api` endpoints, you must have an access token. Below are the steps to get it.

- In a terminal, create the following environment variables. Those values were obtained while (adding Application)[#add-application]
  ```
  OKTA_CLIENT_ID=...
  OKTA_DOMAIN=...
  ```

- Get Okta Access Token Url
  ```
  export OKTA_ACCESS_TOKEN_URL="https://${OKTA_DOMAIN}/oauth2/default/v1/authorize?\
  client_id=${OKTA_CLIENT_ID}\
  &redirect_uri=http://localhost:8080/authenticate\
  &scope=openid\
  &response_type=token\
  &response_mode=form_post\
  &state=state\
  &nonce=6jtp65rt9jf"

  echo $OKTA_ACCESS_TOKEN_URL
  ```

- Copy the Okta Access Token Url from the previous step and past it in a browser

- The Okta login page will appear. Enter the username & password of the person added at the step [`Configuring Okta > Add people`](#add-people) and click `Sign In` button

- It will redirect to `authenticate` endpoint of `jobs-api` and the `Access token` will be displayed.

## Calling jobs-api endpoints using curl

- **`GET api/jobs/newest`**

  The `api/jobs/newest` endpoint is public, so we can access it without any problem.
  ```
  curl -i http://localhost:8080/api/jobs/newest?number=2
  ```
  It should return
  ```
  HTTP/1.1 200
  [{"id":"uuulE2sBTYouQKNL1uoV", ...},{"id":"u-ulE2sBTYouQKNL1-qb", ...}]
  ```

- **`GET api/jobs` without Access Token**

  Try to get the list of jobs without informing the access token.
  ```
  curl -i http://localhost:8080/api/jobs
  ```
  It should return
  ```
  HTTP/1.1 401
  ```

- **`GET api/jobs` with Access Token**

  First, get the access token as explained in [`Getting Access Token`](#getting-access-token) section. Then, create an environment variable for the access token.
  ```
  ACCESS_TOKEN=...
  ```
  
  Call the get the list of jobs informing the access token
  ```
  curl -i http://localhost:8080/api/jobs -H "Authorization: Bearer $ACCESS_TOKEN"
  ```
  Response
  ```
  HTTP/1.1 200
  {"content":[{"id":"uISqEWsBpDcNLtN2kZv3","title":"Expert Java Developer - Cloud","company":"Microsoft","logoUrl"...}
  ```

  > **Note:** If you are using the person `luigi.bros@jobs.com`, you will not be able to create/update/delete a job because it doesn't have the required role for it.

## Using jobs-api with Swagger

- First, get the access token as explained in [`Getting Access Token`](#getting-access-token) section.

- Open `jobs-api` Swagger website http://localhost:8080/swagger-ui.html

- Click `Authorize` button. Paste the access token in the `Value` field. Then, click `Authorize` and `Close` to finalize.

- Done! You can now access the sensitive endpoints.

> **Note:** If you are using the person `luigi.bros@jobs.com`, you will not be able to create/update/delete a job because it doesn't have the required role for it.

## Shutdown

- Go to the terminals where `jobs-api` and `jobs-ui` are running and press `Ctrl+C`

- To stop and remove containers, network and volumes, run in a terminal the following command inside `okta-springboot-react` root folder
  ```
  docker-compose down -v
  ```

## Cleanup

### Okta Configuration

#### Delete Person

- In the `Okta Admin Dashboard` main menu on the left, click `Directory` menu and then `People` sub-menu
- Click `Mario Bros` in the People list
- In `Mario Bros` profile, click `More Actions` multi-button and then `Deactivate`
- Confirm deactivation by clicking `Deactivate` button
- Still in `Mario Bros` profile, click `Delete` button
- Confirm deletion by clicking `Delete` button
- Click `Luigi Bros` in the People list
- In `Luigi Bros` profile, click `More Actions` multi-button and then `Deactivate`
- Confirm deactivation by clicking `Deactivate` button
- Still in `Luigi Bros` profile, click `Delete` button
- Confirm deletion by clicking `Delete` button

#### Delete Groups

- In the `Okta Admin Dashboard` main menu on the left, click `Directory` menu and then `Groups` sub-menu
- Select `JOBS_CUSTOMER`
- In `JOBS_CUSTOMER` profile, click `Delete Group` button
- Confirm deletion by clicking `Delete Group` button
- Select `JOBS_STAFF`
- In `JOBS_STAFF` profile, click `Delete Group` button
- Confirm deletion by clicking `Delete Group` button

#### Delete Application

- In the `Okta Admin Dashboard` main menu on the left, click `Applications` menu and then `Applications` sub-menu
- In Application list whose status is `ACTIVE`, click `Jobs Portal SPA`'s `gear` icon and then click `Deactivate`
- Confirm deactivation by clicking `Deactivate Application` button
- In Application list whose status is `INACTIVE`, click `Jobs Portal SPA`'s `gear` icon and then click `Delete`
- Confirm deletion by clicking `Delete Application` button

#### Delete Clain

- In the `Okta Admin Dashboard` main menu on the left, click `Security` menu and then `API` sub-menu
- In `Authorization Servers` tab, select the `default`
- In `Claims` tab, click the `x` icon related to the `groups` claim created
- Confirm deletion by clicking `OK` button

## How to upgrade jobs-ui dependencies to latest version

- In a terminal, make sure you are in `okta-springboot-react/jobs-ui` folder

- Run the following commands
  ```
  npm i -g npm-check-updates
  ncu -u
  npm install
  ```

## References

- https://www.npmjs.com/package/@okta/okta-react
- https://developer.okta.com/code/react/okta_react_sign-in_widget/
- https://developer.okta.com/blog/2019/03/06/simple-user-authentication-in-react
- https://dzone.com/articles/23-useful-elasticsearch-example-queries
