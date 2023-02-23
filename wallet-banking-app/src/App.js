import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";
import SignIn from "./Component/SignIn";
import SignUp from "./Component/SignUp";

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/signin" element={<SignIn />} />
        <Route path="/signup" element={<SignUp />} />
      </Routes>
    </div>
  );
}

export default App;

//  {/* <SignIn></SignIn>
//       <SignUp></SignUp> */}
