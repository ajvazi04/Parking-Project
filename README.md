# Parking Project

A Java 17+ console app for managing a small parking lot. It demonstrates object-oriented design with vehicle subclasses, a parking-lot model, immutable parking tickets, and a separate console interface. All data is kept in memory and resets when the app exits.

## Features

- Park and remove cars, motorcycles, and trucks
- Search by license plate; duplicate active plates are rejected
- Add or remove parking spaces and view current availability
- Keep a session history of completed parking tickets
- Validate user input and handle full lots and unavailable spaces

## Run (Windows PowerShell)

From the project root, compile and start the app:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
$sources = Get-ChildItem src\*.java | ForEach-Object { $_.FullName }
javac -d out $sources
java -cp out Main
```

## Run the tests

The project has a dependency-free test program covering parking, search, history, validation, and space management:

```powershell
$sources = Get-ChildItem src\*.java, tests\*.java | ForEach-Object { $_.FullName }
javac -d out $sources
java -ea -cp out ParkingLotTest
```

## How the code is organized

- `Vehicle`, `Car`, `Motorcycle`, and `Truck` model the supported vehicle types.
- `ParkingLot`, `ParkingSlot`, and `Ticket` contain the parking rules and state.
- `ParkingApplication` handles menus and user input; `Main` starts the app.
- `tests\ParkingLotTest.java` checks the core behavior without extra libraries.
