# Features
## User Authentication
- Firebase Authentication for secure login using email and password.
## Account Management
- Displayed a list of user accounts with balances and account details using RecyclerView.
## Transfer Interface
- Allows users to transfer funds between accounts.
- Validates transfer amount against the source account balance.
- Provides a summary of transfer details before confirmation.
## Transaction History
- Stores transaction history locally using Room database.
- Displays past transactions in a RecyclerView.

# Libraries Used
- Firebase Authentication: used for user login and authentication.
- RecyclerView: displaying lists of accounts and transaction history.
- Room & SharedPrefrences: local database management for storing accounts and transactions.
- LiveData: data observation for updating UI components based on database changes.
- ViewModel: managing UI-related data in a lifecycle-conscious way.
- Kotlin Coroutines: For managing asynchronous tasks such as database operations and network requests in a structured and sequential manner.

<table> 
<tr> 
<th> <img src= "https://github.com/Chinazablossom/VPD/blob/main/login.png?raw=true" width="200" ></th>
<th> <img src= "https://github.com/Chinazablossom/VPD/blob/main/home_screen.png?raw=true" width="200" ></th> 
<th> <img src= "https://github.com/Chinazablossom/VPD/blob/main/th_1.png?raw=true" width="200" ></th>
<th> <img src= "https://github.com/Chinazablossom/VPD/blob/main/trans1.png?raw=true" width="200" ></th>
</tr>

<tr> 
<th>Login Screen</th> 
<th>Home Screen</th> 
<th>transaction history Screen</th> 
<th>transfer screen</th> 
</tr>

<tr> 
<th><img src= "https://github.com/Chinazablossom/VPD/blob/main/trans2.png?raw=true" width="200" ></th>
<th><img src= "https://github.com/Chinazablossom/VPD/blob/main/updated_home.png?raw=true" width="200" ></th>
<th><img src= "https://github.com/Chinazablossom/VPD/blob/main/th_2.png?raw=true" width="200" ></th> 
</tr>  

<tr> 
<th>transaction details Screen</th> 
<th>updated home Screen</th> 
<th>updated transaction history Screen</th> 
</tr>
</table>

## App Demo
[App Demo](https://github.com/Chinazablossom/VPD/assets/107410128/99c39abc-7b20-4f74-b855-1c7256133cc7)




## SDK Requirements
- Minimum SDK Requirement --- API 24 Android 7.0 (Nougat)
- Target SDK Requirement –-- API 34 Android API 34

## Clone the repo
- [Here](https://github.com/Chinazablossom/VPD.git)

## Build and Run
- Open the project in Android Studio.
- Sync Gradle files and build the project.
- Connect a physical device or use an emulator with Android 5.0 (API level 21) or higher.
- Run the app on your device/emulator.
  
## Notes
- Ensure you have an active internet connection when running the app for Firebase Authentication to work properly.
- The initial setup includes mock data for three different currency accounts (NGN, USD, GBP). These are added on first app launch .
- Transaction history is stored locally using Room database. If running on a physical device, you can inspect the database using the Device File Explorer in Android Studio.

