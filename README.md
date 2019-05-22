# `springboot-react-okta`

The goal of this project is to implement an application where an user can manage (create/read/update/delete) jobs. For it, we will create two microservices: a backend Restful API called `jobs-api` and a frontend user interface called `jobs-ui`. Furthermore, we will use OKTA to secure the application.

# Microservices

## jobs-api

Spring-boot Web Java application that exposes a REST API for managing or retrieving jobs. Its endpoints are secured. `jobs-api` uses OKTA to handle the authentication and authorization.

## jobs-ui

React UI application that users will use to manage jobs easily. In order to access it, you must login. The authentication is handled by OKTA.

# Configuring OKTA

First of all, you must to create a free account at https://developer.okta.com/signup/. Once you it, log in and let's start the configuration.

## Add an OpenID Connect Client in Okta

- In Okta Developer Dashboard, click on `Applications` then on `Add Application` button.

- Select `Single-Page App` and click on the `Next` button.

- Enter the following values in the form

| Setting | Value |
| ------- | ----- |
| App Name | My Jobs |
| Base URIs | http://localhost:3000 |
| Login redirect URIs | http://localhost:3000/implicit/callback |
| Grant Types Allowed | Implicit |

- After the application is created, there are some values that you will need in the overal project configuration.

| Setting | Where to Find |
| ------- | ------------- |
| Client ID | In the applications list, or on the `General` tab of a specific application. |
| Org URL | On the home screen of the developer dashboard, in the upper right. |

## Enabling groups

- In Okta Developer Dashboard, hover the mouse on `API` then click on `Authorization Servers` menu.

- An authorization server table will appear. Edit `default` authorization server by clicking on the pen-symbol button.

- Go to `Scopes` tab and click on `Add Scope`.

- Add the scope with name `groups`, simple description `Okta internal groups`, select `Include in public metadata option` and, finally, click on `Create`.

- Go to `Claims` tab.

- Let's add the first claim. So, click on `Add Claim` button and add the settings displayed on the table below. After setting them, click on `Save` button.

| Setting | Value |   |
| ------- | ----- | - |
| Name | groups | |
| Include in token type | Access Token | |
| Value type | Groups | |
| Filter | Matches regex | .*|
| Include in | Any scope | |

- Now, let's add the second and last claim. For it, click again on `Add Claim` button and add the settings displayed on the table below. After setting them, click on `Save` button.

| Setting | Value |   |
| ------- | ----- | - |
| Name | groups | |
| Include in token type | ID Token | |
| Value type | Groups | |
| Filter | Matches regex | .*|
| Include in | Any scope | |

> Note. The only difference from the first claim is in the "Include in token type". The second has `ID Token` and the former `Access Token`.

## Creating groups

- In Okta Developer Dashboard, hover the mouse on `Users` then click on `Groups` menu.

- Let's add the first group. So, click on `Add Group` button, enter name `JOBS_STAFF` and for group description set `Jobs Staff Group`. Then, click on `Add Group` button.

- Let's add the second group. For it, click again on `Add Group` button, enter name `JOBS_CUSTOMER` and for group description type `Jobs Customer Group`. Then, click on `Add Group` button.

## Adding people

- In Okta Developer Dashboard, hover the mouse on `Users` then click on `People` menu.

- Let's add the first person. He is from jobs staff. So, click on `Add Person` button and enter the following values in the form

| Setting | Value |
| ------- | ----- |
| First name | Ivan |
| Last name | Staff |
| Username | ivan.staff@jobs.com |
| Groups | JOBS_STAFF |
| Password | Set by admin |

Enter a password and do not select the checkbox `User must change password on first login`

- Let's add the second person. She is a jobs customer. For it, click on `Add Person` button and enter the following values in the form

| Setting | Value |
| ------- | ----- |
| First name | Ivan |
| Last name | Customer |
| Username | ivan.customer@jobs.com |
| Groups | JOBS_CUSTOMER |
| Password | Set by admin |

Enter a password and do not select the checkbox `User must change password on first login`

# Running microservices with Maven & Npm

## jobs-api
```
cd jobs-api
./mvnw spring-boot:run
```

## jobs-ui
```
cd jobs-ui
npm start
```

# Useful links

### mongo-express

Web-based MongoDB admin interface can be accessed at http://localhost:8081

# Referances

- https://www.npmjs.com/package/@okta/okta-react
- https://developer.okta.com/code/react/okta_react_sign-in_widget/
- https://developer.okta.com/blog/2019/03/06/simple-user-authentication-in-react

--------------------------------

## OpenId Connect Debugger
```
Authorize URI (required)
https://${OKTA_DOMAIN}/oauth2/default/v1/authorize

Redirect URI (required)
https://oidcdebugger.com/debug

Client ID (required)
0oah...

Scope (required)
openid
 
State
state
 
Nonce 
wbr...

Response type (required)
 code
 token X
 id_token

Response mode (required)
 query
 form_post X
 fragment
```

```
curl -i http://localhost:8080/actuator/health
curl -i http://localhost:8080/api/jobs

curl -i -X POST http://localhost:8080/api/jobs \
  -H "Content-Type: application/json" \
  -d '{"title": "Software Developer", "description": "We are looking for a Software Developer"}'

export TOKEN=...

curl -i -X POST http://localhost:8080/api/jobs \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"title": "Software Developer", "description": "We are looking for a Software Developer"}'
```

## Using client credentials

https://developer.okta.com/blog/2018/10/16/token-auth-for-java

```
export CLIENT_ID=...
export CLIENT_SECRET=...

TOKEN=$(echo -n $CLIENT_ID:$CLIENT_SECRET | base64)

echo -n $TOKEN | base64 -D

curl -i -X POST https://dev-279724.okta.com/oauth2/default/v1/token \
  -H 'Authorization: Basic $TOKEN' \
  -d 'grant_type=client_credentials' \
  -d 'scope=custom_scope'
```
