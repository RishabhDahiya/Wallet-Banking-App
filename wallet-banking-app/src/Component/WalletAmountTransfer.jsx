import React, { useState } from "react";
import NavBar from "./NavBar";
import "./WalletAmountTransfer.css";
import Axios from "axios";
export default function WalletAmountTransfer() {
  const [username, setUsername] = useState("");
  const [amount, setAmount] = useState(0);

  const [amountError, setAmountError] = useState(true);
  // username empty handle karna hai
  const [displayResult, setDisplayResult] = useState("");
  const [isProcessed, setIsProcessed] = useState(false);

  const usernameChangeHandler = (event) => {
    setUsername(event.target.value);
  };

  const changeAmountInputEventHandler = (event) => {
    setAmount(event.target.value);
    if (event.target.value.trim().length > 0) setAmountError(false);
  };
  const transferAmountHandler = (event) => {
    event.preventDefault();

    if (!amountError) {
      const apiUrl = "http://localhost:8099/api/wallet/transfer";

      const token = localStorage.getItem("Wallet__token");

      const bodyParameters = {
        username: username,
        amount: amount,
      };

      const config = {
        headers: { Authorization: token },
      };

      Axios.post(apiUrl, bodyParameters, config)
        .then((response) => {
          //   console.log(response.data);
          setDisplayResult(response.data);
          //   alert(response.data);
        })
        .catch((error) => {
          alert(error.response.data);
          //     if (error.response && error.response.data) {
          //   alert(error.response.data.message);
          //     }
        });
      setIsProcessed(true);
    } else {
      alert("Enter valid amount");
    }
  };
  return (
    <div>
      <NavBar />
      {isProcessed === false ? (
        <div className="transferContainer">
          <form onSubmit={transferAmountHandler} className="form-transfer">
            <div>
              <label htmlFor="transaction_username">Enter username :</label>
              <input
                id="transaction_username"
                type="text"
                placeholder="Enter Username"
                onChange={usernameChangeHandler}
              />
            </div>
            <div>
              <label htmlFor="transaction_amount">Enter amount :</label>
              <input
                id="transaction_amount"
                type="number"
                placeholder="Enter amount"
                onChange={changeAmountInputEventHandler}
              />
            </div>
            <div>
              <button type="submit" className="button-transfer">
                Transfer
              </button>
            </div>
          </form>
        </div>
      ) : (
        <div className="transferContainer">{displayResult}</div>
      )}
    </div>
  );
}
