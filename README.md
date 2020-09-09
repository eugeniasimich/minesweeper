Minesweeper 
=================

The classical game of minesweeper implemented in Scala using Play for a the REST API server and React.js for the UI 

## Running it locally

### Prerequisites

* [Node.js](https://nodejs.org/)
* [scala](https://www.scala-lang.org/download/)
* [PostgreSQL](https://www.postgresql.org/)

### Then

* Fork or clone this repository.

* Set the environment variable `JDBC_DATABASE_URL` to match the path to a database that you have in postgres, for example_ 
```
export JDBC_DATABASE_URL="_jdbc:postgresql://localhost/buscamines_"
```

* Use any of the following [SBT](http://www.scala-sbt.org/) commands which will intern trigger frontend associated npm scripts.

```
    sbt stage       # Build your application from your project’s source directory
    sbt run         # Run both backend and frontend builds in watch mode
    sbt clean       # Clean existing build artifacts
    sbt test        # To run both front and back ens tests
```
If you go with `sbt stage && sbt run` you should be able to access the app at `http://localhost:9000` 

## Directory Layout

```
├── /app/                                 # The backend (scala play) sources (controllers, models)
│     ├── /controllers/                   # Backend controllers
│     |     ├── AuthenticatedAction       # Helper trait to handle requests that need login seesion
│     |     ├── FrontendController        # Asset controller wrapper serving frontend assets and artifacts
|     |     ├── CSRFController            # Serves SCRF token
|     |     ├── GameController            # Game API itself
|     |     ├── GameManager               # Business Logic
|     |     ├── LoginController           # Log in, sign up API
|     |     └── PersistenceController     # Serves the abiliti to save and restore games, it is securitized
|     ├── /models/                        # DB DAOs and Busic Logic models 
│     |     ├── DatabaseConfig            # Centralize point for db config and transactors
│     |     ├── GameDAO                   # Game storage
│     |     ├── GameModel                 # Problem model
│     |     ├── SessionDAO                # Login sessions storage
│     |     ├── UserDAO                   # User storage
│     └── /utils/                         # Game storage
│          └── RandomUtil                 # Randomness helper  
├── /conf/                                # Configurations files and other non-compiled resources (on classpath)
│     ├── application.conf                # Play application configuratiion file.
│     ├── logback.xml                     # Logging configuration
│     └── routes                          # Routes definition file
├── /logs/                                # Log directory
│     └── application.log                 # Application log file
├── /project/                             # Contains project build configuration and plugins
│     ├── FrontendCommands.scala          # Frontend build command mapping configuration
│     ├── FrontendRunHook.scala           # Forntend build PlayRunHook (trigger frontend serve on sbt run)
│     ├── build.properties                # Marker for sbt project
│     └── plugins.sbt                     # SBT plugins declaration
├── /public/                              # Frontend build artifacts will be copied to this directory
├── /target/                              # Play project build artifact directory
├── /test/                                # Contains unit tests of backend sources
├── /ui/                                  # React frontend source (based on Create React App)
│     ├── /public/                        # Contains the index.html file
│     ├── /node_modules/                  # 3rd-party frontend libraries and utilities
│     ├── /src/                           # The frontend source codebase of the application
│     |     ├── App.css                   # Common styles
│     |     ├── App.js                    # Main component
│     |     ├── Auth.js                   # Authentication client
│     |     ├── Cell.js                   # Component for a single cell of the minesweeper grid
│     |     ├── Client.js                 # Communication with server
│     |     ├── flagUtils.js              # Flagging cell utilities
│     |     ├── Grid.js                   # Component for the entire minesweeper grid
│     |     ├── index.js                  # React render
│     |     ├── Login.js                  # Login form component 
│     |     ├── Menu.js                   # Menu component, it holds New game, save game, restore game, sign out menu functionallity
│     |     ├── PrivateRoute.js           # Route wrapper to handle login  
│     |     ├── ResumeGameDialog.js       # Resume game pop up
│     |     ├── SaveGameDialog.js         # Save game pop up
│     |     ├── SessionCookie.js          # Login cookie manager
│     |     ├── shapes.js                 # React "data types"
│     |     └── TimeTracker.js            # Time tracker component
│     ├── package.json                    # NPM configuration of frontend source
│     ├── README.md                       # Contains all user guide details for the ui
│     └── yarn.lock                       # Yarn lock file
├── build.sbt                             # Play application SBT configuration
├── README.md                             # This file
└── ui-build.sbt                          # SBT command hooks associated with frontend npm scripts 
```

## Decisions taken
Use Play framework to develop the server, to use an established way of doing so. An interesting alternative would have been using http4s with cats effects or zio, providing a pure functional backend server.  

This project was intended to provide a REST API server for minesweeper game, therefore the client just renders and holds the minimum business logic.

Flags are persisted in the frontend, as they do not hold more semantics than preserving the user of clicking in a probably infected cell.

Game model is redundant for the sake of simplicity, at the expense of memory complexity.

Sessions are stored in memory also for simplicity, even though the proper way would have been having a database table with an in memory cache.

Use doobie for DB access, to make use of its a pure functional JDBC layer for Scala and Cats. It would have been even better to have it coupled with cats effect or zio, so there's no need to call unsafeRun to get out of the monad, from the description to effects.