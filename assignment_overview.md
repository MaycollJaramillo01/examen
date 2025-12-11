# Room Booking App – Assignment Overview

This repository captures the core requirements for the mobile application assessment described in the prompt. Use it as a quick reference while implementing the project.

## Objectives
- Design and build a mobile app that consumes the provided REST API.
- Demonstrate authentication, room creation, room listing, booking, and unbooking flows.
- Apply good programming practices (OOP, avoidance of hardcoded values, design patterns, English naming).

## Required Screens
1. **Login screen**
   - Authenticate via `POST /users/auth` with the supplied JSON payload.
   - Users can be listed with `GET /users/`.
2. **Room creation screen**
   - Allow creating rooms (creation only).
3. **Room listing screen**
   - Display all rooms, clearly differentiating reserved vs. available.
   - Actions:
     - Select an available room to reserve it using `PUT /rooms/booking`.
     - Select an occupied room to release it using `PUT /rooms/unbooking`.
     - Refresh button to reload data from `GET /rooms`.

## API Behavior Notes
- Inspect `responseCode` for both successful and error responses.
- Surface `message` from the API responses to the user (e.g., show the error if credentials are invalid or the room is already reserved).

Example success response:
```json
{"data":{"user":"estudiante","name":"Estudiante","lastname":"Representante Estudiantil","emailname":"estudiante@est.utn.ac.cr"},"responseCode":"INFO_FOUND","message":"Action executed sucessfully."}
```

Example error response:
```json
{"data":null,"responseCode":"INFO_NOT_FOUND","message":"The user/password does not match with the right credentials or username is inactive."}
```

For full endpoint details, see the API README at <https://github.com/utn-app-movil/rooms-api>.

## Naming and Organization Guidelines
- Prefix objects with their type/name as specified (e.g., activities with `main`, `authentication`, `room`, `roomList`).
- Use English names for variables and objects.
- Provide your own `strings.xml` using the `yen_` prefix for string keys (per the Android Studio guidance).

## Repository and Branching
- Work from your own branch and open a pull request targeting the `development` branch of `https://github.com/utn-app-movil/examenII-g2` after completing the implementation.

## Grading Rubric Highlights
1. Runs and installs on device/emulator (5 pts).
2. Functional coverage (35 pts): authentication, data validation/integration, room creation/listing, data consistency, booking/unbooking.
3. Good practices (10 pts): OOP (including inheritance), design patterns, UX considerations, no hardcoding, English naming.
