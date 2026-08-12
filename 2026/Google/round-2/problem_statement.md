**Design HTTP router**

You are tasked to implement the core HTTP routing logic of a new web framework (similar to Spring Boot, Express, Django, Ktor).

There should be two functions `registerHandler` and `getHandler`.

- `registerHandler(pattern: String)`: Called when a developer adds a handler. Add a new URL pattern that can contain {parameters}.
- `getHandler(path: String)`: Called when a real URL is requested from the service. Return the correct pattern string or null when none is matching.

Example:

registerHandler("/home")
registerHandler("/users/new")
registerHandler("/users/{id}")
registerHandler("/users/{id}/pictures/{pictureId}")

getHandler("/home")                    // returns "/home"
getHandler("/users/101")               // returns "/users/{id}"
getHandler("/users/101/pictures/1")    // returns "/users/{id}/pictures/{pictureId}"
getHandler("/users/new")               // returns "/users/new"
getHandler("/users/")                  // returns null


getHandler(/users/old/pictures/101)    // returns "/users/{id}/pictures/{pictureId}"