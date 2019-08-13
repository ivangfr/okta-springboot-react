# `springboot-react-okta`

The goal of this project is to implement an application where an user can manage (create/read/update/delete) jobs. For it, we will create two microservices: a backend Restful API called `jobs-api` and a frontend user interface called `jobs-ui`. Furthermore, we will use Okta to secure the application.

## Project User Interface Preview

![jobs-portal-preview](images/jobs-portal-preview.gif)

## Project diagram

![project-diagram](images/project-diagram.png)

## Microservices

### jobs-api

Spring-boot Web Java application that exposes a REST API for managing jobs. It has some endpoints that are secured. `jobs-api` uses Okta to handle the authentication and authorization. The table below shows the authorization role required to access the sensitive endpoints.

| Endpoint                | Role                        |
| ----------------------- | --------------------------- |
| `GET /actuator/*`       |                             |
| `GET /api/jobs/newest`  |                             |
| `POST /api/jobs`        | `JOBS_STAFF`                |
| `PUT /api/jobs/{id}`    | `JOBS_STAFF`                |
| `DELETE /api/jobs/{id}` | `JOBS_STAFF`                |
| `GET /api/jobs/{id}`    | `JOBS_STAFF, JOBS_CUSTOMER` |
| `PUT /api/jobs/search`  | `JOBS_STAFF, JOBS_CUSTOMER` |

### jobs-ui

ReactJS application where customers can look for a job and staff members can handle jobs. In order to access it, the person must login. The authentication is handled by Okta.

## Configuring Okta

First of all, you must create a free account at https://developer.okta.com/signup/. Once you have it, log in and let's start the configuration.

### Add an OpenID Connect Client

- In Okta Developer Dashboard, click on `Applications` and then on `Add Application` button.

- Select `Single-Page App` and click on `Next` button.

- Enter the following values in the form

| Setting             | Value                                   |
| ------------------- | --------------------------------------- |
| App Name            | Jobs Portal SPA                         |
| Base URIs           | http://localhost:3000                   |
| Login redirect URIs | http://localhost:3000/implicit/callback |
|                     | http://localhost:8080/authenticate      |
| Grant Types Allowed | Implicit                                |

- After the application is created, there are some values that you will need during all project configuration and execution.

| Setting | Where to Find | Example (fake) |
| ------- | ------------- | -------------- |
| Org URL | On the home screen of the developer dashboard, in the upper right. | https://dev-123456.okta.com |
| Okta Domain| It is the Org URL without `https://` | dev-123456.okta.com |
| Client ID | In the applications list, or on the `General` tab of a specific application. | 0bcky2d71eXtSsscC123 |

### Enabling groups

- In Okta Developer Dashboard, hover the mouse on `API` then click on `Authorization Servers` menu.

- An authorization server table will appear. Edit `default` authorization server by clicking on the pen-symbol button.

- Go to `Scopes` tab and click on `Add Scope`.

- Add the scope with name `groups`, simple description `Okta internal groups`, select `Include in public metadata option` and, finally, click on `Create`.

- Go to `Claims` tab.

- Let's add the first claim. So, click on `Add Claim` button and add the settings displayed on the table below. After that, click on `Save` button.

| Setting               | Value         | Extra info |
| --------------------- | ------------- | ---------- |
| Name                  | groups        |            |
| Include in token type | Access Token  |            |
| Value type            | Groups        |            |
| Filter                | Matches regex | .*         |
| Include in            | Any scope     |            |

- Now, let's add the second and last claim. For it, click again on `Add Claim` button and add the settings displayed on the table below. After that, click on `Save` button.

| Setting               | Value         | Extra info |
| --------------------- | ------------- | ---------- |
| Name                  | groups        |            |
| Include in token type | ID Token      |            |
| Value type            | Groups        |            |
| Filter                | Matches regex | .*         |
| Include in            | Any scope     |            |

> **Note:** The only difference from the first claim is in the "Include in token type". The second has `ID Token` and the former `Access Token`.

### Creating groups

- In Okta Developer Dashboard, hover the mouse on `Users` then click on `Groups` menu.

- Let's add the first group. So, click on `Add Group` button, enter name `JOBS_STAFF` and for group description set `Jobs Staff Group`. Then, click on `Add Group` button.

- Let's add the second group. For it, click again on `Add Group` button, enter name `JOBS_CUSTOMER` and for group description type `Jobs Customer Group`. Then, click on `Add Group` button.

### Adding people

- In Okta Developer Dashboard, hover the mouse on `Users` then click on `People` menu.

