import React, { useState } from "react";
import NavBar from "./NavBar";
import "./WalletRecharge.css";
import Axios from "axios";

export default function WalletRecharge() {
  const [amountError, setAmountError] = useState(true);
  const [amount, setAmount] = useState(0);
  const [isRecharged, setIsRecharged] = useState(false);

  const changeAmountInputEventHandler = (event) => {
    setAmount(event.target.value);
    if (event.target.value.trim().length > 0) setAmountError(false);
  };

  const rechargeEventHandler = (event) => {
    event.preventDefault();
    if (!amountError) {
      const apiUrl = "http://localhost:8099/api/wallet/recharge";

      const token = localStorage.getItem("Wallet__token");

      const bodyParameters = {
        amount: amount,
      };

      const config = {
        headers: { Authorization: token },
      };

      Axios.post(apiUrl, bodyParameters, config)
        .then((response) => {
          console.log(response);
          //   setBalance(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
      setIsRecharged(true);
    } else {
      alert("Enter Valid Amount");
    }
  };
  return (
    <div>
      <NavBar></NavBar>
      {isRecharged !== true ? (
        <div className="container">
          <form onSubmit={rechargeEventHandler} className="form-recharge">
            <div>
              <label htmlFor="recharge">Enter Amount</label>
              <input
                type="number"
                placeholder="Enter amount"
                id="recharge"
                onChange={changeAmountInputEventHandler}
              />
            </div>
            <div>
              <button type="submit" className="button-recharge">
                Recharge
              </button>
            </div>
          </form>
        </div>
      ) : (
        <div className="container"> Recharged Succesfully</div>
      )}
      ;
    </div>
  );
}
