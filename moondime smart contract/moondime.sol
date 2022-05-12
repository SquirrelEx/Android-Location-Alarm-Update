// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract MoondimeToken is ERC20, Ownable {
    constructor(uint256 initialSupply) ERC20("Moondime Token", "MDT") { // 18 decimals by default
        _mint(msg.sender, initialSupply);
    }

    // only the owner of this contract (deployer) can call this function
    function mint(address account, uint256 amount) public onlyOwner { 
        _mint(account, amount);
    }
}