- Let's add the first person. He is a Jobs Portal staff member. So, click on `Add Person` button and enter the following values in the form

| Setting | Value |
| ------- | ----- |
| First name | Ivan |
| Last name | Staff |
| Username | ivan.staff@jobs.com |
| Groups | JOBS_STAFF |
| Password | Set by admin |

Enter a password and DO NOT select the checkbox `User must change password on first login`

- Let's add the second person. He is a Jobs Portal customer. For it, click on `Add Person` button and enter the following values in the form

| Setting    | Value                  |
| ---------- | ---------------------- |
| First name | Ivan                   |
| Last name  | Customer               |
| Username   | ivan.customer@jobs.com |
| Groups     | JOBS_CUSTOMER          |
| Password   | Set by admin           |

Enter a password and DO NOT select the checkbox `User must change password on first login`

## Start environment

- In a terminal and inside `springboot-react-okta` root folder run
```
docker-compose up -d
```

## Running microservices

### jobs-api

- In a terminal, go to `springboot-react-okta/jobs-api` folder

- Export the following environment variables. They were obtained while configuring Okta. See **Configuring Okta > Add an OpenID Connect Client** section.
```
export OKTA_DOMAIN=...
export OKTA_CLIENT_ID=...
```

- Start `jobs-api` using [`Maven`](https://maven.apache.org/)
```
./mvnw clean spring-boot:run
```

- `jobs-api` has a Swagger website: http://localhost:8080/swagger-ui.html

### jobs-ui

- Open a new terminal, go to `springboot-react-okta/jobs-ui` folder

- Start `jobs-api` using [`npm`](https://www.npmjs.com/)
```
npm start
```

## Using jobs-ui

- Open a browser and access the url: http://localhost:3000

- Clink on `Login` in the navigation bar

- The Okta login page will appear. Enter the username & password of the person added at the step **Configuring Okta > Adding people** and click on `Sign In`.

- Done!

> **Note:** If you are using the person `ivan.customer@jobs.com`, you will not be able to create/update/delete a job because it doesn't have the required role for it.

## Getting Access Token

In order to use just the `jobs-api` endpoints, you must have an access token. Below are the steps to get it:

- In a terminal, export the following environment variables. They were obtained while configuring Okta. See **Configuring Okta > Add an OpenID Connect Client** section.
```
export OKTA_DOMAIN=...
export OKTA_CLIENT_ID=...
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

- The Okta login page will appear. Enter the username & password of the person added at the step **Configuring Okta > Adding people** and click on `Sign In`

- It will redirect to `OpenId Connect Debugger` and the `Access token` will be displayed.

## Calling jobs-api endpoints using curl

### GET api/jobs/newest

The `api/jobs/newest` endpoint is public, so we can access it without any problem.
```
curl -i http://localhost:8080/api/jobs/newest?number=2
```
Response
```
HTTP/1.1 200
[{"id":"uuulE2sBTYouQKNL1uoV", ...},{"id":"u-ulE2sBTYouQKNL1-qb", ...}]
```

### GET api/jobs without Access Token

Try to get the list of jobs without informing the access token.
```
curl -i http://localhost:8080/api/jobs
```
Response
```
HTTP/1.1 401
```

### GET api/jobs with Access Token

First, get the access token as explained in **Getting Access Token** section. Then, export the access token to an environment variable.
```
export ACCESS_TOKEN=...
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

> **Note:** If you are using the person `ivan.customer@jobs.com`, you will not be able to create/update/delete a job because it doesn't have the required role for it.

## Using jobs-api with Swagger

- First, get the access token as explained in **Getting Access Token** section.

- Open `jobs-api` Swagger website, http://localhost:8080/swagger-ui.html

- Click on `Authorize` button. Paste the access token in the `Value` field prefixed by `Bearer`, like `Bearer <access-token>`. Then, click on `Authorize` and on `Close` to finalize.

- Done! You can now access the sensitive endpoints.

> **Note:** If you are using the person `ivan.customer@jobs.com`, you will not be able to create/update/delete a job because it doesn't have the required role for it.

## Shutdown

Run the command below to stop and remove containers, networks and volumes
```
docker-compose down -v
```

## References

- https://www.npmjs.com/package/@okta/okta-react
- https://developer.okta.com/code/react/okta_react_sign-in_widget/
- https://developer.okta.com/blog/2019/03/06/simple-user-authentication-in-react
- https://dzone.com/articles/23-useful-elasticsearch-example-queries
