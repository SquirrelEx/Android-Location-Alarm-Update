# Moondime App demo

A concept App showcasing the intended functionality for Moondime based on a location trigger. You can set a "target" destination in real life and, when you physically go there, a Trigger occurs where a transaction is made and 1 Moondime is minted to your wallet.

You can test this app on your own Android device, [here is an .apk file](https://github.com/SquirrelEx/MoonDime/blob/master/moondime.apk).

## Technologies
* Java 11
* [Android studio](https://developer.android.com/studio)
* [Web3j](https://docs.web3j.io/latest/): Java and Android library for working with Smart Contracts and integrating with Ethereum blockchains

Make sure Android Studio is using Java 11

## How to use the app

The app has a context menu on the left, that allow you to:
- Reset view to current map location
- Configure settings such as the radius for the trigger in meters, or changing the Trigger sound
- Set a location for the trigger. Whenever you visit it, the trigger will occur.
- Save locations as favourites
- Caches the current map and submap tiles offline

### 1. Main Menu
![main menu](https://github.com/SquirrelEx/MoonDime/blob/master/extras/screenshots/mainmenu.png?raw=true)

### 2. Location Search
Allows you to search for a location and set a trigger.

![location search](https://github.com/SquirrelEx/MoonDime/blob/master/extras/screenshots/locationsearch.png?raw=true)

### 3. Trigger activated
When you reach the target location, the trigger is activated. Displaying your current Moondtime token balance and triggering a function to mint 1 more Moondime to your wallet.

![trigger activated](https://github.com/SquirrelEx/MoonDime/blob/master/extras/screenshots/trigger.png?raw=true)

### 4. Android location emulator
Allows you to simulate map movement in Android Studio, useful for testing purposes.

![location emulator](https://github.com/SquirrelEx/MoonDime/blob/master/extras/screenshots/emulator.png?raw=true)

### 5. Etherscan block explorer
Allows you to view the Moondime Token Smart Contract transactions. You can check when a token is minted on the bottom under "Transactions", the method is "Mint".

![etherscan](https://github.com/SquirrelEx/MoonDime/blob/master/extras/screenshots/etherscan.png?raw=true)


The recommended way to test the App is:
1. First set your location using the emulator to a random location of your liking
2. Then set the trigger for one specific location using Step 2 above
3. Then move yourself to that same location using the emulator
4. The trigger should occur and a new token will be minted, verify in etherscan


## Smart contract info
**Network:** Rinkeby

**Contract address:** 0x3dd8404ccfb923b3a65efb5e0475ea853c2db26c

**Deployer address:** 0x5efa76b012dbf3b033f641e24d010a673c824023

**Etherscan block explorer link:** https://rinkeby.etherscan.io/address/0x3dd8404ccfb923b3a65efb5e0475ea853c2db26c

The Moondime Token smart contract is based on [OpenZeppelin's implemention of ERC20](https://docs.openzeppelin.com/contracts/4.x/erc20).

A new function was created to allow minting new tokens, in order to be possible to reward the App users with them. This function is only usable by the owner of the contract, in this case the deployer of the smart contract, implemented through the [Ownable mechanism](https://docs.openzeppelin.com/contracts/2.x/access-control).

The smart contract Solidity code and ABI can be found [in this folder](https://github.com/SquirrelEx/MoonDime/tree/master/moondime%20smart%20contract).


## Credits

Base code was forked from https://github.com/gstavrinos/destination-alarm

### Libraries
* OpenStreetMap - https://www.openstreetmap.org
* OSMDroid - https://github.com/osmdroid/osmdroid
* OSMBonusPack - https://github.com/MKergall/osmbonuspack

### Icons
* wake up by Chinnaking from the Noun Project
* Star by y. onaldi from the Noun Project
* search map by b farias from the Noun Project
* setting by Vectorstall from the Noun Project
* Download Map by Ben Davis from the Noun Project
* Download by Kimmi Studio from the Noun Project
* current location by Balam Palma from the Noun Project
* Bus by Orkhan Mursalov from the Noun Project

