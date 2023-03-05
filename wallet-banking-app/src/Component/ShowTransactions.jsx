import React, { useState, useEffect } from "react";
import Axios from "axios";
import NavBar from "./NavBar";
import "./ShowTransactions.css";

export default function ShowTransactions() {
  const [transactionsList, setTransactionsList] = useState([]);
  useEffect(() => {
    async function fetchTransactions() {
      const apiUrl = "http://localhost:8099/api/wallet/show-all-transactions";

      const token = localStorage.getItem("Wallet__token");

      const config = {
        headers: { Authorization: token },
      };

      await Axios.get(apiUrl, config)
        .then((response) => {
          console.log(response);
          setTransactionsList(response.data);
          //   setBalance(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    }
    fetchTransactions();
  }, []);

  return (
    <div>
      <NavBar />
      {transactionsList.length === 0 && (
        <div
          className="ListContainer"
          style={{
            border: "4px solid black",
            height: "200px",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <p>No recent Transactions</p>
        </div>
      )}
      {transactionsList.length !== 0 && (
        <div className="ListContainer">
          <table>
            <tr>
              {/* <th className="thead">transactionID</th> */}
              <th className="thead">transactionType</th>
              <th className="thead">username</th>
              <th className="thead">debitedFrom</th>
              <th className="thead">creditedTo</th>
              <th className="thead">amount</th>
            </tr>
            {transactionsList.map((value, index) => (
              <Transaction transaction={value} key={value["id"]} />
            ))}
          </table>
        </div>
      )}
    </div>
  );
}

function Transaction(props) {
  return (
    <tr>
      {/* <td className="tdata">{props.transaction["id"]}</td> */}
      <td className="tdata">{props.transaction["transactionType"]}</td>
      <td className="tdata">{props.transaction["username"]}</td>

      <td className="tdata">{props.transaction["debitedFrom"]}</td>
      <td className="tdata">{props.transaction["creditedTo"]}</td>
      <td className="tdata">{props.transaction["amount"]}</td>
    </tr>
  );
}

// <div>
//   <div>{props.transactionType}</div>
//   <div>{props.username}</div>
//   <div>{props.debitedFrom}</div>
//   <div>{props.creditedTo}</div>
//   <div>{props.amount}</div>
// </div>

// {
//     "transactionType": "Debit",
//     "username": "DeepakThakur",
//     "debitedFrom": "DeepakThakur",
//     "creditedTo": "RishabhSingh",
//     "amount": 500
//     },

// _id
// 63ff9481bc01536fc3487191
// transactionType
// "wallet-recharge"
// username
// "DipeshJohre"
// debitedFrom
// "Bank"
// creditedTo
// "Wallet"
// amount
// 3000
// _class
// "com.nextuple.walletapi.models.Transactions"

{
  /* {transactions.map((transaction, index) => (
          <Transaction
            key={index}
            transactionType={transaction.transactionType}
            username={transaction.username}
            debitedFrom={transaction.debitedFrom}
            creditedTo={transaction.creditedTo}
            amount={transaction.amount}
          />
        ))} */
}
