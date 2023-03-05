import React, { useState } from "react";
import NavBar from "./NavBar";
import Axios from "axios";
import "./CheckBalance.css";

export default function WalletBalance() {
  const [balance, setBalance] = useState(0);
  const [isFetched, setIsFetched] = useState(false);

  const checkBalanceHandler = () => {
    const apiUrl = "http://localhost:8099/api/wallet/show-balance";
    const token = localStorage.getItem("Wallet__token");

    const config = {
      headers: { Authorization: token },
    };
    Axios.get(apiUrl, config)
      .then((response) => {
        console.log(response);
        setBalance(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
    setIsFetched(true);
  };

  return (
    <div>
      <NavBar />
      {isFetched !== true ? (
        <div className="BalanceContainer">
          <div>
            <button onClick={checkBalanceHandler}>Check Balance</button>
          </div>
        </div>
      ) : (
        <div className="BalanceContainer">Wallet Balance : {balance}</div>
      )}
    </div>
  );
}

//  <div className="BalanceContainer">
//       <div>
//         <button onClick={checkBalanceHandler}>Check Balance</button>
//       </div>
//      <div>{balance}</div>
//       <BalanceBody amount={balance} />
//      </div>

//     console.log(token);
//     console.log("clicked");
//     axios
//       .get(apiUrl, { headers: { Authorization: { token } } })
//       .then((response) => {
//         console.log(response.data);
//         // setBalance(response.data);
//       });

// const config = {
//     headers: { Authorization: `Bearer ${token}` }
// };

// const bodyParameters = {
//    key: "value"
// };

// Axios.post(
//   'http://localhost:8000/api/v1/get_token_payloads',
//   bodyParameters,
//   config
// ).then(console.log).catch(console.log);
