import { Routes, Route, Navigate } from "react-router-dom";
import { useEffect } from "react";
import WalletAmountTransfer from "./Component/WalletAmountTransfer";
import CheckBalance from "./Component/CheckBalance";
import WalletRecharge from "./Component/WalletRecharge";
import { useDispatch, useSelector } from "react-redux";
import { AUTOLOGIN, selectUserData } from "./reduxSlices/authSlice";
import "./App.css";
import SignIn from "./Component/SignIn";
import SignUp from "./Component/SignUp";
import NavBar from "./Component/NavBar";
import ShowTransactions from "./Component/ShowTransactions";
import LogOut from "./Component/LogOut";

function App() {
  const userData = useSelector(selectUserData);
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(AUTOLOGIN());
  }, []);
  return (
    <div className="App">
      {userData.token ? (
        <Routes>
          <Route path="/Recharge" element={<WalletRecharge />} />
          <Route path="/Check Balance" element={<CheckBalance />} />
          <Route path="/Pay" element={<WalletAmountTransfer />} />
          <Route path="/Show Transactions" element={<ShowTransactions />} />
          <Route path="/Log Out" element={<LogOut />} />
          <Route path="/wallet" element={<NavBar />} />
          <Route path="*" element={<Navigate to="/wallet" replace />} />
        </Routes>
      ) : (
        <Routes>
          <Route path="/signin" element={<SignIn />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="*" element={<Navigate to="/signin" replace />} />
        </Routes>
      )}
    </div>
  );
}

export default App;
