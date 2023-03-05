
import React, { useEffect } from "react";

export default function LogOut() {
  useEffect(() => {
    localStorage.clear();
    window.location.reload();
  }, []);
  return <div></div>;
}
